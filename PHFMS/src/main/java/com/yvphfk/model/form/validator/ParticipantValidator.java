/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model.form.validator;

import com.yvphfk.common.ApplicationContextUtils;
import com.yvphfk.common.Util;
import com.yvphfk.model.dao.ParticipantDAO;
import com.yvphfk.model.form.Participant;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ParticipantValidator implements Validator
{
    @Override
    public boolean supports (Class<?> aClass)
    {
        return Participant.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate (Object target, Errors errors)
    {
        if (!(target instanceof Participant)) {
            throw new IllegalArgumentException("Target should be of Participant");
        }
        Participant participant = (Participant) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "participant.name.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobile", "participant.mobile.empty");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "participant.email.empty");

        if (participant.getName() != null &&
                participant.getMobile() != null) {
            ParticipantDAO participantDAO =
                    (ParticipantDAO) ApplicationContextUtils.getApplicationContext().getBean("participantDAOImpl");
            Participant orgParticipant = participantDAO.getParticipant(participant.getId());

            if (orgParticipant != null) {
                if (orgParticipant.getName().equalsIgnoreCase(participant.getName()) &&
                        orgParticipant.getMobile().equalsIgnoreCase(participant.getMobile()))  {
                    return;
                }
            }

            Participant temp = participantDAO.getParticipant(participant.getName(), participant.getMobile());
            if (temp != null) {
                errors.reject("participant.name.unique");
            }
        }
    }
}
