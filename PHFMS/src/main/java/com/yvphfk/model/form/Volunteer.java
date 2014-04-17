/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.model.form;

import com.yvphfk.common.Util;
import com.yvphfk.common.VolunteerPermission;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "phk_volunteer")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class Volunteer extends BaseForm
{
    public static final String ClassName = "com.yvphfk.model.form.Volunteer";
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "PERMISSION")
    private String permission;

    @Column(name = "ACTIVITY")
    private String activity;

    @Column(name = "PREPAREDBY", updatable = false)
    private String preparedBy;

    @Column(name = "TIMECREATED", updatable = false)
    private Date timecreated;

    @Column(name = "TIMEUPDATED")
    private Date timeupdated;

    @OneToOne(mappedBy = "volunteer")
    private LoggedInVolunteer loggedInVolunteer;


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

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getMobile ()
    {
        return mobile;
    }

    public void setMobile (String mobile)
    {
        this.mobile = mobile;
    }

    public String getPermission ()
    {
        return permission;
    }

    public String getPermissionName ()
    {
        if (Util.nullOrEmptyOrBlank(getPermission())) {
            return null;
        }
        return VolunteerPermission.getName(getPermission());
    }

    public void setPermission (String permission)
    {
        this.permission = permission;
    }

    public String getActivity ()
    {
        return activity;
    }

    public void setActivity (String activity)
    {
        this.activity = activity;
    }

    public String getPreparedBy ()
    {
        return preparedBy;
    }

    public void setPreparedBy (String preparedBy)
    {
        this.preparedBy = preparedBy;
    }

    public Date getTimecreated ()
    {
        return timecreated;
    }

    public void setTimecreated (Date timecreated)
    {
        this.timecreated = timecreated;
    }

    public Date getTimeupdated ()
    {
        return timeupdated;
    }

    public void setTimeupdated (Date timeupdated)
    {
        this.timeupdated = timeupdated;
    }

    public LoggedInVolunteer getLogin ()
    {
        return loggedInVolunteer;
    }

    public void setLogin (LoggedInVolunteer loggedInVolunteer)
    {
        this.loggedInVolunteer = loggedInVolunteer;
    }
}