/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model.form;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "phk_eventpmt")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class EventPayment extends BaseForm
{
    public static final String ClassName = "com.yvphfk.model.form.EventPayment";

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @Column(name = "AMOUNTPAID")
    private Long amountPaid;

    @Column(name = "PMTMODE")
    private String mode;

    @Column(name = "RECEIPTINFO")
    private String receiptInfo;

    @Column(name = "RECEIPTDATE")
    private Date receiptDate;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "PDCNOTCLEAR")
    private boolean pdcNotClear;

    @Column(name = "POSTDTCHQ")
    private String pdc;

    @Column(name = "POSTDTCHQDATE")
    private Date pdcDate;

    @Column(name = "PREPAREDBY", updatable = false)
    private String preparedBy;

    @Column(name = "TIMECREATED", updatable = false)
    private Date timeCreated;

    @Column(name = "TIMEUPDATED")
    private Date timeUpdated;

    @Column(name = "ACTIVE")
    private boolean active;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EVENTREGSTRNID")
    private EventRegistration registration;

    public Integer getId ()
    {
        return id;
    }

    public void setId (Integer id)
    {
        this.id = id;
    }

    @Override
    public String getType ()
    {
        return ClassName;
    }

    public Long getAmountPaid ()
    {
        return amountPaid;
    }

    public void setAmountPaid (Long amountPaid)
    {
        this.amountPaid = amountPaid;
    }

    public String getMode ()
    {
        return mode;
    }

    public void setMode (String mode)
    {
        this.mode = mode;
    }

    public String getReceiptInfo ()
    {
        return receiptInfo;
    }

    public void setReceiptInfo (String receiptInfo)
    {
        this.receiptInfo = receiptInfo;
    }

    public Date getReceiptDate ()
    {
        return receiptDate;
    }

    public void setReceiptDate (Date receiptDate)
    {
        this.receiptDate = receiptDate;
    }

    public String getRemarks ()
    {
        return remarks;
    }

    public void setRemarks (String remarks)
    {
        this.remarks = remarks;
    }

    public boolean isPdcNotClear ()
    {
        return pdcNotClear;
    }

    public void setPdcNotClear (boolean pdcNotClear)
    {
        this.pdcNotClear = pdcNotClear;
    }

    public String getPdc ()
    {
        return pdc;
    }

    public void setPdc (String pdc)
    {
        this.pdc = pdc;
    }

    public Date getPdcDate ()
    {
        return pdcDate;
    }

    public void setPdcDate (Date pdcDate)
    {
        this.pdcDate = pdcDate;
    }

    public String getPreparedBy ()
    {
        return preparedBy;
    }

    public void setPreparedBy (String preparedBy)
    {
        this.preparedBy = preparedBy;
    }

    public Date getTimeCreated ()
    {
        return timeCreated;
    }

    public void setTimeCreated (Date timeCreated)
    {
        this.timeCreated = timeCreated;
    }

    public Date getTimeUpdated ()
    {
        return timeUpdated;
    }

    public void setTimeUpdated (Date timeUpdated)
    {
        this.timeUpdated = timeUpdated;
    }

    public boolean isActive ()
    {
        return active;
    }

    public void setActive (boolean active)
    {
        this.active = active;
    }

    public EventRegistration getRegistration ()
    {
        return registration;
    }

    public void setRegistration (EventRegistration registration)
    {
        this.registration = registration;
    }

}
