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
    private Integer participantId;
    private ParticipantCourse participantCourse;
    private boolean showParticipantDetails;

    public Participant getParticipant ()
    {
        return participant;
    }

    public void setParticipant (Participant participant)
    {
        this.participant = participant;
    }

    public Integer getParticipantId ()
    {
        return participantId;
    }

    public void setParticipantId (Integer participantId)
    {
        this.participantId = participantId;
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
        if (participant != null) {
            participant.initialize(email);
        }

        if (participantCourse != null) {
            participantCourse.initialize(email);
        }
    }
}
