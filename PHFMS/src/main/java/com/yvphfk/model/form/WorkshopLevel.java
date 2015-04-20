/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model.form;

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
@Table(name = "phk_workshoplevel")
@org.hibernate.annotations.Entity
public class WorkshopLevel extends BaseForm
{
    public static final String ClassName = "com.yvphfk.model.form.WorkshopLevel";

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event")
    private Event event;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coursetype")
    private CourseType courseType;

    @Column(name = "levelorder")
    private int levelOrder;

    @Column(name = "start")
    private boolean start = false;

    @Column(name = "PREPAREDBY", updatable = false)
    private String preparedBy;

    @Column(name = "TIMECREATED", updatable = false)
    private Date timeCreated;

    @Column(name = "TIMEUPDATED")
    private Date timeUpdated;

    @Column(name = "ACTIVE")
    private boolean active;

    @Transient
    private Integer courseTypeId;

    @Transient
    private Integer eventId;

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

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public Event getEvent ()
    {
        return event;
    }

    public void setEvent (Event event)
    {
        this.event = event;
    }

    public CourseType getCourseType ()
    {
        return courseType;
    }

    public void setCourseType (CourseType courseType)
    {
        this.courseType = courseType;
    }

    public int getLevelOrder ()
    {
        return levelOrder;
    }

    public void setLevelOrder (int levelOrder)
    {
        this.levelOrder = levelOrder;
    }

    public boolean isStart ()
    {
        return start;
    }

    public void setStart (boolean start)
    {
        this.start = start;
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

    public Integer getCourseTypeId ()
    {
        return courseTypeId;
    }

    public void setCourseTypeId (Integer courseTypeId)
    {
        this.courseTypeId = courseTypeId;
    }

    public Integer getEventId ()
    {
        return eventId;
    }

    public void setEventId (Integer eventId)
    {
        this.eventId = eventId;
    }
}
