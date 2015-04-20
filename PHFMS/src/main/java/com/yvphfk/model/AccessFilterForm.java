/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model;

import java.io.Serializable;
import java.util.List;

public class AccessFilterForm implements Serializable
{
    private Integer volunteerId;
    private Integer eventId;
    private List<Integer> foundationIds;

    public Integer getVolunteerId ()
    {
        return volunteerId;
    }

    public void setVolunteerId (Integer volunteerId)
    {
        this.volunteerId = volunteerId;
    }

    public Integer getEventId ()
    {
        return eventId;
    }

    public void setEventId (Integer eventId)
    {
        this.eventId = eventId;
    }

    public List<Integer> getFoundationIds ()
    {
        return foundationIds;
    }

    public void setFoundationIds (List<Integer> foundationIds)
    {
        this.foundationIds = foundationIds;
    }
}
