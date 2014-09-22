/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.service;

import com.yvphfk.model.dao.EventDAO;
import com.yvphfk.model.dao.ParticipantDAO;
import com.yvphfk.model.form.Event;
import com.yvphfk.model.form.EventRegistration;
import com.yvphfk.model.form.ParticipantSeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class NumericSeating extends AbstractSeatingService
{
    @Autowired
    private ParticipantDAO participantDAO;

    @Autowired
    private EventDAO eventDAO;

    @Transactional
    public void allocateSeats (Event event)
    {
        if (event.isSeatAllocated()) {
            return;
        }

        List<EventRegistration> allUnallocatedForeignRegistrations =
                participantDAO.allUnallocatedRegistrations(event, false, ParticipantDAO.All);
        List<EventRegistration> allUnallocatedRegistrations = new ArrayList<EventRegistration>();
        allUnallocatedRegistrations.addAll(allUnallocatedForeignRegistrations);

        int regsSize = allUnallocatedRegistrations.size();

        int seatCounter = 0;

        boolean[] seatFlags = markAllocatedSeats(event, regsSize);

        for (EventRegistration registration : allUnallocatedRegistrations) {

            for (int i = 0; i < seatFlags.length; i++) {

                if (!seatFlags[i]) {
                    seatCounter = seatCounter + 1;
                    ParticipantSeat seat = createSeat(registration, null, seatCounter);
                    participantDAO.saveOrUpdate(seat);
                    seatFlags[i] = true;
                    break;
                }
            }
        }
        event.setSeatAllocated(true);
        eventDAO.saveOrUpdate(event);
    }

    private boolean[] markAllocatedSeats (Event event, int unAllocSize)
    {

        List<ParticipantSeat> allocatedSeats =
                participantDAO.getAllocatedSeats(event, null, null);

        int maxSize = unAllocSize + allocatedSeats.size();

        boolean[] seatFlags = new boolean[maxSize];
        Arrays.fill(seatFlags, false);

        for (ParticipantSeat allocatedSeat : allocatedSeats) {
            seatFlags[allocatedSeat.getSeat().intValue() - 1] = true;
        }
        return seatFlags;
    }

    public ParticipantSeat nextSeat (Event event, EventRegistration registration)
    {
        ParticipantSeat maxSeat = participantDAO.getMaxAllocatedSeat(event);

        int seatCounter = 0;
        if (maxSeat != null) {
            seatCounter = maxSeat.getSeat().intValue();
        }
        seatCounter = seatCounter + 1;

        ParticipantSeat seat = createSeat(registration, null, seatCounter);
        return seat;

    }
}
