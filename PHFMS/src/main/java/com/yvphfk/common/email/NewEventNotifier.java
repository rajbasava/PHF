/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.common.email;

import com.yvphfk.service.ParticipantService;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NewEventNotifier extends BasicEmailNotificationCreator
{
    public static final String JobName = "newEventNotifier";

    private String query = "SELECT partcors.id, part.email " +
            "FROM " +
            "  phk_partipantcourse AS partcors, " +
            "  phk_participant AS part, " +
            "  phk_event as evt " +
            "WHERE " +
            "partcors.participant = part.id " +
            "AND part.email IS NOT NULL " +
            "AND evt.id = 3 " +
            "AND (partcors.coursetype = (select coursetype from phk_event where id = evt.id)  " +
            "OR (partcors.coursetype = (select primaryeligibility from phk_event where id = evt.id) " +
            "and partcors.coursetype = (select secondaryeligibility from phk_event where id = evt.id))) ";

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    protected Map getNotificationParameters ()
    {
        HashMap map = new HashMap();
        map.put(NotificationManager.TemplateName, "NewEvent");
        return map;
    }

    @Override
    protected List getParticipantListToNotify (Map parameters)
    {
        int eventId = ((Integer) parameters.get(EventId)).intValue();
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(query);
        sqlQuery.setParameter("eventId", eventId);
        return sqlQuery.list();
    }

    @Override
    protected String getToAddress (Object valueObject)
    {
        Object[] bo = (Object[]) valueObject;
        return (String) bo[1];
    }
}
