/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.model.form.validator;

import com.yvphfk.common.Util;
import com.yvphfk.model.Login;
import com.yvphfk.model.RegisteredParticipant;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator to validate the fields of the User Registration Form.
 */
public class RegistrationValidator implements Validator
{

    @Override
    public boolean supports (Class<?> clazz)
    {
        return RegisteredParticipant.class.isAssignableFrom(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate (Object target, Errors errors)
    {
        if (!(target instanceof RegisteredParticipant)) {
            throw new IllegalArgumentException("Target should be of RegisteredParticipant type");
        }

        RegisteredParticipant registeredParticipant = (RegisteredParticipant) target;

        if (RegisteredParticipant.ActionRegister.equals(
                registeredParticipant.getAction()) &&
                registeredParticipant.isNewbie()) {
            errors.pushNestedPath("participant");

            ValidationUtils.invokeValidator(new ParticipantValidator(),
                    registeredParticipant.getParticipant(), errors);

            errors.popNestedPath();
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventId", "eventId");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "registration.foundationId", "registration.foundationId");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "registration.eventFee", "registration.eventFee");
        if (registeredParticipant.getRegistration() != null &&
                registeredParticipant.getRegistration().getEvent() != null &&
                registeredParticipant.getRegistration().getEvent().isWorkshop() &&
                (registeredParticipant.getRegistration().getWorkshopLevelId() == null ||
                registeredParticipant.getRegistration().getWorkshopLevelId() < 0)){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "registration.workshopLevelId", "registration.workshopLevelId");
        }

        if (registeredParticipant.getRegistration().getAmountPayable() == null ||
                registeredParticipant.getRegistration().getAmountPayable() <= 0) {
            errors.reject("registration.amountPayable", "registration.amountPayable");
        }

        if (RegisteredParticipant.ActionRegister.equals(
                registeredParticipant.getAction()) &&
                registeredParticipant.isNewbie()) {
            if (registeredParticipant.getCurrentPayment() == null ||
                    registeredParticipant.getCurrentPayment().getAmountPaid() == null ||
                    registeredParticipant.getCurrentPayment().getAmountPaid() <= 0) {
                errors.reject("currentPayment.amountPaid", "currentPayment.amountPaid");
            }
        }

        Login login = Util.getCurrentUser();
        if (login.getAccess().isRegVolunteer() &&
                !(registeredParticipant.getRegistration().getTotalAmountPaid() >=
                        registeredParticipant.getRegistration().getAmountPayable())) {
            errors.reject("registration.noAccess", "registration.noAccess");
        }

        if (registeredParticipant.getCurrentPayment() != null &&
                registeredParticipant.getCurrentPayment().getAmountPaid() != null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentPayment.receiptInfo", "currentPayment.receiptInfo");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentPayment.mode", "currentPayment.mode");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentPayment.receiptDate", "currentPayment.receiptDate");
        }

    }


}
