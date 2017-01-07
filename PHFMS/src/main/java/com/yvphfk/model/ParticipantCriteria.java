/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model;

import com.yvphfk.common.Util;

public class ParticipantCriteria
{
    private Integer participantId;
    private String name;
    private String mobile;
    private String email;
    private Integer foundationId;
    private boolean vip;
    private Integer courseTypeId;
    private Integer excludeEventId;
    private int maxResults = Util.MaxResultCount;

    public Integer getParticipantId ()
    {
        return participantId;
    }

    public void setParticipantId (Integer participantId)
    {
        this.participantId = participantId;
    }

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

    public Integer getFoundationId ()
    {
        return foundationId;
    }

    public void setFoundationId (Integer foundationId)
    {
        this.foundationId = foundationId;
    }

    public boolean isVip ()
    {
        return vip;
    }

    public void setVip (boolean vip)
    {
        this.vip = vip;
    }

    public Integer getCourseTypeId ()
    {
        return courseTypeId;
    }

    public void setCourseTypeId (Integer courseTypeId)
    {
        this.courseTypeId = courseTypeId;
    }

    public int getMaxResults ()
    {
        return maxResults;
    }

    public void setMaxResults (int maxResults)
    {
        this.maxResults = maxResults;
    }

    public Integer getExcludeEventId() {
        return excludeEventId;
    }

    public void setExcludeEventId(Integer excludeEventId) {
        this.excludeEventId = excludeEventId;
    }
}
