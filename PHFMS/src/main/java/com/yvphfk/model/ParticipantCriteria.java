/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model;

public class ParticipantCriteria
{
    private String name;
    private String mobile;
    private String email;
    private String level;
    private String foundation;
    private boolean vip;

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

    public String getLevel ()
    {
        return level;
    }

    public void setLevel (String level)
    {
        this.level = level;
    }

    public String getFoundation ()
    {
        return foundation;
    }

    public void setFoundation (String foundation)
    {
        this.foundation = foundation;
    }

    public boolean isVip ()
    {
        return vip;
    }

    public void setVip (boolean vip)
    {
        this.vip = vip;
    }
}
