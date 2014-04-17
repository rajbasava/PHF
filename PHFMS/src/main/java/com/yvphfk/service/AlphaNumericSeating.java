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
import com.yvphfk.model.form.RowMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class AlphaNumericSeating implements SeatingService
{
    @Autowired
    private ParticipantDAO participantDAO;

    @Autowired
    private EventDAO eventDAO;

    @Transactional
    public void allocateSeats (Event event)
    {
        // fetch all the foreign registrations for this event with not VIPs and country not india and not null
        // fetch all the indian registrations for this event with not VIPs and country is india or null
        // fetch the all row meta by querying select row name, row max where row full is false order by sort order asc
        // for each row meta, construct a boolean seat flag array with row max length and set all the allocated seats as true. We should cache this row till the row is full
        // Increment the seat counter and check whether incremented counter is set true in the boolean seat flag array, if so increment once again, if not allocate.
        // saveOrUpdate tha allocated seat.
        // we should do batching and committing at 50 say.
        // once completed mark event as seat computed to true.

        if (event.isSeatAllocated()) {
            return;
        }

        List<EventRegistration> allUnallocatedForeignRegistrations =
                participantDAO.allUnallocatedRegistrations(event, false, false);
        List<EventRegistration> allUnallocatedIndianRegistrations =
                participantDAO.allUnallocatedRegistrations(event, false, true);
        List<EventRegistration> allUnallocatedRegistrations = new ArrayList<EventRegistration>();
        allUnallocatedRegistrations.addAll(allUnallocatedForeignRegistrations);
        allUnallocatedRegistrations.addAll(allUnallocatedIndianRegistrations);
        List<RowMeta> rowMetas = eventDAO.getAllEmptyRowMetas(event);

        int regsCount = 0;
        int regsSize = allUnallocatedRegistrations.size();

        for (RowMeta rowMeta : rowMetas) {

            if (regsCount >= regsSize) {
                break;
            }
            int seatCounter = 0;

            boolean[] seatFlags = markAllocatedSeats(event, rowMeta);

            boolean isRowFull = false;
            for (int i = 0; i < seatFlags.length; i++) {

                if (regsCount >= regsSize) {
                    break;
                }
                seatCounter = seatCounter + 1;

                if (!seatFlags[i]) {
                    EventRegistration registration = allUnallocatedRegistrations.get(regsCount);
                    ParticipantSeat seat = createSeat(registration, rowMeta.getRowName(), seatCounter);
                    participantDAO.saveOrUpdate(seat);
                    seatFlags[i] = true;
                    regsCount++;
                }

                if (seatFlags.length == seatCounter) {
                    isRowFull = true;
                }
            }

            if (isRowFull) {
                rowMeta.setRowFull(true);
                eventDAO.saveOrUpdate(rowMeta);
            }
        }
        event.setSeatAllocated(true);
        eventDAO.saveOrUpdate(event);
    }

    private boolean[] markAllocatedSeats (Event event, RowMeta rowMeta)
    {
        boolean[] seatFlags = new boolean[rowMeta.getRowMax()];
        Arrays.fill(seatFlags, false);

        List<ParticipantSeat> allocatedSeats =
                participantDAO.getAllocatedSeats(event, rowMeta.getRowName(), null);
        for (ParticipantSeat allocatedSeat : allocatedSeats) {
            seatFlags[allocatedSeat.getSeat().intValue() - 1] = true;
        }
        return seatFlags;
    }

    private ParticipantSeat createSeat (EventRegistration registration,
                                        String alpha,
                                        Integer seatNo)
    {
        ParticipantSeat seat = new ParticipantSeat();
        seat.setAlpha(alpha);
        seat.setSeat(seatNo);
        seat.setEvent(registration.getEvent());
        seat.setCourseType(registration.getEvent().getPrimaryEligibility());
        seat.setRegistration(registration);

        return seat;
    }

    @Transactional
    public ParticipantSeat nextSeat (Event event, EventRegistration registration)
    {
        RowMeta rowMeta = eventDAO.getFirstEmptyRowMeta(event);

        if (isRowMetaEmpty(event, rowMeta)) {
            rowMeta = eventDAO.getFirstEmptyRowMeta(event);
        }

        boolean[] seatFlags = markAllocatedSeats(event, rowMeta);

        int seatCounter = 0;
        ParticipantSeat seat = null;
        for (int i = 0; i < seatFlags.length; i++) {

            seatCounter = seatCounter + 1;

            if (!seatFlags[i]) {
                seat = createSeat(registration, rowMeta.getRowName(), seatCounter);
                break;
            }
        }

        return seat;
    }

    private boolean isRowMetaEmpty (Event event, RowMeta rowMeta)
    {
        boolean isRowMetaFull = true;
        boolean[] seatFlags = markAllocatedSeats(event, rowMeta);

        for (int i = 0; i < seatFlags.length; i++) {
            if (!seatFlags[i]) {
                isRowMetaFull = false;
            }
        }

        if (isRowMetaFull) {
            rowMeta.setRowFull(true);
            eventDAO.saveOrUpdate(rowMeta);
        }

        return isRowMetaFull;
    }
}
