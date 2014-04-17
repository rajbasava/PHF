/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.model;

import java.io.Serializable;
import java.util.Date;

public class EventCriteria implements Serializable
{
    private String name;
    private Integer courseTypeId;
    private Integer primaryTrainerId;
    private Integer eventType;
    private Date startDate;
    private Date endDate;
    private String venue;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public Integer getCourseTypeId ()
    {
        return courseTypeId;
    }

    public void setCourseTypeId (Integer courseTypeId)
    {
        this.courseTypeId = courseTypeId;
    }

    public Integer getPrimaryTrainerId ()
    {
        return primaryTrainerId;
    }

    public void setPrimaryTrainerId (Integer primaryTrainerId)
    {
        this.primaryTrainerId = primaryTrainerId;
    }

    public Integer getEventType ()
    {
        return eventType;
    }

    public void setEventType (Integer eventType)
    {
        this.eventType = eventType;
    }

    public Date getStartDate ()
    {
        return startDate;
    }

    public void setStartDate (Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate ()
    {
        return endDate;
    }

    public void setEndDate (Date endDate)
    {
        this.endDate = endDate;
    }

    public String getVenue ()
    {
        return venue;
    }

    public void setVenue (String venue)
    {
        this.venue = venue;
    }
}
