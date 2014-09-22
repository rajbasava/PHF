/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.common.email;

import com.yvphfk.model.RegistrationCriteria;
import com.yvphfk.model.form.BaseForm;
import com.yvphfk.model.form.EventRegistration;
import com.yvphfk.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RegisteredParticipantNotifier extends BasicEmailNotificationCreator
{

    public static final String JobName = "registeredParticipantNotifier";

    @Autowired
    private ParticipantService participantService;

    @Override
    protected Map getNotificationParameters ()
    {
        HashMap map = new HashMap();
        map.put(NotificationManager.TemplateName, "RegisteredParticipant");
        return  map;
    }

    @Override
    protected List getParticipantListToNotify (Map parameters)
    {
        RegistrationCriteria criteria = new RegistrationCriteria();
        criteria.setEventId((Integer) parameters.get(EventId));
        return participantService.listRegistrations(criteria);
    }

    @Override
    protected String getToAddress (Object valueObject)
    {
        EventRegistration registration = (EventRegistration) valueObject;
        return registration.getParticipant().getEmail();
    }

}
