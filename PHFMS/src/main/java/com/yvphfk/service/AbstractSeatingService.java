/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.service;

import com.yvphfk.model.form.EventRegistration;
import com.yvphfk.model.form.ParticipantSeat;

public abstract class AbstractSeatingService implements SeatingService
{
    public ParticipantSeat createSeat (EventRegistration registration,
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

}
