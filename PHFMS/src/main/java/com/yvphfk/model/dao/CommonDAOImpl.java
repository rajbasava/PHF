/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model.dao;

import com.yvphfk.model.form.BaseForm;
import com.yvphfk.model.form.PHFoundation;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CommonDAOImpl implements CommonDAO
{
    @Autowired
    private SessionFactory sessionFactory;

    public void saveOrUpdate (BaseForm form)
    {
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(form);
        session.flush();
        session.close();
    }

    @Override
    public PHFoundation getFoundation (Integer foundationId)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(PHFoundation.class);

        criteria.add(Restrictions.eq("id", foundationId));
        criteria.add(Restrictions.eq("active", true));
        List<PHFoundation> events = criteria.list();

        session.close();
        if (events == null ||
                events.isEmpty()) {
            return null;
        }

        return events.get(0);
    }
}
