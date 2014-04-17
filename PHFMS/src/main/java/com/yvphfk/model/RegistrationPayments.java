/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model;

import com.yvphfk.model.form.EventPayment;
import com.yvphfk.model.form.EventRegistration;

import java.util.List;

public class RegistrationPayments
{
    public static final String Add = "Add";
    public static final String Update = "Update";

    private Integer registrationId;
    private EventRegistration registration;
    private EventPayment currentPayment;
    private List<EventPayment> payments;
    private String action = Add;
    private Integer updatePaymentId;

    public Integer getRegistrationId ()
    {
        return registrationId;
    }

    public void setRegistrationId (Integer registrationId)
    {
        this.registrationId = registrationId;
    }

    public EventRegistration getRegistration ()
    {
        return registration;
    }

    public void setRegistration (EventRegistration registration)
    {
        this.registration = registration;
    }

    public EventPayment getCurrentPayment ()
    {
        return currentPayment;
    }

    public void setCurrentPayment (EventPayment currentPayment)
    {
        this.currentPayment = currentPayment;
    }

    public List<EventPayment> getPayments ()
    {
        return payments;
    }

    public void setPayments (List<EventPayment> payments)
    {
        this.payments = payments;
    }

    public String getAction ()
    {
        return action;
    }

    public void setAction (String action)
    {
        this.action = action;
    }

    public Integer getUpdatePaymentId ()
    {
        return updatePaymentId;
    }

    public void setUpdatePaymentId (Integer updatePaymentId)
    {
        this.updatePaymentId = updatePaymentId;
    }
}
