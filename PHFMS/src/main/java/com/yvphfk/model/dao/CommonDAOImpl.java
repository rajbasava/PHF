/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model.dao;

import com.yvphfk.model.form.AccessFilter;
import com.yvphfk.model.form.BaseForm;
import com.yvphfk.model.form.HistoryRecord;
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

    @Override
    public void saveOrUpdate (BaseForm form)
    {
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(form);
        session.flush();
        session.close();
    }

    @Override
    public void saveOrUpdate (List<BaseForm> objs)
    {
        Session session = sessionFactory.openSession();
        for(BaseForm form: objs) {
            session.saveOrUpdate(form);
        }
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

    @Override
    public PHFoundation getFoundation (String shortName)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(PHFoundation.class);

        criteria.add(Restrictions.eq("shortName", shortName));
        criteria.add(Restrictions.eq("active", true));
        List<PHFoundation> events = criteria.list();

        session.close();
        if (events == null ||
                events.isEmpty()) {
            return null;
        }

        return events.get(0);
    }

    @Override
    public BaseForm lookup (Integer id, Class clazz)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(clazz);

        criteria.add(Restrictions.eq("id", id));
        List objs = criteria.list();

        session.close();
        if (objs == null ||
                objs.isEmpty()) {
            return null;
        }

        return (BaseForm) objs.get(0);
    }

    @Override
    public void delete (Integer formId, Class clazz)
    {
        Session session = sessionFactory.openSession();

        BaseForm baseForm = lookup(formId, clazz);

        session.delete(baseForm);

        session.flush();
        session.close();
    }

    public void createAndAddHistoryRecord (String comment,
                                           String preparedBy,
                                           BaseForm object)
    {
        HistoryRecord record =
                createHistoryRecord(comment, preparedBy, object);
        saveOrUpdate(record);
    }

    public HistoryRecord createHistoryRecord (String comment,
                                              String preparedBy,
                                              BaseForm object)
    {
        HistoryRecord record = new HistoryRecord();
        record.setComment(comment);
        record.initialize(preparedBy);
        record.setObject(object);
        return record;
    }
}
