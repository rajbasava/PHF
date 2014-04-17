/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.model;

import com.yvphfk.model.form.Participant;
import com.yvphfk.model.form.ParticipantCourse;

public class ParticipantCourseForm
{
    private Participant participant;
    private ParticipantCourse participantCourse;

    public Participant getParticipant ()
    {
        return participant;
    }

    public void setParticipant (Participant participant)
    {
        this.participant = participant;
    }

    public ParticipantCourse getParticipantCourse ()
    {
        return participantCourse;
    }

    public void setParticipantCourse (ParticipantCourse participantCourse)
    {
        this.participantCourse = participantCourse;
    }

    public void initialize (String email)
    {
        participant.initialize(email);
        participantCourse.initialize(email);
    }
}
