/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.service;

import com.yvphfk.model.form.EventRegistration;
import com.yvphfk.model.form.ParticipantSeat;
import com.yvphfk.model.form.RowMeta;

public abstract class AbstractSeatingService implements SeatingService
{
    public ParticipantSeat createSeat (EventRegistration registration,
                                       RowMeta rowMeta,
                                       Integer seatNo)
    {
        ParticipantSeat seat = new ParticipantSeat();

        if (rowMeta != null) {
            seat.setAlpha(rowMeta.getRowName());
            seat.setGate(rowMeta.getGate());
            seat.setFoodCounter(rowMeta.getFoodCounter());
        }

        seat.setSeat(seatNo);
        seat.setEvent(registration.getEvent());
        seat.setCourseType(registration.getEvent().getPrimaryEligibility());
        seat.setRegistration(registration);

        return seat;
    }

}
