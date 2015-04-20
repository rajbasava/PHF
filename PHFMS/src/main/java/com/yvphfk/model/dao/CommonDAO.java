/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model.dao;

import com.yvphfk.model.form.BaseForm;
import com.yvphfk.model.form.PHFoundation;

import java.util.List;

public interface CommonDAO
{
    public void saveOrUpdate (BaseForm form);

    public void saveOrUpdate (List<BaseForm> objs);

    public PHFoundation getFoundation (Integer foundationId);

    public PHFoundation getFoundation (String shortName);

    public BaseForm lookup (Integer id, Class clazz);

    public void delete (Integer id, Class clazz);

    public void createAndAddHistoryRecord (String comment, String preparedBy, BaseForm object);
}
