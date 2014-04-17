/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
 */
package com.yvphfk.model.form.validator;

import com.yvphfk.model.RegistrationPayments;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 Validator to validate the fields of the Payment Form.
 */
public class PaymentValidator implements Validator
{

    @Override
    public boolean supports (Class<?> clazz)
    {
        return RegistrationPayments.class.isAssignableFrom(clazz);
    }

    /**
     {@inheritDoc}
     */
    @Override
    public void validate (Object target, Errors errors)
    {
        if (!(target instanceof RegistrationPayments)) {
            throw new IllegalArgumentException(
                    "Target should be of RegistrationPayments type");
        }

        RegistrationPayments participant = (RegistrationPayments) target;

        if (participant.getCurrentPayment() != null) {
            if (participant.getCurrentPayment().getAmountPaid() == null
                    || participant.getCurrentPayment().getAmountPaid() <= 0) {
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentPayment.amountPaid",
                        "currentPayment.amountPaid");
            }
            else if (participant.getCurrentPayment() != null && participant.getCurrentPayment().getAmountPaid() != null
                    && participant.getCurrentPayment().getAmountPaid() > 0) {
                ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                        "currentPayment.receiptInfo",
                        "currentPayment.receiptInfo");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                        "currentPayment.receiptDate",
                        "currentPayment.receiptDate");
            }

            if (participant.getCurrentPayment() != null
                    && participant.getCurrentPayment().isPdcNotClear()) {
                ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                        "currentPayment.pdcDate", "currentPayment.pdcDate");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                        "currentPayment.pdc", "currentPayment.pdc");
            }
        }
    }

}
