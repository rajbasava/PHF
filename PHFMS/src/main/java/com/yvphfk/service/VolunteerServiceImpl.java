/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.service;

import com.yvphfk.model.dao.VolunteerDAO;
import com.yvphfk.model.Login;
import com.yvphfk.model.form.Volunteer;
import com.yvphfk.model.form.VolunteerKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class VolunteerServiceImpl implements VolunteerService
{
    @Autowired
    private VolunteerDAO volunteerDAO;

    @Transactional
    public void addVolunteer (Volunteer volunteer)
    {
        volunteerDAO.addVolunteer(volunteer);
    }

    @Transactional
    public void addVolunteerKit (VolunteerKit volunteerKit)
    {
        volunteerDAO.addVolunteerKit(volunteerKit);
    }

    @Transactional
    public List<Volunteer> listVolunteer ()
    {
        return volunteerDAO.listVolunteer();
    }

    @Transactional
    public Map<String, String> listVolunteerWithoutKits (Integer eventKitId)
    {
        return volunteerDAO.listVolunteerWithoutKits(eventKitId);
    }

    @Transactional
    public void removeVolunteer (Integer id)
    {
        volunteerDAO.removeVolunteer(id);
    }

    @Transactional
    public boolean processLogin (Login login)
    {
        return volunteerDAO.processLogin(login);
    }

    @Transactional
    public void processLogout (Login login)
    {
        volunteerDAO.processLogout(login);
    }

    @Transactional
    public Volunteer getVolunteer (Integer volunteerId)
    {
        return volunteerDAO.getVolunteer(volunteerId);
    }
}