/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.model;

import java.io.Serializable;
import java.util.Date;

public class PaymentCriteria implements Serializable
{
    private Integer eventId;
    private String foundation;
    private Integer courseTypeId;
    private String mode;
    private String reference;
    private Date fromReceiptDate;
    private Date toReceiptDate;
    private boolean pdcNotClear;
    private Date fromPdcDate;
    private Date toPdcDate;

    public Integer getEventId ()
    {
        return eventId;
    }

    public void setEventId (Integer eventId)
    {
        this.eventId = eventId;
    }

    public String getFoundation ()
    {
        return foundation;
    }

    public void setFoundation (String foundation)
    {
        this.foundation = foundation;
    }

    public Integer getCourseTypeId ()
    {
        return courseTypeId;
    }

    public void setCourseTypeId (Integer courseTypeId)
    {
        this.courseTypeId = courseTypeId;
    }

    public String getMode ()
    {
        return mode;
    }

    public void setMode (String mode)
    {
        this.mode = mode;
    }

    public String getReference ()
    {
        return reference;
    }

    public void setReference (String reference)
    {
        this.reference = reference;
    }

    public Date getFromReceiptDate ()
    {
        return fromReceiptDate;
    }

    public void setFromReceiptDate (Date fromReceiptDate)
    {
        this.fromReceiptDate = fromReceiptDate;
    }

    public Date getToReceiptDate ()
    {
        return toReceiptDate;
    }

    public void setToReceiptDate (Date toReceiptDate)
    {
        this.toReceiptDate = toReceiptDate;
    }

    public boolean isPdcNotClear ()
    {
        return pdcNotClear;
    }

    public void setPdcNotClear (boolean pdcNotClear)
    {
        this.pdcNotClear = pdcNotClear;
    }

    public Date getFromPdcDate ()
    {
        return fromPdcDate;
    }

    public void setFromPdcDate (Date fromPdcDate)
    {
        this.fromPdcDate = fromPdcDate;
    }

    public Date getToPdcDate ()
    {
        return toPdcDate;
    }

    public void setToPdcDate (Date toPdcDate)
    {
        this.toPdcDate = toPdcDate;
    }
}
