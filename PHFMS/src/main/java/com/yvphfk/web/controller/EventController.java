/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.web.controller;

import com.yvphfk.common.SeatingType;
import com.yvphfk.common.Util;
import com.yvphfk.model.EventCriteria;
import com.yvphfk.model.Login;
import com.yvphfk.model.Option;
import com.yvphfk.model.form.CourseType;
import com.yvphfk.model.form.Event;
import com.yvphfk.model.form.EventFee;
import com.yvphfk.model.form.Kit;
import com.yvphfk.model.form.LoggedInVolunteer;
import com.yvphfk.model.form.ReferenceGroup;
import com.yvphfk.model.form.Volunteer;
import com.yvphfk.model.form.VolunteerKit;
import com.yvphfk.model.form.validator.EventValidator;
import com.yvphfk.service.EventService;
import com.yvphfk.service.ParticipantService;
import com.yvphfk.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EventController extends CommonController
{
    @Autowired
    private EventService eventService;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private ParticipantService participantService;

    @RequestMapping("/showAddEvent")
    public String showAddEvent (Map<String, Object> map)
    {
        map.put("event", new Event());
        map.put("allParticipantCourseTypes", allCourseTypes());
        map.put("allSeatingTypes", SeatingType.allSeatingTypes());
        map.put("allRowMetaNames", eventService.getAllRowMetaNames());
        map.put("eventTypeMap", getEventTypeMap());
        return "addEvent";
    }

    @RequestMapping("/showManageEvents")
    public String showManageEvents (Map<String, Object> map)
    {
        map.put("eventCriteria", new EventCriteria());
        map.put("allParticipantCourseTypes", allCourseTypes());
        map.put("allSeatingTypes", SeatingType.allSeatingTypes());
        map.put("allRowMetaNames", eventService.getAllRowMetaNames());
        map.put("eventTypeMap", getEventTypeMap());
        return "manageEvents";
    }

    @RequestMapping("/searchEvents")
    public String searchEvents (Map<String, Object> map, EventCriteria eventCriteria)
    {
        map.put("eventCriteria", eventCriteria);
        map.put("eventList", eventService.searchEvents(eventCriteria));
        map.put("allParticipantCourseTypes", allCourseTypes());
        map.put("allSeatingTypes", SeatingType.allSeatingTypes());
        map.put("allRowMetaNames", eventService.getAllRowMetaNames());
        map.put("eventTypeMap", getEventTypeMap());
        return "manageEvents";
    }

    @RequestMapping("/showEventDetails")
    public String showEventDetails (Map<String, Object> map, HttpServletRequest request)
    {


        boolean isFwd = false;
        String strIsFwd = (String) request.getAttribute("isForward");
        if (!Util.nullOrEmptyOrBlank(strIsFwd)) {
            isFwd = Boolean.parseBoolean(strIsFwd);
        }

        String strEventId = null;
        String strIsEdit = null;
        if (isFwd) {
            strEventId = (String) request.getAttribute("eventId");
            strIsEdit = (String) request.getAttribute("isEdit");
        }
        else {
            strEventId = request.getParameter("eventId");
            strIsEdit = request.getParameter("isEdit");
        }

        if (Util.nullOrEmptyOrBlank(strEventId)) {
            return null;
        }

        boolean isEdit = false;
        if (!Util.nullOrEmptyOrBlank(strIsEdit)) {
            isEdit = Boolean.parseBoolean(strIsEdit);
        }

        Integer eventId = Integer.parseInt(strEventId);

        Event event = eventService.getEvent(eventId);
        if (event.getPrimaryEligibility() != null) {
            event.setPrimaryEligibilityId(event.getPrimaryEligibility().getId());
        }

        if (event.getSecondaryEligibility() != null) {
            event.setSecondaryEligibilityId(event.getSecondaryEligibility().getId());
        }

        if (event.getCourseType() != null) {
            event.setCourseTypeId(event.getCourseType().getId());
        }

        map.put("event", event);
        map.put("isEdit", isEdit);
        map.put("eventList", eventService.allEvents());
        map.put("eventFeeList", eventService.getEventFees(eventId));
        map.put("allParticipantCourseTypes", allCourseTypes());
        map.put("allSeatingTypes", SeatingType.allSeatingTypes());
        map.put("allRowMetaNames", eventService.getAllRowMetaNames());
        map.put("eventTypeMap", getEventTypeMap());
        return "eventDetails";
    }

    @RequestMapping(value = "/saveOrUpdateEvent", method = RequestMethod.POST)
    public String saveOrUpdateEvent (@ModelAttribute("event") Event event,
                                     BindingResult errors,
                                     Map<String, Object> map,
                                     HttpServletRequest request)
    {
        if (event.getPrimaryEligibilityId() != -1){
            event.setPrimaryEligibility(eventService.getCourseType(event.getPrimaryEligibilityId()));
        }
        if (event.getSecondaryEligibilityId() != -1) {
            event.setSecondaryEligibility(eventService.getCourseType(event.getSecondaryEligibilityId()));
        }

        if (event.getCourseTypeId() != -1) {
            event.setCourseType(eventService.getCourseType(event.getCourseTypeId()));
        }

        if (event.getPrimaryTrainerId() != -1) {
            event.setPrimaryTrainer(participantService.getTrainer(event.getPrimaryTrainerId()));
        }
        if (event.getSecondaryTrainerId() != -1) {
            event.setSecondaryTrainer(participantService.getTrainer(event.getSecondaryTrainerId()));
        }

        EventValidator val = new EventValidator();
        val.validate(event, errors);

        if (errors.hasErrors()) {
            map.put("errors", errors);
            map.put("event", event);
            map.put("eventList", eventService.allEvents());
            map.put("allParticipantCourseTypes", allCourseTypes());
            map.put("allSeatingTypes", SeatingType.allSeatingTypes());
            map.put("allRowMetaNames", eventService.getAllRowMetaNames());
            map.put("eventTypeMap", getEventTypeMap());
            return "addEvent";
        }
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);
        if (event.getId() == null) {
            event.initialize(login.getEmail());
        }
        else {
            event.initializeForUpdate(login.getEmail());
        }
        eventService.saveOrUpdateEvent(event);
        request.setAttribute("eventId", String.valueOf(event.getId()));
        request.setAttribute("isEdit", "false");
        request.setAttribute("isForward", "true");
        return "forward:/showEventDetails.htm";
    }

    @RequestMapping("/eventFee")
    public String listEventFee (Map<String, Object> map, HttpServletRequest request)
    {
        String strEventId = request.getParameter("eventId");
        if (Util.nullOrEmptyOrBlank(strEventId)) {
            return null;
        }
        Integer eventId = Integer.parseInt(strEventId);
        EventFee fee = new EventFee();
        fee.setEventId(eventId);
        if (fee.getCourseType() != null) {
            fee.setCourseTypeId(fee.getCourseType().getId());
        }

        map.put("eventFee", fee);
        map.put("eventFeeList", eventService.getEventFees(eventId));
        map.put("event", eventService.getEvent(eventId));
        map.put("allParticipantCourseTypes", allCourseTypes());
        return "eventFee";
    }

    @RequestMapping("/showEventDetailUI")
    public String showEventDetailUI (Map<String, Object> map, HttpServletRequest request)
    {
        String strEventId = request.getParameter("eventId");
        if (Util.nullOrEmptyOrBlank(strEventId)) {
            strEventId = (String) request.getAttribute("eventId");
            if (Util.nullOrEmptyOrBlank(strEventId)) {
                return null;
            }
        }
        Integer eventId = Integer.parseInt(strEventId);
        Kit kit = eventService.getEventKit(eventId);
        if (kit == null) {
            kit = new Kit();
        }
        kit.setEventId(eventId);
        map.put("eventKit", kit);
        map.put("event", eventService.getEvent(eventId));
        return "eventDetailUI";
    }

    @RequestMapping("/showKitsUI")
    public String showKitsUI (Map<String, Object> map, HttpServletRequest request)
    {
        String strEventId = request.getParameter("eventId");
        if (Util.nullOrEmptyOrBlank(strEventId)) {
            strEventId = (String) request.getAttribute("eventId");
            if (Util.nullOrEmptyOrBlank(strEventId)) {
                return null;
            }
        }
        Integer eventId = Integer.parseInt(strEventId);
        Kit kit = eventService.getEventKit(eventId);
        if (kit == null) {
            kit = new Kit();
        }
        kit.setEventId(eventId);
        List<VolunteerKit> volunteerKitList = eventService.getVolunteerKits(kit.getId());
        int unallotedKitsCount = 0;
        int allotedKitsCount = 0;
        int kitsGivenCount = 0;
        int kitsLeftCount = 0;
        if (volunteerKitList != null) {
            for (VolunteerKit volunteerKit : volunteerKitList) {
                allotedKitsCount += volunteerKit.getKitCount();
                kitsGivenCount += volunteerKit.getKitsGiven();
                kitsLeftCount += volunteerKit.getKitCount() - volunteerKit.getKitsGiven();
            }
            unallotedKitsCount = kit.getStock() - allotedKitsCount;
            map.put("unallotedKitsCount", unallotedKitsCount);
            map.put("allotedKitsCount", allotedKitsCount);
            map.put("kitsGivenCount", kitsGivenCount);
            map.put("kitsLeftCount", kitsLeftCount);
            map.put("eventKit", kit);
            map.put("volunteerKits", volunteerKitList);
            map.put("event", eventService.getEvent(eventId));
            return "kitsUI";
        }
        else {
            // todo need to provide message when no kits were allocated
            request.setAttribute("eventId", strEventId);
            request.setAttribute("isEdit", "false");
            request.setAttribute("isForward", "true");
            return "forward:/showEventDetails.htm";
        }
    }

    @RequestMapping("/showEventKitsUI")
    public String showEventKitsUI (Map<String, Object> map, HttpServletRequest request)
    {
        String strEventId = request.getParameter("eventId");
        if (Util.nullOrEmptyOrBlank(strEventId)) {
            return null;
        }
        Integer eventId = Integer.parseInt(strEventId);
        Kit kit = eventService.getEventKit(eventId);
        if (kit == null) {
            kit = new Kit();
        }
        kit.setEventId(eventId);
        map.put("eventKit", kit);
        map.put("event", eventService.getEvent(eventId));
        return "eventKitsUI";
    }

    @RequestMapping("/showVolKitsUI")
    public String showVolKitsUI (Map<String, Object> map, HttpServletRequest request)
    {
        String strVolKitId = request.getParameter("id");
        if (Util.nullOrEmptyOrBlank(strVolKitId)) {
            return null;
        }
        Integer voldKitId = Integer.parseInt(strVolKitId);
        VolunteerKit volunteerKit = eventService.getVolunteerKit(voldKitId);

        map.put("event", eventService.getEvent(volunteerKit.getKit().getEvent().getId()));
        map.put("volunteerKit", volunteerKit);
        return "volKitsUI";
    }

    @RequestMapping("/showAddVolKitsUI")
    public String showAddVolKitsUI (Map<String, Object> map, HttpServletRequest request)
    {
        String strEventId = request.getParameter("eventId");
        if (Util.nullOrEmptyOrBlank(strEventId)) {
            return null;
        }
        Integer eventId = Integer.parseInt(strEventId);
        Kit kit = eventService.getEventKit(eventId);
        if (kit != null) {
            VolunteerKit volunteerKit = new VolunteerKit();
            volunteerKit.setKit(kit);
            String strUnallotedKitsCount = request.getParameter("unallotedKitsCount");
            if (!Util.nullOrEmptyOrBlank(strUnallotedKitsCount)) {
                Integer unallotedKitsCount = Integer.parseInt(strUnallotedKitsCount);
                map.put("unallotedKitsCount", unallotedKitsCount);
            }
            map.put("volunteerKit", volunteerKit);
            map.put("volunteerList", volunteerService.listVolunteerWithoutKits(kit.getId()));
            return "addVolKitsUI";
        }
        return "redirect:/event.htm";
    }

    public static Map<String, String> volunteersMap (List<Volunteer> volunteerList)
    {
        Map<String, String> volunteerMap = new LinkedHashMap<String, String>();
        for (Volunteer vol : volunteerList) {
            volunteerMap.put(String.valueOf(vol.getId()), vol.getName());
        }
        return volunteerMap;
    }

    @RequestMapping("/refreshKitsUI")
    public String refreshKitsUI (Map<String, Object> map, HttpServletRequest request)
    {
        String strEventId = request.getParameter("eventId");
        if (!Util.nullOrEmptyOrBlank(strEventId)) {
            request.setAttribute("eventId", strEventId);
        }
        return "forward:/showKitsUI.htm";
    }

    @RequestMapping("/backToEventsAction")
    public String backToEventsAction (Map<String, Object> map, HttpServletRequest request)
    {
        return "redirect:/event.htm";
    }

    @RequestMapping("/backToKitsAction")
    public String backToKitsAction (Map<String, Object> map, HttpServletRequest request)
    {
        String strEventId = request.getParameter("eventId");
        if (!Util.nullOrEmptyOrBlank(strEventId)) {
            request.setAttribute("eventId", strEventId);
        }
        return "forward:/showKitsUI.htm";
    }

    @RequestMapping(value = "/addVolKitsAction", method = RequestMethod.POST)
    public String addVolKitsAction (@ModelAttribute("volunteerKit") VolunteerKit volunteerKit,
                                    HttpServletRequest request)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);
        volunteerKit.initialize(login.getEmail());
        LoggedInVolunteer loggedInVolunteer = volunteerService.getVolunteer(volunteerKit.getVolunteerId()).getLogin();
        volunteerKit.setLoggedInVolunteer(loggedInVolunteer);
        volunteerService.addVolunteerKit(volunteerKit);
        String strEventId = String.valueOf(volunteerKit.getKit().getEvent().getId());
        if (!Util.nullOrEmptyOrBlank(strEventId)) {
            request.setAttribute("eventId", strEventId);
        }
        return "forward:/showKitsUI.htm";
    }

    @RequestMapping(value = "/allotEventKitsAction", method = RequestMethod.POST)
    public String allotEventKitsAction (Kit kit,
                                        HttpServletRequest request)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);
        if (kit.getTimeCreated() == null) {
            kit.initialize(login.getEmail());
        }
        else {
            kit.initializeForUpdate(login.getEmail());
        }
        eventService.manageEventKit(kit);
        request.setAttribute("eventId", kit.getEventId());
        return "forward:/showEventDetailUI.htm";
    }

    @RequestMapping(value = "/allotVolKitsAction", method = RequestMethod.POST)
    public String allotVolKitsAction (Map<String, Object> map, VolunteerKit volunteerKit,
                                      HttpServletRequest request)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);
        if (volunteerKit.getTimeCreated() == null) {
            volunteerKit.initialize(login.getEmail());
        }
        else {
            volunteerKit.initializeForUpdate(login.getEmail());
        }
        eventService.allotVolunteerKits(volunteerKit);

        String strEventId = request.getParameter("kit.event.id");
        if (!Util.nullOrEmptyOrBlank(strEventId)) {
            request.setAttribute("eventId", strEventId);
        }
        return "forward:/showKitsUI.htm";
    }

    @RequestMapping(value = "/addEventFee", method = RequestMethod.POST)
    public String addEventFee (@ModelAttribute("event") EventFee eventFee,
                               HttpServletRequest request)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);
        eventFee.initialize(login.getEmail());
        eventService.addEventFee(eventFee, eventFee.getEventId());
        request.setAttribute("eventId", String.valueOf(eventFee.getEventId()));
        request.setAttribute("isForward", "true");
        return "forward:/showEventDetails.htm";
    }

    @RequestMapping("/deleteEvent")
    public String removeEvent (HttpServletRequest request)
    {
        String strEventId = request.getParameter("eventId");
        if (!Util.nullOrEmptyOrBlank(strEventId)) {
            Integer eventId = Integer.parseInt(strEventId);
            eventService.removeEvent(eventId);
        }
        return "redirect:/event.htm";
    }

    @RequestMapping("/deleteEventFee")
    public String removeEventFee (HttpServletRequest request)
    {
        String strEventFeeId = request.getParameter("eventFeeId");
        String strEventId = request.getParameter("eventId");
        if (!Util.nullOrEmptyOrBlank(strEventFeeId)) {
            Integer eventFeeId = Integer.parseInt(strEventFeeId);
            eventService.removeEventFee(eventFeeId);
        }
        request.setAttribute("eventId", strEventId);
        return "forward:/eventFee.htm";
    }

    @RequestMapping("/allocateSeats")
    public String allocateSeats (HttpServletRequest request, Map<String, Object> map)
    {
        String strEventId = request.getParameter("eventId");
        if (Util.nullOrEmptyOrBlank(strEventId)) {
            return "redirect:/event.htm";
        }

        Integer eventId = Integer.parseInt(strEventId);
        Event event = eventService.getEvent(eventId);
        if (event != null) {
            eventService.allocateSeats(event);
        }
        return "redirect:/event.htm";
    }

    @RequestMapping(value = "/getAllEventFees", produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    List<Option> eventFees (HttpServletRequest request)
    {
        String strEventId = request.getParameter("eventId");
        if (!Util.nullOrEmptyOrBlank(strEventId)) {
            return getAllEventFees(Integer.parseInt(strEventId));
        }
        return null;
    }

    @RequestMapping(value = "/fetchEventFee", produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    Option fetchEventFee (HttpServletRequest request)
    {
        String strEventFeeId = request.getParameter("eventFeeId");
        if (!Util.nullOrEmptyOrBlank(strEventFeeId)) {
            EventFee fee = eventService.getEventFee(Integer.parseInt(strEventFeeId));
            return new Option(fee.getId(), String.valueOf(fee.getAmount()));
        }
        return null;
    }

    @RequestMapping(value = "/getCourseEligibilities", produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    List<Option> getCourseEligibilities (Map<String, Object> map,
                                         HttpServletRequest request)
    {
        String strCourseTypeId = request.getParameter("courseTypeId");
        CourseType courseType = null;
        if (!Util.nullOrEmptyOrBlank(strCourseTypeId)) {
            courseType = eventService.getCourseType(Integer.parseInt(strCourseTypeId));
        }

        List options = new ArrayList();

        if (courseType.getPrimaryEligibility() != null) {
            options.add(
                    new Option(courseType.getPrimaryEligibility().getId(),
                            courseType.getPrimaryEligibility().getName()));
        }

        if (courseType.getSecondaryEligibility() != null) {
            options.add(
                    new Option(courseType.getSecondaryEligibility().getId(),
                            courseType.getSecondaryEligibility().getName()));
        }

        return options;
    }


    @RequestMapping(value = "/referenceGroup", method = RequestMethod.GET)
    public String referenceGroup (Map<String, Object> map)
    {
        map.put("referenceGroup", new ReferenceGroup());
        map.put("referenceGroupList", eventService.listReferenceGroups());
        return "referenceGroup";
    }

    @RequestMapping(value = "/addReferenceGroup", method = RequestMethod.POST)
    public String addReferenceGroup (ReferenceGroup referenceGroup,
                                     Map<String, Object> map,
                                     HttpServletRequest request)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);
        referenceGroup.initialize(login.getEmail());

        eventService.addReferenceGroup(referenceGroup);
        map.put("referenceGroup", new ReferenceGroup());
        map.put("referenceGroupList", eventService.listReferenceGroups());
        return "referenceGroup";
    }
}
