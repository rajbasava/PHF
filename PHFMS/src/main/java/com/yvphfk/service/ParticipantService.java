/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.service;

import com.yvphfk.model.ParticipantCourseForm;
import com.yvphfk.model.ParticipantCriteria;
import com.yvphfk.model.TrainerCriteria;
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
import com.yvphfk.model.form.Trainer;
import com.yvphfk.model.form.TrainerCourse;

import java.util.List;

public interface ParticipantService extends CommonService
{
    public EventRegistration registerParticipant (RegisteredParticipant registeredParticipant, Login login);

    public Participant getParticipant (Integer participantId);

    public EventRegistration getEventRegistration (Integer registrationId);

    public List<Participant> listParticipants (ParticipantCriteria participantCriteria);

    public List<EventRegistration> listRegistrations (RegistrationCriteria registrationCriteria);

    public List<EventPayment> listPayments (PaymentCriteria paymentCriteria);

    public List<Participant> listParticipantsNotInEvent (ParticipantCriteria participantCriteria, Event event);

    public void processPayment (EventPayment payment, Integer registrationId, boolean isAdd);

    public void cancelRegistration (EventRegistration registration, HistoryRecord historyRecord);

    public void onHoldRegistration (EventRegistration registration, HistoryRecord historyRecord);

    public void changeToRegistered (EventRegistration registration, HistoryRecord historyRecord);

    public void replaceParticipant (EventRegistration registration,
                                    Participant participantToReplace,
                                    HistoryRecord record);

    public ParticipantCourse addParticipantCourse (ParticipantCourseForm participantCourseForm);

    public List<ParticipantSeat> getAllSeats (Event event);

    public void addParticipantSeat (ParticipantSeat participantSeat);

    public List<ParticipantCourse> getCourses (Integer participantId);

    public Participant saveOrUpdateParticipant (Participant participant);

    public Trainer addTrainer (Trainer trainer);

    public Trainer getTrainer (Integer trainerId);

    public Trainer getTrainerByParticipantId (Integer participantId);

    public List<TrainerCourse> getTrainerCourses (Integer trainerId);

    public TrainerCourse addTrainerCourse(TrainerCourse trainerCourse);

    public List<Trainer> listTrainers (TrainerCriteria trainerCriteria);

    public List getEligibleCourses (Integer participantId);

    public List<EventRegistration> getRegisteredCourses (Integer participantId);

}
