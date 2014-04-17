/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.model.dao;

import com.yvphfk.model.ParticipantCourseForm;
import com.yvphfk.model.ParticipantCriteria;
import com.yvphfk.model.form.Event;
import com.yvphfk.model.form.EventPayment;
import com.yvphfk.model.form.EventRegistration;
import com.yvphfk.model.form.HistoryRecord;
import com.yvphfk.model.Login;
import com.yvphfk.model.form.Participant;
import com.yvphfk.model.form.ParticipantCourse;
import com.yvphfk.model.form.ParticipantSeat;
import com.yvphfk.model.PaymentCriteria;
import com.yvphfk.model.RegisteredParticipant;
import com.yvphfk.model.RegistrationCriteria;
import com.yvphfk.model.RegistrationForm;

import java.util.List;

public interface ParticipantDAO extends CommonDAO
{
    public ParticipantCourse addParticipantCourse (ParticipantCourseForm participantCourseForm);

    public EventRegistration registerParticipant (RegisteredParticipant registeredParticipant, Login login);

    public Participant getParticipant (Integer userId);

    public void addParticipantSeat (ParticipantSeat participantSeat);

    public EventRegistration getEventRegistration (Integer registrationId);

    public List<Participant> listParticipants (ParticipantCriteria participantCriteria);

    public List<EventRegistration> listRegistrations (RegistrationCriteria registrationCriteria);

    public List<EventRegistration> allUnallocatedRegistrations (Event event, boolean vip, boolean indian);

    public List<EventPayment> listPayments (PaymentCriteria paymentCriteria);

    public void processPayment (EventPayment payment, Integer registrationId, boolean isAdd);

    public List<ParticipantSeat> getAllSeats (Event event, String level);

    public void cancelRegistration (EventRegistration registration, HistoryRecord historyRecord);

    public void onHoldRegistration (EventRegistration registration, HistoryRecord historyRecord);

    public void changeToRegistered (EventRegistration registration, HistoryRecord historyRecord);

    public void replaceParticipant (EventRegistration registration,
                                    Participant participantToReplace,
                                    HistoryRecord record);

    public List<ParticipantSeat> getAllocatedSeats (Event event, String alpha, String suffix);

    public void removeEventRegistrations (Integer id);

    public List<ParticipantSeat> getAllSeats (Event event);

    public void updateRegistration (RegistrationForm registrationForm);

}
