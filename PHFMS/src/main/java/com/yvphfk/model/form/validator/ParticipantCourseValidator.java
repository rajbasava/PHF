/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model.form.validator;

import com.yvphfk.model.form.Participant;
import com.yvphfk.model.form.ParticipantCourse;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ParticipantCourseValidator implements Validator
{
    @Override
    public boolean supports (Class<?> aClass)
    {
        return ParticipantCourse.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate (Object target, Errors errors)
    {
        if (!(target instanceof ParticipantCourse)) {
            throw new IllegalArgumentException("Target should be of ParticipantCourse");
        }
        ParticipantCourse participantCourse = (ParticipantCourse) target;

        Participant participant = participantCourse.getParticipant();

        if (participant != null && participant.getId() == null) {
            errors.pushNestedPath("participant");
            ValidationUtils.invokeValidator(new ParticipantValidator(),
                    participantCourse.getParticipant(), errors);
            errors.popNestedPath();
        }

        errors.pushNestedPath("participantCourse");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "foundation", "participantCourse.foundation.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "courseType", "participantCourse.courseType.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "primaryTrainer", "participantCourse.primaryTrainer.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "participantCourse.startDate.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDate", "participantCourse.endDate.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "participantCourse.city.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "state", "participantCourse.state.empty");

        errors.popNestedPath();
    }
}
