/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.model;

import com.yvphfk.common.VolunteerPermission;
import com.yvphfk.common.helper.Access;

import java.io.Serializable;

public class Login implements Serializable
{
    public static final String ClassName = "com.yvphfk.model.Login";

    private String email;
    private String name;
    private String password;
    private String counter;
    private String permission;
    private Integer volunteerId;
    private long lastAccessed;
    private Access access = new Access();

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

    public String getCounter ()
    {
        return counter;
    }

    public void setCounter (String counter)
    {
        this.counter = counter;
    }

    public String getPermission ()
    {
        return permission;
    }

    /**
     set user permission which can be accessed in jstl tags in the views

     @param permission
     */
    public void setPermission (String permission)
    {
        this.access.setAdmin(VolunteerPermission.isAdmin(permission));
        this.access.setSpotRegVolunteer(VolunteerPermission.isSpotRegVolunteeer(permission));
        this.access.setRegVolunteer(VolunteerPermission.isRegVolunteer(permission));
        this.access.setInfoVolunteer(VolunteerPermission.isInfoVolunteer(permission));

        this.permission = permission;
    }

    public Integer getVolunteerId ()
    {
        return volunteerId;
    }

    public void setVolunteerId (Integer volunteerId)
    {
        this.volunteerId = volunteerId;
    }

    public long getLastAccessed ()
    {
        return lastAccessed;
    }

    public void setLastAccessed (long lastAccessed)
    {
        this.lastAccessed = lastAccessed;
    }

    public Access getAccess ()
    {
        return access;
    }

    public void setAccess (Access access)
    {
        this.access = access;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }
}
