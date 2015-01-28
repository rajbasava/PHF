/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.model.form;

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

@Entity
@Table(name = "phk_seat")
public class ParticipantSeat extends BaseForm
{
    public static final String ClassName = "com.yvphfk.model.form.ParticipantSeat";

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EVENTREGSTRNID")
    private EventRegistration registration;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EVENTID")
    private Event event;

    // removed cascade here to avoid org.hibernate.NonUniqueObjectException: a different object with the same
    // identifier value was already associated with the session:
    // link :http://stackoverflow.com/questions/16246675/hibernate-error-a-different-object-with-the-same-identifier-value-was-already-a
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coursetype")
    private CourseType courseType;

    @Column(name = "SEAT")
    private Integer seat;

    @Column(name = "ALPHA")
    private String alpha;

    @Column(name = "SUFFIX")
    private String suffix;

    @Column(name = "CUSTOM")
    private boolean custom;

    @Transient
    private Integer registrationId;

    @Transient
    private String courseTypeShortName;

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

    public EventRegistration getRegistration ()
    {
        return registration;
    }

    public void setRegistration (EventRegistration registration)
    {
        this.registration = registration;
    }

    public CourseType getCourseType ()
    {
        return courseType;
    }

    public void setCourseType (CourseType courseType)
    {
        this.courseType = courseType;
    }

    public String getLevelName ()
    {
        if (getCourseType() == null) {
            return null;
        }

        return getCourseType().getShortName();
    }

    public Integer getSeat ()
    {
        return seat;
    }

    public void setSeat (Integer seat)
    {
        this.seat = seat;
    }

    public Event getEvent ()
    {
        return event;
    }

    public void setEvent (Event event)
    {
        this.event = event;
    }

    public String getAlpha ()
    {
        return alpha;
    }

    public void setAlpha (String alpha)
    {
        this.alpha = alpha;
    }

    public String getSuffix ()
    {
        return suffix;
    }

    public void setSuffix (String suffix)
    {
        this.suffix = suffix;
    }

    public boolean isCustom ()
    {
        return custom;
    }

    public void setCustom (boolean custom)
    {
        this.custom = custom;
    }

    public Integer getRegistrationId ()
    {
        return registrationId;
    }

    public void setRegistrationId (Integer registrationId)
    {
        this.registrationId = registrationId;
    }

    public String getCourseTypeShortName ()
    {
        return courseTypeShortName;
    }

    public void setCourseTypeShortName (String courseTypeShortName)
    {
        this.courseTypeShortName = courseTypeShortName;
    }

    @Override
    public boolean equals (Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParticipantSeat)) {
            return false;
        }

        ParticipantSeat that = (ParticipantSeat) o;

        if (alpha != null ? !alpha.equals(that.alpha) : that.alpha != null) {
            return false;
        }
        if (courseType != null ? !courseType.equals(that.courseType) : that.courseType != null) {
            return false;
        }
        if (event != null ? !event.equals(that.event) : that.event != null) {
            return false;
        }
        if (!id.equals(that.id)) {
            return false;
        }
        if (registration != null ? !registration.equals(that.registration) : that.registration != null) {
            return false;
        }
        if (seat != null ? !seat.equals(that.seat) : that.seat != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode ()
    {
        int result = id.hashCode();
        result = 31 * result + (registration != null ? registration.hashCode() : 0);
        result = 31 * result + (event != null ? event.hashCode() : 0);
        result = 31 * result + (courseType != null ? courseType.hashCode() : 0);
        result = 31 * result + (seat != null ? seat.hashCode() : 0);
        result = 31 * result + (alpha != null ? alpha.hashCode() : 0);
        return result;
    }
}