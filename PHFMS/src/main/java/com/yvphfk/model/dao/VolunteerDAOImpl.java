/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.model.dao;

import com.yvphfk.common.CommonCache;
import com.yvphfk.common.Util;
import com.yvphfk.model.form.AccessControl;
import com.yvphfk.model.form.AccessFilter;
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

        List<AccessFilter> filterList = getAccessFilterList(volunteer.getId());
        for (AccessFilter af: filterList) {
            sessionFactory.getCurrentSession().delete(af);
        }

        List<AccessControl> controlList = getAccessControlList(volunteer.getId());
        for (AccessControl ac: controlList) {
            sessionFactory.getCurrentSession().delete(ac);
        }

        if (null != volunteer) {
            sessionFactory.getCurrentSession().delete(volunteer);
        }
    }

    public int processLogin (Login login)
    {
        if (Util.nullOrEmptyOrBlank(login.getEmail())) {
            return Login.InvalidUsernamePassword;
        }

        Volunteer volunteer = getVolunteerByEmail(login.getEmail());

        if (volunteer != null &&
                volunteer.getEmail().equals(login.getEmail()) &&
                volunteer.getPassword().equals(login.getPassword())) {
            login.setName(volunteer.getName());
            login.setVolunteerId(volunteer.getId());
            List<AccessControl> accessControlList = getAccessControlList(volunteer.getId());
            login.setAccessControlList(accessControlList);
            List<AccessFilter> accessFilterList = getAccessFilterList(volunteer.getId());
            login.setAccessFilterList(accessFilterList);

            if (login.hasNoAccess()) {
                return Login.UserHasNoAccess;
            }

            LoggedInVolunteer loggedInVolunteer = volunteer.getLogin();
            if (loggedInVolunteer == null) {
                loggedInVolunteer = new LoggedInVolunteer();
                loggedInVolunteer.setVolunteer(volunteer);
                loggedInVolunteer.setLoggedin(new Date());
                sessionFactory.getCurrentSession().save(loggedInVolunteer);
            }
            else {
                loggedInVolunteer.setLoggedin(new Date());
                loggedInVolunteer.setLoggedout(null);
                sessionFactory.getCurrentSession().update(loggedInVolunteer);
            }

            return Login.Success;
        }

        return Login.InvalidUsernamePassword;
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

    @Override
    public List<AccessFilter> getAccessFilterList (Integer volunteerId)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(AccessFilter.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        Volunteer volunteer = (Volunteer) lookup(volunteerId, Volunteer.class);

        criteria.add(Restrictions.eq("volunteer", volunteer));

        List<AccessFilter> accessFilterList = criteria.list();

        session.flush();
        session.close();

        return accessFilterList;
    }

    @Override
    public List<AccessControl> getAccessControlList (Integer volunteerId)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(AccessControl.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        Volunteer volunteer = (Volunteer) lookup(volunteerId, Volunteer.class);

        criteria.add(Restrictions.eq("volunteer", volunteer));

        List<AccessControl> accessControlList = criteria.list();

        session.flush();
        session.close();

        return accessControlList;
    }

}