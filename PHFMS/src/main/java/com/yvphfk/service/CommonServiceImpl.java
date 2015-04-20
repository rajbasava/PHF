/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.service;

import com.yvphfk.model.dao.EventDAO;
import com.yvphfk.model.form.BaseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommonServiceImpl implements CommonService
{
    @Autowired
    private EventDAO eventDAO;

    @Override
    @Transactional
    public void saveOrUpdate (BaseForm form)
    {
        eventDAO.saveOrUpdate(form);
    }

    @Override
    @Transactional
    public void saveOrUpdate (List<BaseForm> forms)
    {
        eventDAO.saveOrUpdate(forms);
    }

    @Override
    @Transactional
    public BaseForm lookup (Integer id, Class clazz)
    {
        return eventDAO.lookup(id, clazz);
    }

    @Override
    @Transactional
    public void delete (Integer id, Class clazz)
    {
        eventDAO.delete(id, clazz);
    }

    @Override
    @Transactional
    public void createAndAddHistoryRecord (String comment, String preparedBy, BaseForm object)
    {
        eventDAO.createAndAddHistoryRecord(comment, preparedBy, object);
    }
}
