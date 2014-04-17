/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.service;

import com.yvphfk.model.form.EventRegistration;
import com.yvphfk.model.form.ParticipantSeat;
import com.yvphfk.model.RegisteredParticipant;
import com.yvphfk.model.RegistrationForm;
import com.yvphfk.model.form.RowMeta;

public interface ImportableService
{
    public EventRegistration registerParticipant (RegisteredParticipant registeredParticipant);

    public void addRowMeta (RowMeta rowMeta);

    public void addParticipantSeat (ParticipantSeat participantSeat);

    public void updateRegistration (RegistrationForm registrationForm);
}
