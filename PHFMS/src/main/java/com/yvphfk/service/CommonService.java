/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.service;


import com.yvphfk.model.form.BaseForm;

import java.util.List;

public interface CommonService
{
    public void saveOrUpdate (BaseForm form);

    public void saveOrUpdate (List<BaseForm> forms);

    public BaseForm lookup (Integer id, Class clazz);

    public void delete (Integer id, Class clazz);

    public void createAndAddHistoryRecord (String comment, String preparedBy, BaseForm object);

}
