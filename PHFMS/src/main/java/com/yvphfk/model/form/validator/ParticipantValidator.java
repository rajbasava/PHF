/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model.form.validator;

import com.yvphfk.common.Util;
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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "participant.email.empty");
    }
}
