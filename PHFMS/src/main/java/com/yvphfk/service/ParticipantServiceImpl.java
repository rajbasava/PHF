/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.service;

import com.yvphfk.model.ParticipantCourseForm;
import com.yvphfk.model.dao.ParticipantDAO;
import com.yvphfk.model.form.Event;
import com.yvphfk.model.form.EventPayment;
import com.yvphfk.model.form.EventRegistration;
import com.yvphfk.model.form.HistoryRecord;
import com.yvphfk.model.Login;
import com.yvphfk.model.form.Participant;
import com.yvphfk.model.ParticipantCriteria;
import com.yvphfk.model.form.ParticipantSeat;
import com.yvphfk.model.PaymentCriteria;
import com.yvphfk.model.form.ReferenceGroup;
import com.yvphfk.model.RegisteredParticipant;
import com.yvphfk.model.RegistrationCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ParticipantServiceImpl implements ParticipantService
{
    @Autowired
    private ParticipantDAO participantDAO;

    @Transactional
    public EventRegistration registerParticipant (RegisteredParticipant registeredParticipant, Login login)
    {
        return participantDAO.registerParticipant(registeredParticipant, login);
    }

    @Transactional
    public Participant getParticipant (Integer participantId)
    {
        return participantDAO.getParticipant(participantId);
    }

    @Transactional
    public EventRegistration getEventRegistration (Integer registrationId)
    {
        return participantDAO.getEventRegistration(registrationId);
    }

    @Transactional
    public List<Participant> listParticipants (ParticipantCriteria participantCriteria)
    {
        return participantDAO.listParticipants(participantCriteria);
    }

    @Transactional
    public List<EventRegistration> listRegistrations (RegistrationCriteria registrationCriteria)
    {
        return participantDAO.listRegistrations(registrationCriteria);
    }

    @Transactional
    public List<EventPayment> listPayments (PaymentCriteria paymentCriteria)
    {
        return participantDAO.listPayments(paymentCriteria);
    }

    @Transactional
    public void processPayment (EventPayment payment, Integer registrationId, boolean isAdd)
    {
        participantDAO.processPayment(payment, registrationId, isAdd);
    }

    @Transactional
    public void cancelRegistration (EventRegistration registration, HistoryRecord historyRecord)
    {
        participantDAO.cancelRegistration(registration, historyRecord);
    }

    @Transactional
    public void onHoldRegistration (EventRegistration registration, HistoryRecord historyRecord)
    {
        participantDAO.onHoldRegistration(registration, historyRecord);
    }

    @Transactional
    public void changeToRegistered (EventRegistration registration, HistoryRecord historyRecord)
    {
        participantDAO.changeToRegistered(registration, historyRecord);
    }

    @Transactional
    public void replaceParticipant (EventRegistration registration,
                                    Participant participantToReplace,
                                    HistoryRecord record)
    {
        participantDAO.replaceParticipant(registration, participantToReplace, record);
    }

    @Transactional
    public void addParticipantCourse (ParticipantCourseForm participantCourseForm)
    {
        participantDAO.addParticipantCourse(participantCourseForm);
    }

    @Transactional
    public List<ParticipantSeat> getAllSeats (Event event)
    {
        return participantDAO.getAllSeats(event);
    }

    @Transactional
    public void addParticipantSeat (ParticipantSeat participantSeat)
    {
        participantDAO.addParticipantSeat(participantSeat);
    }

}