/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model.form;

import com.yvphfk.common.AppProperties;
import com.yvphfk.common.ApplicationContextUtils;
import com.yvphfk.model.Importable;
import org.springframework.context.ApplicationContext;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseForm implements Serializable, Importable
{

    public void initialize (String email)
    {
        setPreparedBy(email);
        setTimeCreated(new Date());
        setTimeUpdated(new Date());
        setActive(true);
    }

    public void initializeForUpdate (String email)
    {
        setTimeUpdated(new Date());
        setActive(true);
    }

    public abstract Integer getId ();

    public abstract String getType ();

    public void setPreparedBy (String preparedBy)
    {

    }

    public void setTimeCreated (Date timeCreated)
    {

    }

    public void setTimeUpdated (Date timeUpdated)
    {

    }

    public void setActive (boolean active)
    {

    }

    public void initializeForImport (String email)
    {

    }

    public void applyEvent (Event event)
    {

    }

    protected Integer getDefaultFoundationId ()
    {
        ApplicationContext context = ApplicationContextUtils.getApplicationContext();

        if (context == null) {
            return null;
        }

        AppProperties appProperties =
                (AppProperties) context.getBean("appProperties");
        if (appProperties != null) {
            return appProperties.getDefaultFoundationId();
        }

        return null;
    }
}
