/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model.dao;

import com.yvphfk.model.form.BaseForm;
import com.yvphfk.model.form.PHFoundation;

public interface CommonDAO
{
    public void saveOrUpdate (BaseForm form);

    public PHFoundation getFoundation (Integer foundationId);

    public PHFoundation getFoundation (String shortName);
}
