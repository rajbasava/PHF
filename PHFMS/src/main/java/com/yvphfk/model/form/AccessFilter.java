/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model.form;


import com.yvphfk.common.Util;
import com.yvphfk.common.VolunteerPermission;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Table(name = "phk_accessfilter")
@org.hibernate.annotations.Entity
public class AccessFilter extends BaseForm
{
    public static final String ClassName = "com.yvphfk.model.form.AccessFilter";

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event")
    private Event event;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "volunteer")
    private Volunteer volunteer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "foundation")
    private PHFoundation foundation;

    @Column(name = "PREPAREDBY", updatable = false)
    private String preparedBy;

    @Column(name = "TIMECREATED", updatable = false)
    private Date timeCreated;

    @Column(name = "TIMEUPDATED")
    private Date timeUpdated;

    @Column(name = "ACTIVE")
    private boolean active;

    @Override
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

    public Volunteer getVolunteer ()
    {
        return volunteer;
    }

    public void setVolunteer (Volunteer volunteer)
    {
        this.volunteer = volunteer;
    }

    public PHFoundation getFoundation ()
    {
        return foundation;
    }

    public void setFoundation (PHFoundation foundation)
    {
        this.foundation = foundation;
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

}
