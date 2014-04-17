/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model.form;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Table(name = "phk_volkit")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class VolunteerKit extends BaseForm
{
    public static final String ClassName = "com.yvphfk.model.form.VolunteerKit";

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "KITID")
    private Kit kit;

    @ManyToOne
    @JoinColumn(name = "VOLLOGINID")
    private LoggedInVolunteer loggedInVolunteer;

    @Column(name = "KITCOUNT")
    private int kitCount;

    @Column(name = "KITSGIVEN")
    private int kitsGiven;

    @Column(name = "PREPAREDBY", updatable = false)
    private String preparedBy;

    @Column(name = "TIMECREATED", updatable = false)
    private Date timeCreated;

    @Column(name = "TIMEUPDATED")
    private Date timeUpdated;

    @Column(name = "ACTIVE")
    private boolean active;

    @Transient
    private Integer volunteerId;

    @Transient
    private Integer allotKits;

    @Override
    public String getType ()
    {
        return ClassName;
    }

    public Integer getId ()
    {
        return id;
    }

    public void setId (Integer id)
    {
        this.id = id;
    }

    public Kit getKit ()
    {
        return kit;
    }

    public void setKit (Kit kit)
    {
        this.kit = kit;
    }

    public LoggedInVolunteer getLoggedInVolunteer ()
    {
        return loggedInVolunteer;
    }

    public void setLoggedInVolunteer (LoggedInVolunteer loggedInVolunteer)
    {
        this.loggedInVolunteer = loggedInVolunteer;
    }

    public int getKitCount ()
    {
        return kitCount;
    }

    public void setKitCount (int kitCount)
    {
        this.kitCount = kitCount;
    }

    public int getKitsGiven ()
    {
        return kitsGiven;
    }

    public void setKitsGiven (int kitsGiven)
    {
        this.kitsGiven = kitsGiven;
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

    public Integer getVolunteerId ()
    {
        return volunteerId;
    }

    public void setVolunteerId (Integer volunteerId)
    {
        this.volunteerId = volunteerId;
    }

    public Integer getAllotKits ()
    {
        return allotKits;
    }

    public void setAllotKits (Integer allotKits)
    {
        this.allotKits = allotKits;
    }
}
