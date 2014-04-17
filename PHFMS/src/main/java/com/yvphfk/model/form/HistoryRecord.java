/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.model.form;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "phk_history")
public class HistoryRecord extends BaseForm
{
    public static final String ClassName = "com.yvphfk.model.form.HistoryRecord";

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @Column(name = "OBJECTID")
    private Integer objectId;

    @Column(name = "OBJECTTYPE")
    private String objectType;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "PREPAREDBY")
    private String preparedBy;

    @Column(name = "TIMECREATED")
    private Date timeCreated;

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

    public Integer getObjectId ()
    {
        return objectId;
    }

    public void setObjectId (Integer objectId)
    {
        this.objectId = objectId;
    }

    public String getObjectType ()
    {
        return objectType;
    }

    public void setObjectType (String objectType)
    {
        this.objectType = objectType;
    }

    public String getComment ()
    {
        return comment;
    }

    public void setComment (String comment)
    {
        this.comment = comment;
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

    public void setObject (BaseForm form)
    {
        setObjectId(form.getId());
        setObjectType(form.getType());
    }
}