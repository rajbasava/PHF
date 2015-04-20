/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model.form;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Table(name = "phk_eventfee")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class EventFee extends BaseForm
{
    public static final String ClassName = "com.yvphfk.model.form.EventFee";

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "EVENTID")
    private Event event;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AMOUNT")
    private Long amount;

    @Column(name = "CUTOFFDATE")
    private Date cutOffDate;

    @Column(name = "REVIEW")
    private boolean review;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "workshopLevel")
    private WorkshopLevel workshopLevel;

    @Column(name = "PREPAREDBY", updatable = false)
    private String preparedBy;

    @Column(name = "TIMECREATED", updatable = false)
    private Date timeCreated;

    @Column(name = "TIMEUPDATED")
    private Date timeUpdated;

    @Column(name = "ACTIVE")
    private boolean active;

    @Transient
    private Integer eventId;

    @Transient
    private Integer workshopLevelId;

    public Integer getId ()
    {
        return id;
    }

    @Override
    public String getType ()
    {
        return ClassName;
    }

    public void setId (Integer id)
    {
        this.id = id;
    }

    public Event getEvent ()
    {
        return event;
    }

    public void setEvent (Event event)
    {
        this.event = event;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public Long getAmount ()
    {
        return amount;
    }

    public void setAmount (Long amount)
    {
        this.amount = amount;
    }

    public Date getCutOffDate ()
    {
        return cutOffDate;
    }

    public void setCutOffDate (Date cutOffDate)
    {
        this.cutOffDate = cutOffDate;
    }

    public boolean isReview ()
    {
        return review;
    }

    public void setReview (boolean review)
    {
        this.review = review;
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

    public Integer getEventId ()
    {
        return eventId;
    }

    public void setEventId (Integer eventId)
    {
        this.eventId = eventId;
    }

    public WorkshopLevel getWorkshopLevel ()
    {
        return workshopLevel;
    }

    public void setWorkshopLevel (WorkshopLevel workshopLevel)
    {
        this.workshopLevel = workshopLevel;
    }

    public Integer getWorkshopLevelId ()
    {
        return workshopLevelId;
    }

    public void setWorkshopLevelId (Integer workshopLevelId)
    {
        this.workshopLevelId = workshopLevelId;
    }
}
