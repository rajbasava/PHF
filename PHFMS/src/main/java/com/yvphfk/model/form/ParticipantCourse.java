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
@Table(name = "phk_partipantcourse")
@Immutable
@org.hibernate.annotations.Entity
public class ParticipantCourse extends BaseForm
{
    public static final String ClassName = "com.yvphfk.model.form.ParticipantCourse";

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "participant")
    private Participant participant;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coursetype")
    private CourseType courseType;

    @Column(name = "startdate")
    private Date startDate;

    @Column(name = "enddate")
    private Date endDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "primarytrainer")
    private Trainer primaryTrainer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "secondarytrainer")
    private Trainer secondaryTrainer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "foundation")
    private PHFoundation foundation;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "coursecertificate")
    private String coursecertificate;

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
    private Integer primaryTrainerId;

    @Transient
    private Integer secondaryTrainerId;

    @Transient
    private Integer foundationId;

    public ParticipantCourse ()
    {
        setFoundationId(getDefaultFoundationId());
    }

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

    public Participant getParticipant ()
    {
        return participant;
    }

    public void setParticipant (Participant participant)
    {
        this.participant = participant;
    }

    public CourseType getCourseType ()
    {
        return courseType;
    }

    public void setCourseType (CourseType courseType)
    {
        this.courseType = courseType;
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

    public Trainer getPrimaryTrainer ()
    {
        return primaryTrainer;
    }

    public void setPrimaryTrainer (Trainer primaryTrainer)
    {
        this.primaryTrainer = primaryTrainer;
    }

    public Trainer getSecondaryTrainer ()
    {
        return secondaryTrainer;
    }

    public void setSecondaryTrainer (Trainer secondaryTrainer)
    {
        this.secondaryTrainer = secondaryTrainer;
    }

    public PHFoundation getFoundation ()
    {
        return foundation;
    }

    public void setFoundation (PHFoundation foundation)
    {
        this.foundation = foundation;
    }

    public String getCity ()
    {
        return city;
    }

    public void setCity (String city)
    {
        this.city = city;
    }

    public String getState ()
    {
        return state;
    }

    public void setState (String state)
    {
        this.state = state;
    }

    public String getCoursecertificate ()
    {
        return coursecertificate;
    }

    public void setCoursecertificate (String coursecertificate)
    {
        this.coursecertificate = coursecertificate;
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

    public Integer getPrimaryTrainerId ()
    {
        return primaryTrainerId;
    }

    public void setPrimaryTrainerId (Integer primaryTrainerId)
    {
        this.primaryTrainerId = primaryTrainerId;
    }

    public Integer getSecondaryTrainerId ()
    {
        return secondaryTrainerId;
    }

    public void setSecondaryTrainerId (Integer secondaryTrainerId)
    {
        this.secondaryTrainerId = secondaryTrainerId;
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
