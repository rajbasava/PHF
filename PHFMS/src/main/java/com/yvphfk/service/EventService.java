/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.service;

import com.yvphfk.model.EventCriteria;
import com.yvphfk.model.form.CourseType;
import com.yvphfk.model.form.Event;
import com.yvphfk.model.form.EventFee;
import com.yvphfk.model.form.EventRegistration;
import com.yvphfk.model.form.Kit;
import com.yvphfk.model.form.PHFoundation;
import com.yvphfk.model.form.ParticipantSeat;
import com.yvphfk.model.form.ReferenceGroup;
import com.yvphfk.model.form.VolunteerKit;
import com.yvphfk.model.form.WorkshopLevel;

import java.util.List;
import java.util.Map;

public interface EventService
{
    public void saveOrUpdateEvent (Event event);

    public Event getEvent (Integer eventId);

    public CourseType getCourseType (Integer courseTypeId);

    public WorkshopLevel getWorkshopLevel (Integer workshopLevelId);

    public List<Event> allEvents ();

    public List<Event> searchEvents (EventCriteria eventCriteria);

    public void removeEvent (Integer id);

    public void addEventFee (EventFee fee, Integer eventId);

    public void addWorkshopLevel (WorkshopLevel workshopLevel, Integer eventId);

    public List<EventFee> getEventFees (Integer eventId);

    public List<EventFee> getAllEventFees (Integer eventId);

    public List<WorkshopLevel> getAllWorkshopLevels (Integer eventId);

    public List<EventFee> getEventFees (Integer eventId, Boolean review, Integer workshopLevelId);

    public void removeEventFee (Integer eventFeeId);

    public EventFee getEventFee (Integer eventFeeId);

    public Kit getEventKit (Integer eventId);

    public void manageEventKit (Kit kit);

    public List<VolunteerKit> getVolunteerKits (Integer kitId);

    public VolunteerKit getVolunteerKit (Integer voldKitId);

    public void allotVolunteerKits (VolunteerKit volunteerKit);

    public List<String> getAllRowMetaNames ();

    public java.util.Map<String, String> getAllFoundations ();

    public Map<String, String> allCourseTypes ();

    public java.util.Map<String, String> allArhaticCourseTypes ();

    public void allocateSeats (Event event);

    public ParticipantSeat nextSeat (Event event, EventRegistration registration);

    public void addReferenceGroup (ReferenceGroup referenceGroup);

    public ReferenceGroup getReferenceGroup (String name);

    public List<ReferenceGroup> listReferenceGroups ();

    public PHFoundation getFoundation (Integer foundationId);

    public List getAttendeesPivot (Integer eventId);

    public Object getTotalAttendeesPivot (Integer eventId);


}
