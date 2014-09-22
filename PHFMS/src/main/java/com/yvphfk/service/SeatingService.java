/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.service;

import com.yvphfk.model.form.Event;
import com.yvphfk.model.form.EventRegistration;
import com.yvphfk.model.form.ParticipantSeat;

public interface SeatingService
{
    public void allocateSeats (Event event);

    public ParticipantSeat nextSeat (Event event, EventRegistration registration);

    public ParticipantSeat createSeat (EventRegistration registration,
                                          String alpha,
                                          Integer seatNo);


}
