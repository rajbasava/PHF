/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.service;

import com.yvphfk.common.ImportableProcessor;
import com.yvphfk.common.Util;
import com.yvphfk.model.Importable;
import com.yvphfk.model.dao.EventDAO;
import com.yvphfk.model.dao.ParticipantDAO;
import com.yvphfk.model.form.CourseType;
import com.yvphfk.model.form.ParticipantSeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipantSeatImportableProcessor implements ImportableProcessor
{
    @Autowired
    private ParticipantDAO participantDAO;

    @Autowired
    private EventDAO eventDAO;

    @Override
    public void preLoad (Importable importable)
    {
        ParticipantSeat participantSeat = (ParticipantSeat) importable;
        loadCourseLevel(participantSeat);
    }

    @Override
    public void postLoad (Importable importable)
    {


    }

    private void loadCourseLevel (ParticipantSeat participantSeat)
    {
        String courseTypeShortName = participantSeat.getCourseTypeShortName();
        if (!Util.nullOrEmptyOrBlank(courseTypeShortName)) {
            CourseType courseType = eventDAO.getCourseType(courseTypeShortName);
            if (courseType != null) {
                participantSeat.setCourseType(courseType);
            }
        }
    }
}
