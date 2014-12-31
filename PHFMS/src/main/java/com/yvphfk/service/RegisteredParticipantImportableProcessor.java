/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.service;

import com.yvphfk.common.ImportableProcessor;
import com.yvphfk.common.Util;
import com.yvphfk.model.Importable;
import com.yvphfk.model.RegisteredParticipant;
import com.yvphfk.model.dao.EventDAO;
import com.yvphfk.model.dao.ParticipantDAO;
import com.yvphfk.model.form.CourseType;
import com.yvphfk.model.form.EventFee;
import com.yvphfk.model.form.EventRegistration;
import com.yvphfk.model.form.PHFoundation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisteredParticipantImportableProcessor implements ImportableProcessor
{
    @Autowired
    private ParticipantDAO participantDAO;

    @Autowired
    private EventDAO eventDAO;

    @Override
    public void preLoad (Importable importable)
    {
        RegisteredParticipant registeredParticipant = (RegisteredParticipant) importable;
        loadFoundation(registeredParticipant);
        loadCourseLevel(registeredParticipant);
        loadBestEventFee(registeredParticipant);
    }

    @Override
    public void postLoad (Importable importable)
    {


    }

    private void loadFoundation (RegisteredParticipant registeredParticipant)
    {
        String foundationShortName = registeredParticipant.getFoundationShortName();
        if (!Util.nullOrEmptyOrBlank(foundationShortName)) {
            PHFoundation foundation = eventDAO.getFoundation(foundationShortName);
            if (foundation != null) {
                registeredParticipant.getRegistration().setFoundation(foundation);
            }
        }
    }

    private void loadCourseLevel (RegisteredParticipant registeredParticipant)
    {
        String courseTypeShortName = registeredParticipant.getCourseTypeShortName();
        if (!Util.nullOrEmptyOrBlank(courseTypeShortName)) {
            CourseType courseType = eventDAO.getCourseType(courseTypeShortName);
            if (courseType != null) {
                registeredParticipant.getRegistration().setCourseType(courseType);
            }
        }
    }

    private void loadBestEventFee (RegisteredParticipant registeredParticipant)
    {
        String courseTypeShortName = registeredParticipant.getCourseTypeShortName();
        EventRegistration registration = registeredParticipant.getRegistration();
        if (!Util.nullOrEmptyOrBlank(courseTypeShortName)) {
            EventFee eventFee = eventDAO.getBestEventFee(registration.getEvent().getId(),
                    registration.isReview(),
                    registration.getAmountPayable(),
                    registration.getCourseType());
            if (eventFee != null) {
                registeredParticipant.getRegistration().setEventFee(eventFee);
            }
        }
    }
}
