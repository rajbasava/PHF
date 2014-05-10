/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model.form;

import com.yvphfk.common.SeatingType;
import com.yvphfk.common.Util;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "phk_event")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class Event extends BaseForm
{
    public static final String ClassName = "com.yvphfk.model.form.Event";
    public static final Integer EventTypeCourse = new Integer(1);
    public static final Integer EventTypeWorkshop = new Integer(2);

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "VENUE")
    private String venue;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "primaryeligibility")
    private CourseType primaryEligibility;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "secondaryeligibility")
    private CourseType secondaryEligibility;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "primarytrainer")
    private Trainer primaryTrainer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "secondarytrainer")
    private Trainer secondaryTrainer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coursetype")
    private CourseType courseType;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "foundation")
    private PHFoundation foundation;

    @Column(name = "eventtype")
    private Integer eventType;

    @Column(name = "STARTDATE")
    private Date startDate;

    @Column(name = "ENDDATE")
    private Date endDate;

    @Column(name = "isseatperlvl")
    private boolean seatPerLevel;

    @Column(name = "SEATALLOCATED")
    private boolean seatAllocated;

    @Column(name = "SEATINGTYPE")
    private String seatingType;

    @Column(name = "ROWMETANAME")
    private String rowMetaName;

    @Column(name = "PREPAREDBY", updatable = false)
    private String preparedBy;

    @Column(name = "TIMECREATED", updatable = false)
    private Date timeCreated;

    @Column(name = "TIMEUPDATED")
    private Date timeUpdated;

    @Column(name = "ACTIVE")
    private boolean active;

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    private Set<EventFee> fees;

    @Transient
    private Integer primaryEligibilityId;

    @Transient
    private Integer secondaryEligibilityId;

    @Transient
    private Integer courseTypeId;

    @Transient
    private Integer primaryTrainerId;

    @Transient
    private Integer secondaryTrainerId;

    @Transient
    private Integer foundationId;


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

    public String getVenue ()
    {
        return venue;
    }

    public void setVenue (String venue)
    {
        this.venue = venue;
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

    public CourseType getPrimaryEligibility ()
    {
        return primaryEligibility;
    }

    public void setPrimaryEligibility (CourseType primaryEligibility)
    {
        this.primaryEligibility = primaryEligibility;
    }

    public CourseType getSecondaryEligibility ()
    {
        return secondaryEligibility;
    }

    public void setSecondaryEligibility (CourseType secondaryEligibility)
    {
        this.secondaryEligibility = secondaryEligibility;
    }

    public CourseType getCourseType ()
    {
        return courseType;
    }

    public void setCourseType (CourseType courseType)
    {
        this.courseType = courseType;
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

    public boolean isSeatPerLevel ()
    {
        return seatPerLevel;
    }

    public void setSeatPerLevel (boolean seatPerLevel)
    {
        this.seatPerLevel = seatPerLevel;
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

    public Set<EventFee> getFees ()
    {
        return fees;
    }

    public void setFees (Set<EventFee> fees)
    {
        this.fees = fees;
    }

    public boolean isSeatAllocated ()
    {
        return seatAllocated;
    }

    public void setSeatAllocated (boolean seatAllocated)
    {
        this.seatAllocated = seatAllocated;
    }

    public String getSeatingType ()
    {
        return seatingType;
    }

    public String getSeatingTypeName ()
    {
        if (!Util.nullOrEmptyOrBlank(getSeatingType())) {
            return SeatingType.getName(getSeatingType());
        }
        else {
            return "";
        }
    }

    public void setSeatingType (String seatingType)
    {
        this.seatingType = seatingType;
    }

    public String getRowMetaName ()
    {
        return rowMetaName;
    }

    public void setRowMetaName (String rowMetaName)
    {
        this.rowMetaName = rowMetaName;
    }

    public Integer getCourseTypeId ()
    {
        return courseTypeId;
    }

    public void setCourseTypeId (Integer courseTypeId)
    {
        this.courseTypeId = courseTypeId;
    }

    public Integer getPrimaryEligibilityId ()
    {
        return primaryEligibilityId;
    }

    public void setPrimaryEligibilityId (Integer primaryEligibilityId)
    {
        this.primaryEligibilityId = primaryEligibilityId;
    }

    public Integer getSecondaryEligibilityId ()
    {
        return secondaryEligibilityId;
    }

    public void setSecondaryEligibilityId (Integer secondaryEligibilityId)
    {
        this.secondaryEligibilityId = secondaryEligibilityId;
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

    public String getEventTypeName ()
    {
        return getEventType() == 1 ? "Course" : "Workshop";
    }

    public PHFoundation getFoundation ()
    {
        return foundation;
    }

    public void setFoundation (PHFoundation foundation)
    {
        this.foundation = foundation;
    }

    public Integer getFoundationId ()
    {
        return foundationId;
    }

    public void setFoundationId (Integer foundationId)
    {
        this.foundationId = foundationId;
    }

    @Override
    public boolean equals (Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (!id.equals(event.id)) return false;

        return true;
    }

    @Override
    public int hashCode ()
    {
        return id.hashCode();
    }
}
