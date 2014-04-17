/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.common.helper;

import java.io.Serializable;

public class Access implements Serializable
{
    private boolean isAdmin = false;
    private boolean isSpotRegVolunteer = false;
    private boolean isRegVolunteer = false;
    private boolean isInfoVolunteer = false;

    public boolean isAdmin ()
    {
        return isAdmin;
    }

    public void setAdmin (boolean isAdmin)
    {
        this.isAdmin = isAdmin;
    }

    public boolean isSpotRegVolunteer ()
    {
        return isSpotRegVolunteer;
    }

    public void setSpotRegVolunteer (boolean isSpotRegVolunteer)
    {
        this.isSpotRegVolunteer = isSpotRegVolunteer;
    }

    public boolean isRegVolunteer ()
    {
        return isRegVolunteer;
    }

    public void setRegVolunteer (boolean isRegVolunteer)
    {
        this.isRegVolunteer = isRegVolunteer;
    }

    public boolean isInfoVolunteer ()
    {
        return isInfoVolunteer;
    }

    public void setInfoVolunteer (boolean isInfoVolunteer)
    {
        this.isInfoVolunteer = isInfoVolunteer;
    }
}
