/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.model.dao;

import com.yvphfk.common.Util;
import com.yvphfk.model.form.LoggedInVolunteer;
import com.yvphfk.model.Login;
import com.yvphfk.model.form.Volunteer;
import com.yvphfk.model.form.VolunteerKit;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Repository
public class VolunteerDAOImpl extends CommonDAOImpl implements VolunteerDAO
{
    @Autowired
    private SessionFactory sessionFactory;

    public void addVolunteer (Volunteer volunteer)
    {
        sessionFactory.getCurrentSession().save(volunteer);
    }

    public void addVolunteerKit (VolunteerKit volunteerKit)
    {
        Session session = sessionFactory.openSession();
        session.save(volunteerKit);
        session.flush();
        session.close();
    }

    public List<Volunteer> listVolunteer ()
    {
        return sessionFactory.getCurrentSession().createQuery("from Volunteer")
                .list();
    }

    public Map<String, String> listVolunteerWithoutKits (Integer eventKitId)
    {
        Session session = sessionFactory.openSession();
        String query = "select v.id, v.name " +
                "from phk_vollogin vl, phk_volunteer v " +
                "where vl.volunteerid = v.id " +
                "and vl.id not in " +
                "(select volloginid from phk_volkit where kitid = " + eventKitId + ")";
        List resultList = session.createSQLQuery(query).list();
        Map<String, String> volunteerMap = new LinkedHashMap<String, String>();
        if (resultList != null && !resultList.isEmpty()) {
            for (int i = 0; i < resultList.size(); i++) {
                Object[] array = (Object[]) resultList.get(i);
                volunteerMap.put(String.valueOf(array[0]), String.valueOf(array[1]));
            }
        }
        session.flush();
        session.close();
        return volunteerMap;
    }

    public void removeVolunteer (Integer id)
    {
        Volunteer volunteer = (Volunteer) sessionFactory.getCurrentSession().load(
                Volunteer.class, id);
        LoggedInVolunteer loggedInVolunteer = volunteer.getLogin();

        if (null != loggedInVolunteer) {
            sessionFactory.getCurrentSession().delete(loggedInVolunteer);
        }

        if (null != volunteer) {
            sessionFactory.getCurrentSession().delete(volunteer);
        }
    }

    public boolean processLogin (Login login)
    {
        if (Util.nullOrEmptyOrBlank(login.getEmail())) {
            return false;
        }

        Volunteer volunteer = getVolunteerByEmail(login.getEmail());

        if (volunteer != null &&
                volunteer.getEmail().equals(login.getEmail()) &&
                volunteer.getPassword().equals(login.getPassword())) {
            LoggedInVolunteer loggedInVolunteer = volunteer.getLogin();
            if (loggedInVolunteer == null) {
                loggedInVolunteer = new LoggedInVolunteer();
                loggedInVolunteer.setVolunteer(volunteer);
                loggedInVolunteer.setCounter(login.getCounter());
                loggedInVolunteer.setLoggedin(new Date());
                sessionFactory.getCurrentSession().save(loggedInVolunteer);
            }
            else {
                loggedInVolunteer.setCounter(login.getCounter());
                loggedInVolunteer.setLoggedin(new Date());
                loggedInVolunteer.setLoggedout(null);
                sessionFactory.getCurrentSession().update(loggedInVolunteer);
            }
            login.setName(volunteer.getName());
            login.setVolunteerId(volunteer.getId());
            login.setPermission(volunteer.getPermission());
            return true;
        }

        return false;
    }

    public void processLogout (Login login)
    {
        if (login != null && !Util.nullOrEmptyOrBlank(login.getEmail())) {
            Volunteer volunteer = getVolunteerByEmail(login.getEmail());
            LoggedInVolunteer loggedInVolunteer = volunteer.getLogin();
            loggedInVolunteer.setLoggedout(new Date());
            sessionFactory.getCurrentSession().update(loggedInVolunteer);
        }
    }

    private Volunteer getVolunteerByEmail (String email)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Volunteer.class);

        criteria.add(Restrictions.eq("email", email));
        List<Volunteer> volunteers = criteria.list();

        session.close();
        if (volunteers == null ||
                volunteers.isEmpty()) {
            return null;
        }

        return volunteers.get(0);
    }

    @Override
    public Volunteer getVolunteer (Integer volunteerId)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Volunteer.class);

        criteria.add(Restrictions.eq("id", volunteerId));
        Volunteer volunteer = (Volunteer) criteria.uniqueResult();
        session.flush();
        session.close();
        return volunteer;
    }


}