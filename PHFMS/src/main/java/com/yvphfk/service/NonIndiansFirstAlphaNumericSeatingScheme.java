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

import java.util.ArrayList;
import java.util.List;

@Component
public class NonIndiansFirstAlphaNumericSeatingScheme implements AlphaNumericSeatingScheme
{
    @Autowired
    private ParticipantDAO participantDAO;

    @Override
    public List<EventRegistration> fetchList (Event event)
    {
        List<EventRegistration> allUnallocatedForeignRegistrations =
                participantDAO.allUnallocatedRegistrations(event, false, ParticipantDAO.NonIndians);
        List<EventRegistration> allUnallocatedIndianRegistrations =
                participantDAO.allUnallocatedRegistrations(event, false, ParticipantDAO.Indians);
        List<EventRegistration> allUnallocatedRegistrations = new ArrayList<EventRegistration>();
        allUnallocatedRegistrations.addAll(allUnallocatedForeignRegistrations);
        allUnallocatedRegistrations.addAll(allUnallocatedIndianRegistrations);

        return allUnallocatedRegistrations;
    }
}
