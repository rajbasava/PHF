/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.model.dao;

import com.yvphfk.model.EventCriteria;
import com.yvphfk.model.form.CourseType;
import com.yvphfk.model.form.Event;
import com.yvphfk.model.form.EventFee;
import com.yvphfk.model.form.Kit;
import com.yvphfk.model.form.ReferenceGroup;
import com.yvphfk.model.form.RowMeta;
import com.yvphfk.model.form.VolunteerKit;
import com.yvphfk.model.form.WorkshopLevel;

import java.util.List;
import java.util.Map;

public interface EventDAO extends CommonDAO
{
    public void saveOrUpdateEvent (Event event);

    public Event getEvent (Integer eventId);

    public CourseType getCourseType (Integer courseTypeId);

    public WorkshopLevel getWorkshopLevel (Integer workshopLevelId);

    public WorkshopLevel getWorkshopLevel (String courseShortName);

    public CourseType getCourseType (String shortName);

    public List<Event> allEvents ();

    public List<Event> searchEvents (EventCriteria eventCriteria);

    public void removeEvent (Integer id);

    public void addFee (EventFee fee, Integer eventId);

    public void addWorkshopLevel (WorkshopLevel workshopLevel, Integer eventId);

    public List<EventFee> getEventFees (Integer eventId);

    public List<EventFee> getAllEventFees (Integer eventId);

    public List<WorkshopLevel> getAllWorkshopLevels (Integer eventId);

    public List<EventFee> getEventFees (Integer eventId, Boolean review, Integer workshopLevelId);

    public EventFee getBestEventFee (Integer eventId, Boolean review, Long amount, WorkshopLevel workshopLevel);

    public void removeEventFee (Integer eventFeeId);

    public EventFee getEventFee (Integer eventFeeId);

    public Kit getEventKit (Integer eventId);

    public void manageEventKit (Kit kit);

    public List<VolunteerKit> getVolunteerKits (Integer kitId);

    public VolunteerKit getVolunteerKit (Integer voldKitId);

    public void allotVolunteerKits (VolunteerKit volunteerKit);

    public RowMeta getFirstEmptyRowMeta (Event event);

    public List<RowMeta> getAllEmptyRowMetas (Event event);

    public List<String> getAllRowMetaNames ();

    public java.util.Map<String, String> getAllFoundations ();

    public Map<String, String> allCourseTypes ();

    public java.util.Map<String, String> allArhaticCourseTypes ();

    public void addRowMeta (RowMeta rowMeta);

    public void addReferenceGroup (ReferenceGroup referenceGroup);

    public ReferenceGroup getReferenceGroup (String name);

    public List<ReferenceGroup> listReferenceGroups ();

    public List getAttendeesPivot (Integer eventId);

    public Object getTotalAttendeesPivot (Integer eventId);

    public List getPaymentPivot (Integer eventId);

    public Object getTotalPaymentPivot (Integer eventId);

}
