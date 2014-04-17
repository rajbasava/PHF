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
@Table(name = "phk_rowmeta")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class RowMeta extends BaseForm
{
    public static final String ClassName = "com.yvphfk.model.form.Event";

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SORTORDER")
    private int sortOrder;

    @Column(name = "ROWNAME")
    private String rowName;

    @Column(name = "ROWMAX")
    private int rowMax;

    @Column(name = "ROWFULL")
    private boolean rowFull;

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

    public int getSortOrder ()
    {
        return sortOrder;
    }

    public void setSortOrder (int sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    public String getRowName ()
    {
        return rowName;
    }

    public void setRowName (String rowName)
    {
        this.rowName = rowName;
    }

    public int getRowMax ()
    {
        return rowMax;
    }

    public void setRowMax (int rowMax)
    {
        this.rowMax = rowMax;
    }

    public boolean isRowFull ()
    {
        return rowFull;
    }

    public void setRowFull (boolean rowFull)
    {
        this.rowFull = rowFull;
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

    public void initializeForImport (String email)
    {
        initialize(email);
    }

}
