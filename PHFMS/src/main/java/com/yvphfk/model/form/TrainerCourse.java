/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model.form;

import org.hibernate.annotations.Immutable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Table(name = "phk_trainercourse")
@Immutable
@org.hibernate.annotations.Entity
public class TrainerCourse extends BaseForm
{
    public static final String ClassName = "com.yvphfk.model.form.TrainerCourse";

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "trainer")
    private Trainer trainer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coursetype")
    private CourseType courseType;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "foundation")
    private PHFoundation foundation;

    @Column(name = "apprentice", columnDefinition = "default false")
    private boolean apprentice;

    @Column(name = "contractstartdate")
    private Date contractStartDate;

    @Column(name = "contractenddate")
    private Date contractEndDate;

    @Column(name = "PREPAREDBY", updatable = false)
    private String preparedBy;

    @Column(name = "TIMECREATED", updatable = false)
    private Date timeCreated;

    @Column(name = "TIMEUPDATED")
    private Date timeUpdated;

    @Column(name = "ACTIVE")
    private boolean active;

    @Transient
    private Integer trainerId;

    @Transient
    private Integer courseTypeId;

    @Transient
    private Integer foundationId;

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

    public Trainer getTrainer ()
    {
        return trainer;
    }

    public void setTrainer (Trainer trainer)
    {
        this.trainer = trainer;
    }

    public CourseType getCourseType ()
    {
        return courseType;
    }

    public void setCourseType (CourseType courseType)
    {
        this.courseType = courseType;
    }

    public PHFoundation getFoundation ()
    {
        return foundation;
    }

    public void setFoundation (PHFoundation foundation)
    {
        this.foundation = foundation;
    }

    public boolean isApprentice ()
    {
        return apprentice;
    }

    public void setApprentice (boolean apprentice)
    {
        this.apprentice = apprentice;
    }

    public Date getContractStartDate ()
    {
        return contractStartDate;
    }

    public void setContractStartDate (Date contractStartDate)
    {
        this.contractStartDate = contractStartDate;
    }

    public Date getContractEndDate ()
    {
        return contractEndDate;
    }

    public void setContractEndDate (Date contractEndDate)
    {
        this.contractEndDate = contractEndDate;
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

    public Integer getTrainerId ()
    {
        return trainerId;
    }

    public void setTrainerId (Integer trainerId)
    {
        this.trainerId = trainerId;
    }

    public Integer getCourseTypeId ()
    {
        return courseTypeId;
    }

    public void setCourseTypeId (Integer courseTypeId)
    {
        this.courseTypeId = courseTypeId;
    }

    public Integer getFoundationId ()
    {
        return foundationId;
    }

    public void setFoundationId (Integer foundationId)
    {
        this.foundationId = foundationId;
    }
}
