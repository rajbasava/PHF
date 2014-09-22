/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.web.controller;

import com.yvphfk.model.Option;
import com.yvphfk.model.form.Event;
import com.yvphfk.model.form.EventFee;
import com.yvphfk.model.form.EventRegistration;
import com.yvphfk.model.form.ReferenceGroup;
import com.yvphfk.model.RegistrationCriteria;
import com.yvphfk.common.email.EmailService;
import com.yvphfk.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommonController
{
    @Autowired
    private EventService eventService;

    @Autowired
    protected EmailService emailService;

    @InitBinder
    public void initBinder (WebDataBinder binder)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
    }

    public Map<String, String> getAllEventMap (List<Event> events)
    {
        LinkedHashMap eventMap = new LinkedHashMap<String, String>();
        for (Event event : events) {
            eventMap.put(event.getId(), event.getName());
        }
        return eventMap;
    }

    public Map<String, String> getAllReferenceGroups (List<ReferenceGroup> groups)
    {
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (ReferenceGroup referenceGroup : groups) {
            String name = referenceGroup.getName();
            map.put(name, name);
        }
        return map;
    }

    public Map<String, String> getRegistrationStatusMap ()
    {
        LinkedHashMap registrationStatusMap = new LinkedHashMap<String, String>();
        registrationStatusMap.put(EventRegistration.StatusRegistered, EventRegistration.StatusRegistered);
        registrationStatusMap.put(EventRegistration.StatusCancelled, EventRegistration.StatusCancelled);
        registrationStatusMap.put(EventRegistration.StatusOnHold, EventRegistration.StatusOnHold);
        return registrationStatusMap;
    }

    public Map<Integer, String> getEventTypeMap ()
    {
        LinkedHashMap eventTypeMap = new LinkedHashMap<String, String>();
        eventTypeMap.put(Event.EventTypeCourse, "Course");
        eventTypeMap.put(Event.EventTypeWorkshop, "Workshop");
        return eventTypeMap;
    }

    public java.util.Map<String, String> allFoundations ()
    {
        return eventService.getAllFoundations();
    }

    public Map<String, String> allCourseTypes ()
    {
        return eventService.allCourseTypes();
    }

    public Event getDefaultEvent ()
    {
        List<Event> events = eventService.allEvents();
        RegistrationCriteria criteria = new RegistrationCriteria();
        if (events != null && !events.isEmpty()) {
            Event event = events.get(0);
            return event;
        }
        return null;
    }

    protected List<Option> getAllEventFees (Integer eventId, Boolean review)
    {
        List<EventFee> eventFeeList = eventService.getEventFees(eventId, review);
        ArrayList<Option> options = new ArrayList<Option>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for (EventFee eventFee : eventFeeList) {
            String cutOffDateStr = formatter.format(eventFee.getCutOffDate());
            StringBuffer buffer = new StringBuffer();
            buffer.append(eventFee.getName());
            buffer.append(" - ");
            buffer.append(cutOffDateStr);
            buffer.append(" - ");
            buffer.append(eventFee.getAmount());
            if (eventFee.isReview()) {
                buffer.append(" - ");
                buffer.append("Review");
            }
            Option option = new Option(eventFee.getId(), buffer.toString());
            options.add(option);
        }
        return options;
    }

}
