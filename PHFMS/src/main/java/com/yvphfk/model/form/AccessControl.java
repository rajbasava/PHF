/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model.form;

import com.yvphfk.common.Util;
import com.yvphfk.common.VolunteerPermission;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name = "phk_accesscontrol")
@org.hibernate.annotations.Entity
public class AccessControl extends BaseForm
{
    public static final String ClassName = "com.yvphfk.model.form.AccessControl";

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "volunteer")
    private Volunteer volunteer;

    @Column(name = "permission")
    private String permission;

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

    public Volunteer getVolunteer ()
    {
        return volunteer;
    }

    public void setVolunteer (Volunteer volunteer)
    {
        this.volunteer = volunteer;
    }

    public String getPermission ()
    {
        return permission;
    }

    public void setPermission (String permission)
    {
        this.permission = permission;
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

    public String getPermissionName ()
    {
        if (Util.nullOrEmptyOrBlank(getPermission())) {
            return null;
        }
        return VolunteerPermission.getName(getPermission());
    }

}
