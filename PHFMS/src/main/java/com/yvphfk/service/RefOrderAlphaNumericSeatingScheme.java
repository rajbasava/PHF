/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.service;

import com.yvphfk.model.dao.ParticipantDAO;
import com.yvphfk.model.form.Event;
import com.yvphfk.model.form.EventRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RefOrderAlphaNumericSeatingScheme implements AlphaNumericSeatingScheme
{
    @Autowired
    private ParticipantDAO participantDAO;

    @Override
    public List<EventRegistration> fetchList (Event event)
    {
        List<EventRegistration> results = participantDAO.allRefOrderBasedUnallocatedRegistrations(event, false);

        return results;
    }
}
