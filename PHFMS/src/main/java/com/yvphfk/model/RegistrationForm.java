/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model;

import com.yvphfk.model.form.Event;

import java.util.Date;

public class RegistrationForm implements Importable
{
    private Integer registrationId;

    private Integer refOrder;

    private Date registrationDate;

    public Integer getRegistrationId ()
    {
        return registrationId;
    }

    public void setRegistrationId (Integer registrationId)
    {
        this.registrationId = registrationId;
    }

    public Integer getRefOrder ()
    {
        return refOrder;
    }

    public void setRefOrder (Integer refOrder)
    {
        this.refOrder = refOrder;
    }

    public Date getRegistrationDate ()
    {
        return registrationDate;
    }

    public void setRegistrationDate (Date registrationDate)
    {
        this.registrationDate = registrationDate;
    }

    public void initializeForImport (String email)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void applyEvent (Event event)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
