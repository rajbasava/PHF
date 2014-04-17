/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model.dao;

import com.yvphfk.model.form.BaseForm;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
}
