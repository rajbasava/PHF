/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.service;

import com.yvphfk.model.Login;
import com.yvphfk.model.ParticipantCourseForm;
import com.yvphfk.model.ParticipantCriteria;
import com.yvphfk.model.PaymentCriteria;
import com.yvphfk.model.RegisteredParticipant;
import com.yvphfk.model.RegistrationCriteria;
import com.yvphfk.model.TrainerCriteria;
import com.yvphfk.model.dao.EventDAO;
import com.yvphfk.model.dao.ParticipantDAO;
import com.yvphfk.model.form.CourseType;
import com.yvphfk.model.form.Event;
import com.yvphfk.model.form.EventPayment;
import com.yvphfk.model.form.EventRegistration;
import com.yvphfk.model.form.HistoryRecord;
import com.yvphfk.model.form.Participant;
import com.yvphfk.model.form.ParticipantCourse;
import com.yvphfk.model.form.ParticipantSeat;
import com.yvphfk.model.form.Trainer;
import com.yvphfk.model.form.TrainerCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParticipantServiceImpl implements ParticipantService
{
    @Autowired
    private ParticipantDAO participantDAO;

    @Autowired
    private EventDAO eventDAO;

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
    public ParticipantCourse addParticipantCourse (ParticipantCourseForm participantCourseForm)
    {
        return participantDAO.addParticipantCourse(participantCourseForm);
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

    @Transactional
    public List<ParticipantCourse> getCourses (Integer participantId)
    {
        return participantDAO.getCourses(participantId);
    }

    public Participant saveOrUpdateParticipant (Participant participant)
    {
        return participantDAO.saveOrUpdateParticipant(participant);
    }

    public Trainer addTrainer (Trainer trainer)
    {
        return participantDAO.addTrainer(trainer);
    }

    public Trainer getTrainer (Integer trainerId)
    {
        return participantDAO.getTrainer(trainerId);
    }

    public List<TrainerCourse> getTrainerCourses (Integer trainerId)
    {
        return participantDAO.getTrainerCourses(trainerId);
    }

    public TrainerCourse addTrainerCourse (TrainerCourse trainerCourse)
    {
        return participantDAO.addTrainerCourse(trainerCourse);
    }

    public List<Trainer> listTrainers (TrainerCriteria trainerCriteria)
    {
        return participantDAO.listTrainers(trainerCriteria);
    }

    public List getEligibleCourses (Integer participantId)
    {
        List results = new ArrayList();
        List<Event> reviewCourses = new ArrayList<Event>();
        List<Event> newCourses = new ArrayList<Event>();

        List<CourseType> courseTypes = getCourseTypes(participantId);
        List<Event> events = eventDAO.allEvents();

        for (Event event : events) {
            if (courseTypes.contains(event.getCourseType())) {
                reviewCourses.add(event);
            }
            else if (courseTypes.contains(event.getPrimaryEligibility()) &&
                    (event.getSecondaryEligibility() == null ||
                            courseTypes.contains(event.getSecondaryEligibility()))) {
                newCourses.add(event);
            }
        }

        results.add(newCourses);
        results.add(reviewCourses);

        return results;
    }

    private List<CourseType> getCourseTypes (Integer participantId)
    {
        List<CourseType> courseTypes = new ArrayList<CourseType>();
        List<ParticipantCourse> participantCourses = participantDAO.getCourses(participantId);
        for (ParticipantCourse participantCourse : participantCourses) {
            courseTypes.add(participantCourse.getCourseType());
        }
        return courseTypes;
    }

}