/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.service;

import com.yvphfk.common.AutoWiringQuartzJobBean;
import com.yvphfk.common.Util;
import com.yvphfk.model.dao.ParticipantDAO;
import com.yvphfk.model.form.Event;
import com.yvphfk.model.form.EventRegistration;
import com.yvphfk.model.form.ParticipantCourse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UpdateAttendedCoursesJob extends AutoWiringQuartzJobBean
{
    @Autowired
    private ParticipantDAO participantDAO;

    @Autowired
    private SessionFactory sessionFactory;

    private static final int CommitSize = 50;

    @Override
    protected void run (org.quartz.JobExecutionContext jobExecutionContext) throws JobExecutionException
    {
        Date cutOffDate = Util.add(Util.getDateWithoutTime(new Date()), Calendar.DATE, -2);

        List<EventRegistration> registrationList = participantDAO.allAttendedRegistrationsTill(cutOffDate);

        if (registrationList == null || registrationList.isEmpty()) {
            return;
        }

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        int count = 0;
        for (EventRegistration registration : registrationList) {
            if (EventRegistration.StatusRegistered.equals(registration.getStatus()) &&
                    registration.isEventKit() &&
                    !registration.isReview()) {
                ParticipantCourse course = createParticipantCourse(registration);
                session.save(course);
            }

            registration.setActive(false);
            session.update(registration);
            count++;

            if (count % CommitSize == 0) {
                session.flush();
                session.clear();
            }
        }

        transaction.commit();
        session.flush();
        session.close();
    }

    private ParticipantCourse createParticipantCourse (EventRegistration registration)
    {
        Event event = registration.getEvent();
        ParticipantCourse course = new ParticipantCourse();
        course.initialize("system");

        course.setParticipant(registration.getParticipant());
        course.setCourseType(event.getCourseType());
        course.setFoundation(event.getFoundation());
        course.setPrimaryTrainer(event.getPrimaryTrainer());
        course.setSecondaryTrainer(event.getSecondaryTrainer());
        course.setStartDate(event.getStartDate());
        course.setEndDate(event.getEndDate());
        course.setCity(event.getCity());
        course.setState(event.getState());

        return course;
    }

}
