/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.model;

import com.yvphfk.model.form.EventRegistration;

import java.io.Serializable;
import java.util.Date;

public class RegistrationCriteria implements Serializable
{
    private String name;
    private String mobile;
    private String email;
    private Integer courseTypeId;
    private String foundation;
    private Integer seat;
    private String amountPaidCategory;
    private String reference;
    private Integer eventId;
    private boolean consolidated;
    private boolean vip;
    private String foodCoupon = "";
    private String eventKit = "";
    private Date fromRegistrationDate;
    private Date toRegistrationDate;
    private String status = EventRegistration.StatusRegistered;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getMobile ()
    {
        return mobile;
    }

    public void setMobile (String mobile)
    {
        this.mobile = mobile;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public Integer getCourseTypeId ()
    {
        return courseTypeId;
    }

    public void setCourseTypeId (Integer courseTypeId)
    {
        this.courseTypeId = courseTypeId;
    }

    public String getFoundation ()
    {
        return foundation;
    }

    public void setFoundation (String foundation)
    {
        this.foundation = foundation;
    }

    public Integer getSeat ()
    {
        return seat;
    }

    public void setSeat (Integer seat)
    {
        this.seat = seat;
    }

    public String getAmountPaidCategory ()
    {
        return amountPaidCategory;
    }

    public void setAmountPaidCategory (String amountPaidCategory)
    {
        this.amountPaidCategory = amountPaidCategory;
    }

    public String getReference ()
    {
        return reference;
    }

    public void setReference (String reference)
    {
        this.reference = reference;
    }

    public Integer getEventId ()
    {
        return eventId;
    }

    public void setEventId (Integer eventId)
    {
        this.eventId = eventId;
    }

    public boolean isConsolidated ()
    {
        return consolidated;
    }

    public void setConsolidated (boolean consolidated)
    {
        this.consolidated = consolidated;
    }

    public boolean isVip ()
    {
        return vip;
    }

    public void setVip (boolean vip)
    {
        this.vip = vip;
    }

    public Date getFromRegistrationDate ()
    {
        return fromRegistrationDate;
    }

    public void setFromRegistrationDate (Date fromRegistrationDate)
    {
        this.fromRegistrationDate = fromRegistrationDate;
    }

    public Date getToRegistrationDate ()
    {
        return toRegistrationDate;
    }

    public void setToRegistrationDate (Date toRegistrationDate)
    {
        this.toRegistrationDate = toRegistrationDate;
    }

    public String getFoodCoupon ()
    {
        return foodCoupon;
    }

    public void setFoodCoupon (String foodCoupon)
    {
        this.foodCoupon = foodCoupon;
    }

    public String getEventKit ()
    {
        return eventKit;
    }

    public void setEventKit (String eventKit)
    {
        this.eventKit = eventKit;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }
}