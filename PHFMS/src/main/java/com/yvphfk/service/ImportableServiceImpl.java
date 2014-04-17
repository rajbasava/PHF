/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.service;

import com.yvphfk.common.Util;
import com.yvphfk.model.dao.EventDAO;
import com.yvphfk.model.dao.ParticipantDAO;
import com.yvphfk.model.form.EventRegistration;
import com.yvphfk.model.Login;
import com.yvphfk.model.form.ParticipantSeat;
import com.yvphfk.model.RegisteredParticipant;
import com.yvphfk.model.RegistrationForm;
import com.yvphfk.model.form.RowMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImportableServiceImpl implements ImportableService
{
    @Autowired
    private ParticipantDAO participantDAO;

    @Autowired
    private EventDAO eventDAO;

    public EventRegistration registerParticipant (RegisteredParticipant registeredParticipant)
    {
        Login login = Util.getCurrentUser();
        return participantDAO.registerParticipant(registeredParticipant, login);
    }

    public void addRowMeta (RowMeta rowMeta)
    {
        eventDAO.addRowMeta(rowMeta);
    }

    public void addParticipantSeat (ParticipantSeat participantSeat)
    {
        participantDAO.addParticipantSeat(participantSeat);
    }

    public void updateRegistration (RegistrationForm registrationForm)
    {
        participantDAO.updateRegistration(registrationForm);
    }

}
