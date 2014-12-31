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

import java.util.List;
import java.util.Map;

public interface EventDAO extends CommonDAO
{
    public void saveOrUpdateEvent (Event event);

    public Event getEvent (Integer eventId);

    public CourseType getCourseType (Integer courseTypeId);

    public CourseType getCourseType (String shortName);

    public List<Event> allEvents ();

    public List<Event> searchEvents (EventCriteria eventCriteria);

    public void removeEvent (Integer id);

    public void addFee (EventFee fee, Integer eventId);

    public List<EventFee> getEventFees (Integer eventId);

    public List<EventFee> getEventFees (Integer eventId, Boolean review);

    public EventFee getBestEventFee (Integer eventId, Boolean review, Long amount, CourseType courseType);

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


}
