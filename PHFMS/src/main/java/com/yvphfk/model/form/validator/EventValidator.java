/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.model.form.validator;

import com.yvphfk.model.form.Event;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 Validator to validate the fields of the Manage Event Form.
 */
public class EventValidator implements Validator
{

    @Override
    public boolean supports (Class<?> clazz)
    {
        return Event.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate (Object target, Errors errors)
    {
        if (!(target instanceof Event)) {
            throw new IllegalArgumentException("Target should be of Event type");
        }
        Event event = (Event) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "event.name.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eventType", "event.eventType.empty");

        if (event.getEventType() == Event.EventTypeCourse) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "courseType", "event.courseType.empty");
        }

        if (event.getCourseType() != null &&
                event.getCourseType().getPrimaryEligibility() != null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(
                    errors, "primaryEligibility", "event.primaryEligibility.empty");
        }

        if (event.getCourseType() != null &&
                event.getCourseType().getSecondaryEligibility() != null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(
                    errors, "secondaryEligibility", "event.secondaryEligibility.empty");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "primaryTrainer", "event.primaryTrainer.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "foundation", "event.foundation.empty");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "event.startDate.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDate", "event.endDate.empty");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "venue", "event.venue.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "event.city.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "state", "event.state.empty");

        if ("NONE".equalsIgnoreCase(event.getSeatingType())) {
            errors.rejectValue("seatingType", "event.seatingType.empty");
        }
    }

}
