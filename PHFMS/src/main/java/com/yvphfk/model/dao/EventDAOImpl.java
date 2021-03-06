/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model.dao;

import com.yvphfk.common.Log;
import com.yvphfk.common.Util;
import com.yvphfk.model.EventCriteria;
import com.yvphfk.model.form.CourseType;
import com.yvphfk.model.form.Event;
import com.yvphfk.model.form.EventFee;
import com.yvphfk.model.form.Kit;
import com.yvphfk.model.form.PHFoundation;
import com.yvphfk.model.form.ReferenceGroup;
import com.yvphfk.model.form.RowMeta;
import com.yvphfk.model.form.VolunteerKit;
import com.yvphfk.model.form.WorkshopLevel;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

@Repository
public class EventDAOImpl extends CommonDAOImpl implements EventDAO
{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveOrUpdateEvent (Event event)
    {
        saveOrUpdate(event);
    }

    @Override
    public Event getEvent (Integer eventId)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Event.class);
        criteria.setFetchMode("fees", FetchMode.EAGER);

        criteria.add(Restrictions.eq("id", eventId));
        criteria.add(Restrictions.eq("active", true));
        List<Event> events = criteria.list();

        session.close();
        if (events == null ||
                events.isEmpty()) {
            return null;
        }

        return events.get(0);
    }

    @Override
    public CourseType getCourseType (Integer courseTypeId)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(CourseType.class);

        criteria.add(Restrictions.eq("id", courseTypeId));
        criteria.add(Restrictions.eq("active", true));
        List<CourseType> courseTypes = criteria.list();

        session.close();
        if (courseTypes == null ||
                courseTypes.isEmpty()) {
            return null;
        }

        return courseTypes.get(0);
    }

    @Override
    public WorkshopLevel getWorkshopLevel (Integer workshopLevelId)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(WorkshopLevel.class);

        criteria.add(Restrictions.eq("id", workshopLevelId));
        criteria.add(Restrictions.eq("active", true));
        List<WorkshopLevel> workshopLevels = criteria.list();

        session.close();
        if (workshopLevels == null ||
                workshopLevels.isEmpty()) {
            return null;
        }

        return workshopLevels.get(0);
    }

    @Override
    public CourseType getCourseType (String shortName)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(CourseType.class);

        criteria.add(Restrictions.eq("shortName", shortName));
        criteria.add(Restrictions.eq("active", true));
        List<CourseType> courseTypes = criteria.list();

        session.close();
        if (courseTypes == null ||
                courseTypes.isEmpty()) {
            return null;
        }

        return courseTypes.get(0);
    }

    @Override
    public WorkshopLevel getWorkshopLevel (String courseShortName)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(WorkshopLevel.class);
        criteria.createAlias("courseType", "courseType");

        criteria.add(Restrictions.eq("courseType.shortName", courseShortName));
        criteria.add(Restrictions.eq("active", true));
        List<WorkshopLevel> workshopLevels = criteria.list();

        session.close();
        if (workshopLevels == null ||
                workshopLevels.isEmpty()) {
            return null;
        }

        return workshopLevels.get(0);
    }

    @Override
    public List<Event> allEvents ()
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Event.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.addOrder(Order.asc("timeCreated"));
        criteria.add(Restrictions.eq("active", true));
        List<Event> events = criteria.list();
        session.close();
        return events;
    }

    @Override
    public List<Event> searchEvents (EventCriteria eventCriteria)
    {

        Log.event.warn("search of events called");

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Event.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.addOrder(Order.asc("timeCreated"));

        if (!Util.nullOrEmptyOrBlank(eventCriteria.getName())) {
            criteria.add(Restrictions.ilike("name", eventCriteria.getName(), MatchMode.ANYWHERE));
        }

        if (eventCriteria.getCourseTypeId() != null
                && eventCriteria.getCourseTypeId() != 0) {
            criteria.add(
                    Restrictions.eq(
                            "courseType.id", eventCriteria.getCourseTypeId()));
        }

        if (eventCriteria.getPrimaryTrainerId() != null
                && eventCriteria.getPrimaryTrainerId() != 0) {
            criteria.add(
                    Restrictions.eq(
                            "primaryTrainer.id", eventCriteria.getPrimaryTrainerId()));
        }

        if (eventCriteria.getEventType() != null && eventCriteria.getEventType() != 0) {
            criteria.add(Restrictions.eq("eventType", eventCriteria.getEventType()));
        }

        if (eventCriteria.getStartDate() != null) {
            criteria.add(Restrictions.ge("startDate", eventCriteria.getStartDate()));
        }

        if (eventCriteria.getEndDate() != null) {
            criteria.add(Restrictions.le("endDate", eventCriteria.getEndDate()));
        }

        if (!eventCriteria.isIncludeInactive()) {
            criteria.add(Restrictions.eq("active", true));
        }

        List<Event> results = criteria.list();

        session.close();
        return results;

    }

    @Override
    public void removeEvent (Integer id)
    {
        if (id == null) {
            return;
        }

        Session session = sessionFactory.openSession();

        Event event = (Event) session.load(Event.class, id);

        if (event == null) {
            return;
        }

        Set<EventFee> fees = event.getFees();

        for (Iterator<EventFee> iterator = fees.iterator(); iterator.hasNext(); ) {
            EventFee fee = iterator.next();
            removeEventFee((Integer) fee.getId(), session);
        }

        event.setActive(false);
        session.update(event);
        session.flush();
        session.close();

    }

    @Override
    public void addFee (EventFee fee, Integer eventId)
    {
        if (eventId == null) {
            return;
        }
        Session session = sessionFactory.openSession();

        Event event = (Event) session.load(Event.class, eventId);

        if (event == null) {
            return;
        }

        fee.setEvent(event);
        session.save(fee);

        session.close();
    }

    @Override
    public void addWorkshopLevel (WorkshopLevel workshopLevel, Integer eventId)
    {
        if (eventId == null) {
            return;
        }
        Session session = sessionFactory.openSession();

        Event event = (Event) session.load(Event.class, eventId);

        if (event == null) {
            return;
        }

        CourseType courseType = (CourseType) session.load(CourseType.class,
                workshopLevel.getCourseTypeId());

        workshopLevel.setCourseType(courseType);
        workshopLevel.setEvent(event);
        workshopLevel.setName(courseType.getShortName());
        session.save(workshopLevel);

        session.close();
    }

    @Override
    public List<EventFee> getEventFees (Integer eventId)
    {
        return getEventFees(eventId, null, null);
    }

    @Override
    public List<EventFee> getEventFees (Integer eventId, Boolean review, Integer workshopLevelId)
    {
        if (eventId == null) {
            return null;
        }

        Event event = (Event) sessionFactory.getCurrentSession().load(
                Event.class, eventId);

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(EventFee.class);

        criteria.add(Restrictions.eq("event", event));
        criteria.add(Restrictions.eq("active", true));

        if (workshopLevelId != null) {
            WorkshopLevel workshopLevel = (WorkshopLevel) sessionFactory.getCurrentSession().load(
                    WorkshopLevel.class, workshopLevelId);
            criteria.add(Restrictions.eq("workshopLevel", workshopLevel));
        }

        if (review != null) {
            criteria.add(Restrictions.eq("review", review.booleanValue()));
        }

        criteria.add(Restrictions.ge("cutOffDate", new Date()));
        criteria.addOrder(Order.asc("timeCreated"));
        List<EventFee> eventFees = criteria.list();
        session.close();

        return eventFees;
    }

    @Override
    public List<EventFee> getAllEventFees (Integer eventId)
    {
        if (eventId == null) {
            return null;
        }

        Event event = (Event) sessionFactory.getCurrentSession().load(
                Event.class, eventId);

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(EventFee.class);
        criteria.add(Restrictions.eq("event", event));
        criteria.addOrder(Order.asc("timeCreated"));
        List<EventFee> eventFees = criteria.list();
        session.close();

        return eventFees;
    }

    @Override
    public List<WorkshopLevel> getAllWorkshopLevels (Integer eventId)
    {
        if (eventId == null) {
            return null;
        }

        Event event = (Event) sessionFactory.getCurrentSession().load(
                Event.class, eventId);

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(WorkshopLevel.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.eq("event", event));
        criteria.addOrder(Order.asc("levelOrder"));
        List<WorkshopLevel> workshopLevels = criteria.list();
        session.close();

        return workshopLevels;
    }

    @Override
    public EventFee getBestEventFee (Integer eventId,
                                     Boolean review,
                                     Long amount,
                                     WorkshopLevel workshopLevel)
    {
        if (eventId == null) {
            return null;
        }

        Event event = (Event) sessionFactory.getCurrentSession().load(
                Event.class, eventId);

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(EventFee.class);

        criteria.add(Restrictions.eq("event", event));

        if (review != null) {
            criteria.add(Restrictions.eq("review", review.booleanValue()));
        }

        if (amount != null) {
            criteria.add(Restrictions.eq("amount", amount));
        }

        if (workshopLevel != null) {
            criteria.add(Restrictions.eq("workshopLevel", workshopLevel));
        }

        criteria.addOrder(Order.asc("timeCreated"));
        List<EventFee> eventFees = criteria.list();

        session.close();

        if (eventFees == null ||
                eventFees.isEmpty()) {
            return null;
        }

        return eventFees.get(0);
    }

    @Override
    public void removeEventFee (Integer eventFeeId)
    {
        Session session = sessionFactory.openSession();

        removeEventFee(eventFeeId, session);
        session.flush();
        session.close();
    }

    @Override
    public EventFee getEventFee (Integer eventFeeId)
    {
        Session session = sessionFactory.openSession();

        Criteria criteria = session.createCriteria(EventFee.class);
        criteria.add(Restrictions.eq("id", eventFeeId));
        criteria.add(Restrictions.eq("active", true));
        EventFee fee = (EventFee) criteria.uniqueResult();

        session.flush();
        session.close();

        return fee;

    }

    private void removeEventFee (Integer eventFeeId, Session session)
    {
        removeEventFee(eventFeeId, session, true);
    }

    private void removeEventFee (Integer eventFeeId, Session session, boolean isSoftDelete)
    {
        if (eventFeeId == null) {
            return;
        }

        EventFee fee = (EventFee) session.load(EventFee.class, eventFeeId);

        if (fee == null) {
            return;
        }

        if (isSoftDelete) {
            fee.setActive(false);
            session.update(fee);
        }
        else {
            session.delete(fee);
        }
    }

    @Override
    public Kit getEventKit (Integer eventId)
    {
        if (eventId == null) {
            return null;
        }

        Event event = (Event) sessionFactory.getCurrentSession().load(
                Event.class, eventId);

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Kit.class);
        criteria.add(Restrictions.eq("event", event));
        criteria.add(Restrictions.eq("active", true));
        Kit kit = (Kit) criteria.uniqueResult();
        session.flush();
        session.close();

        return kit;
    }

    @Override
    public void manageEventKit (Kit kit)
    {
        Session session = sessionFactory.openSession();

        Event event = (Event) session.load(Event.class, kit.getEventId());

        if (event == null) {
            return;
        }

        kit.setEvent(event);
        session.saveOrUpdate(kit);

        session.flush();
        session.close();
    }

    @Override
    public List<VolunteerKit> getVolunteerKits (Integer kitId)
    {
        if (kitId == null) {
            return null;
        }

        Kit kit = (Kit) sessionFactory.getCurrentSession().load(Kit.class, kitId);

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(VolunteerKit.class);
        criteria.add(Restrictions.eq("kit", kit));
        criteria.add(Restrictions.eq("active", true));
        criteria.addOrder(Order.desc("timeCreated"));
        List<VolunteerKit> volunteerKits = criteria.list();
        session.flush();
        session.close();

        return volunteerKits;
    }

    @Override
    public VolunteerKit getVolunteerKit (Integer voldKitId)
    {
        if (voldKitId == null) {
            return null;
        }

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(VolunteerKit.class);
        criteria.add(Restrictions.eq("id", voldKitId));
        criteria.add(Restrictions.eq("active", true));
        VolunteerKit volunteerKit = (VolunteerKit) criteria.uniqueResult();
        session.flush();
        session.close();

        return volunteerKit;
    }

    public void allotVolunteerKits (VolunteerKit volunteerKit)
    {
        volunteerKit.setKitCount(volunteerKit.getKitCount() + volunteerKit.getAllotKits());
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(volunteerKit);

        session.flush();
        session.close();
    }

    public RowMeta getFirstEmptyRowMeta (Event event)
    {
        Session session = sessionFactory.openSession();

        Criteria criteria = session.createCriteria(RowMeta.class);
        criteria.add(Restrictions.eq("name", event.getRowMetaName()));
        criteria.add(Restrictions.eq("rowFull", false));
        criteria.addOrder(Order.asc("sortOrder"));
        criteria.setMaxResults(1);
        RowMeta rowMeta = (RowMeta) criteria.uniqueResult();

        session.flush();
        session.close();

        return rowMeta;
    }

    public List<RowMeta> getAllEmptyRowMetas (Event event)
    {
        Session session = sessionFactory.openSession();

        Criteria criteria = session.createCriteria(RowMeta.class);
        criteria.add(Restrictions.eq("name", event.getRowMetaName()));
        criteria.add(Restrictions.eq("rowFull", false));
        criteria.addOrder(Order.asc("sortOrder"));
        List<RowMeta> rowMetas = criteria.list();

        session.flush();
        session.close();

        return rowMetas;
    }

    public List<String> getAllRowMetaNames ()
    {
        Session session = sessionFactory.openSession();

        Query query = session.createSQLQuery("select distinct name from phk_rowmeta where active = :active");
        query.setParameter("active", "1");
        List<String> rowMetaNames = query.list();

        session.flush();
        session.close();

        return rowMetaNames;
    }

    public java.util.Map<String, String> getAllFoundations ()
    {
        Session session = sessionFactory.openSession();
        java.util.Map<String, String> map = new LinkedHashMap<String, String>();

        Criteria criteria = session.createCriteria(PHFoundation.class);
        criteria.add(Restrictions.eq("active", true));
        List<PHFoundation> foundations = criteria.list();
        for (PHFoundation foundation : foundations) {
            map.put(String.valueOf(foundation.getId()), foundation.getShortName());
        }

        session.flush();
        session.close();

        return map;
    }

    public java.util.Map<String, String> allCourseTypes ()
    {
        Session session = sessionFactory.openSession();
        java.util.Map<String, String> map = new LinkedHashMap<String, String>();

        Criteria criteria = session.createCriteria(CourseType.class);
        criteria.add(Restrictions.eq("active", true));
        List<CourseType> courseTypes = criteria.list();
        for (CourseType courseType : courseTypes) {
            map.put(String.valueOf(courseType.getId()), courseType.getName());
        }

        session.flush();
        session.close();

        return map;
    }

    public java.util.Map<String, String> allArhaticCourseTypes ()
    {
        Session session = sessionFactory.openSession();
        java.util.Map<String, String> map = new LinkedHashMap<String, String>();

        Criteria criteria = session.createCriteria(CourseType.class);
        criteria.add(Restrictions.eq("active", true));
        criteria.add(Restrictions.ilike("name", "Arhatic Yoga", MatchMode.ANYWHERE));
        List<CourseType> courseTypes = criteria.list();
        for (CourseType courseType : courseTypes) {
            map.put(String.valueOf(courseType.getId()), courseType.getName());
        }

        session.flush();
        session.close();

        return map;
    }

    public void addRowMeta (RowMeta rowMeta)
    {
        saveOrUpdate(rowMeta);
    }

    public void addReferenceGroup (ReferenceGroup referenceGroup)
    {
        sessionFactory.getCurrentSession().save(referenceGroup);
    }

    public ReferenceGroup getReferenceGroup (String name)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(ReferenceGroup.class);

        criteria.add(Restrictions.eq("uniquename", name));
        List<ReferenceGroup> referenceGroups = criteria.list();

        session.close();
        if (referenceGroups == null ||
                referenceGroups.isEmpty()) {
            return null;
        }

        return referenceGroups.get(0);

    }

    public List<ReferenceGroup> listReferenceGroups ()
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(ReferenceGroup.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.addOrder(Order.asc("timeCreated"));
        List<ReferenceGroup> referenceGroups = criteria.list();
        session.close();
        return referenceGroups;
    }

    public List getAttendeesPivot (Integer eventId)
    {
        /**
         select foundation.shortname,
         count(reg.id),
         (select count(id) from phk_eventregstrn where attend = 1 and foundation = reg.foundation) as Attendees,
         (select count(id) from phk_eventregstrn where attend = 0 and foundation = reg.foundation) as Absentees
         from phk_eventregstrn as reg, phk_foundation as foundation where reg.foundation = foundation.id
         group by foundation.id

         */

        String strQuery = "select foundation.shortname, " +
                " (select count(id) from phk_eventregstrn " +
                "   where attend = 1 and foundation = reg.foundation and eventid = :eventId) as Attendees, " +
                " (select count(id) from phk_eventregstrn " +
                "   where attend = 0 and foundation = reg.foundation and eventid = :eventId) as Absentees, " +
                " count(reg.id) " +
                " from phk_eventregstrn as reg, phk_foundation as foundation " +
                " where reg.foundation = foundation.id and reg.eventid = :eventId" +
                " group by foundation.id ";

        Session session = sessionFactory.openSession();

        Query query = session.createSQLQuery(strQuery);
        query.setInteger("eventId", eventId);
        List results = query.list();

        session.flush();
        session.close();

        return results;
    }

    public Object getTotalAttendeesPivot (Integer eventId)
    {
        /**
            select count(reg.id),
            (select count(id) from phk_eventregstrn where attend = 1) as Attendees,
            (select count(id) from phk_eventregstrn where attend = 0) as Absentees
            from phk_eventregstrn as reg
         */

        String strQuery = "select " +
                " (select count(id) from phk_eventregstrn where attend = 1 and eventid = :eventId) as Attendees, " +
                " (select count(id) from phk_eventregstrn where attend = 0 and eventid = :eventId) as Absentees,  " +
                " count(reg.id)  "+
                " from phk_eventregstrn as reg where reg.eventid = :eventId";

        Session session = sessionFactory.openSession();

        Query query = session.createSQLQuery(strQuery);
        query.setInteger("eventId", eventId);
        Object result = query.uniqueResult();

        session.flush();
        session.close();

        return result;
    }

    public List getPaymentPivot (Integer eventId)
    {
        /**
         SELECT foundation.shortname,
         (SELECT COUNT(id) FROM phk_eventregstrn WHERE amountdue = 0 AND foundation = reg.foundation and eventid = 1 ) AS CompCount,
         (SELECT SUM(totalamountpaid) FROM phk_eventregstrn WHERE amountdue = 0 AND foundation = reg.foundation and eventid = 1) AS TotalPaidAmt,
         (SELECT COUNT(id) FROM phk_eventregstrn WHERE amountdue > 0 AND foundation = reg.foundation and eventid = 1 ) AS PPCount,
         (SELECT SUM(amountdue) FROM phk_eventregstrn WHERE amountdue > 0 AND foundation = reg.foundation and eventid = 1) AS TotalAmtDue
         FROM phk_eventregstrn AS reg, phk_foundation AS foundation
         WHERE reg.foundation = foundation.id and reg.eventid = 1
         GROUP BY foundation.id
         */

        String strQuery = "select foundation.shortname, " +
                " (SELECT COUNT(id) FROM phk_eventregstrn WHERE amountdue = 0 AND foundation = reg.foundation and eventid = :eventId  ) AS CompCount, " +
                " (SELECT SUM(totalamountpaid) FROM phk_eventregstrn WHERE amountdue = 0 AND foundation = reg.foundation and eventid = :eventId ) AS TotalPaidAmt, " +
                " (SELECT COUNT(id) FROM phk_eventregstrn WHERE amountdue > 0 AND foundation = reg.foundation and eventid = :eventId  ) AS PPCount, " +
                " (SELECT SUM(amountdue) FROM phk_eventregstrn WHERE amountdue > 0 AND foundation = reg.foundation and eventid = :eventId ) AS TotalAmtDue "+
                " FROM phk_eventregstrn AS reg, phk_foundation AS foundation " +
                " where reg.foundation = foundation.id and reg.eventid = :eventId" +
                " group by foundation.id ";

        Session session = sessionFactory.openSession();

        Query query = session.createSQLQuery(strQuery);
        query.setInteger("eventId", eventId);
        List results = query.list();

        session.flush();
        session.close();

        return results;
    }

    public Object getTotalPaymentPivot (Integer eventId)
    {
        /**
            SELECT
             (SELECT COUNT(id) FROM phk_eventregstrn WHERE amountdue = 0  and eventid = 1 ) AS CompCount,
             (SELECT SUM(totalamountpaid) FROM phk_eventregstrn WHERE amountdue = 0 and eventid = 1) AS TotalPaidAmt,
             (SELECT COUNT(id) FROM phk_eventregstrn WHERE amountdue > 0 and eventid = 1 ) AS PPCount,
             SUM(amountdue) AS TotalAmtDue
            FROM phk_eventregstrn
            WHERE  eventid = 1
         */

        String strQuery = " SELECT " +
                " (SELECT COUNT(id) FROM phk_eventregstrn WHERE amountdue = 0 and eventid = :eventId) AS CompCount, " +
                " (SELECT SUM(totalamountpaid) FROM phk_eventregstrn WHERE amountdue = 0 and eventid = :eventId) AS TotalPaidAmt, " +
                " (SELECT COUNT(id) FROM phk_eventregstrn WHERE amountdue > 0 and eventid = :eventId) AS PPCount, " +
                " SUM(amountdue) AS TotalAmtDue  " +
                " FROM phk_eventregstrn " +
                " WHERE  eventid = :eventId";

        Session session = sessionFactory.openSession();

        Query query = session.createSQLQuery(strQuery);
        query.setInteger("eventId", eventId);
        Object result = query.uniqueResult();

        session.flush();
        session.close();

        return result;
    }


}
