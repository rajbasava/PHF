/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.web.controller;

import com.yvphfk.common.Util;
import com.yvphfk.model.Login;
import com.yvphfk.model.ParticipantCourseForm;
import com.yvphfk.model.ParticipantCriteria;
import com.yvphfk.model.form.Participant;
import com.yvphfk.model.form.ParticipantCourse;
import com.yvphfk.service.EventService;
import com.yvphfk.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class ParticipantController extends CommonController
{
    @Autowired
    private ParticipantService participantService;

    @Autowired
    private EventService eventService;

    @RequestMapping("/listParticipants")
    public String listParticipants (Map<String, Object> map,
                                    ParticipantCriteria participantCriteria,
                                    HttpServletRequest request)
    {
        map.put("participantCriteria", participantCriteria);
        if (participantCriteria != null) {
            //todo if the page is replace, we should search participant that are not participating in the current event.
            map.put("participantList", participantService.listParticipants(participantCriteria));
            map.put("allParticipantCourseTypes", allCourseTypes());
            map.put("allFoundations", allFoundations());
        }

        String page = request.getParameter("page");
        map.put("page", page);


//        String strRegistrationId = request.getParameter("registrationId");
//        RegisteredParticipant registeredParticipant = populateRegisteredParticipant(strRegistrationId);
//        if (registeredParticipant != null) {
//            map.put("registeredParticipant", registeredParticipant);
//        }
        return page;
    }

    @RequestMapping("/searchParticipants")
    public String searchParticipants (Map<String, Object> map)
    {
        map.put("participantCriteria", new ParticipantCriteria());
        map.put("allParticipantCourseTypes", allCourseTypes());
        map.put("allFoundations", allFoundations());
        map.put("page", "searchParticipants");
        return "searchParticipants";
    }

    @RequestMapping("/addParticipantCourse")
    public String addParticipantCourse (Map<String, Object> map,
                                        ParticipantCourseForm participantCourseForm,
                                        HttpServletRequest request)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);
        participantCourseForm.initialize(login.getEmail());

        ParticipantCourse participantCourse = participantCourseForm.getParticipantCourse();
        participantCourse.setCourseType(
                eventService.getCourseType(participantCourse.getCourseTypeId()));
        participantCourse.setFoundation(
                eventService.getFoundation(participantCourse.getFoundationId()));

        Participant participant = null;
        if (participantCourseForm.getParticipantId() != null) {
            participant = participantService.getParticipant(participantCourseForm.getParticipantId());
        }
        else {
            participant = participantCourseForm.getParticipant();
        }
        participantCourse.setParticipant(participant);

        participantService.addParticipantCourse(participantCourseForm);

        request.setAttribute("isForward", "true");
        request.setAttribute("isEdit", "false");
        request.setAttribute("participantId", String.valueOf(participantCourseForm.getParticipantId()));
        return "forward:/showParticipantDetails.htm";
    }

    @RequestMapping("/updateParticipant")
    public String addParticipantCourse (Map<String, Object> map,
                                        Participant participant,
                                        HttpServletRequest request)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);
        participant.initializeForUpdate(login.getEmail());
        participantService.saveOrUpdateParticipant(participant);
        request.setAttribute("isForward", "true");
        request.setAttribute("isEdit", "false");
        request.setAttribute("participantId", String.valueOf(participant.getId()));
        return "forward:/showParticipantDetails.htm";
    }

    @RequestMapping("/showAddParticipantCourse")
    public String showAddParticipantCourse (HttpServletRequest request, Map<String, Object> map)
    {
        ParticipantCourseForm participantCourseForm =  new ParticipantCourseForm();
        String strParticipantId = request.getParameter("participantId");
        if (!Util.nullOrEmptyOrBlank(strParticipantId)) {
            participantCourseForm.setParticipantId(Integer.parseInt(strParticipantId));
        }
        map.put("participantCourseForm", participantCourseForm);
        map.put("showParticipantDetails", Util.nullOrEmptyOrBlank(strParticipantId));
        map.put("allParticipantCourseTypes", allCourseTypes());
        map.put("allFoundations", allFoundations());
        return "addParticipantCourse";
    }

    @RequestMapping("/showParticipantDetails")
    public String showParticipantDetails (HttpServletRequest request, Map<String, Object> map)
    {
        boolean isFwd = false;
        String strIsFwd = (String) request.getAttribute("isForward");
        if (!Util.nullOrEmptyOrBlank(strIsFwd)) {
            isFwd = Boolean.parseBoolean(strIsFwd);
        }

        String strParticipantId = null;
        String strIsEdit = null;
        if (isFwd) {
            strParticipantId = (String) request.getAttribute("participantId");
            strIsEdit = (String) request.getAttribute("isEdit");
        }
        else {
            strParticipantId = request.getParameter("participantId");
            strIsEdit = request.getParameter("isEdit");
        }

        Integer participantId = null;
        if (!Util.nullOrEmptyOrBlank(strParticipantId)) {
            participantId = Integer.parseInt(strParticipantId);
        }

        boolean isEdit = false;
        if (!Util.nullOrEmptyOrBlank(strIsEdit)) {
            isEdit = Boolean.parseBoolean(strIsEdit);
        }

        Participant participant = participantService.getParticipant(participantId);
        List<ParticipantCourse> courses = participantService.getCourses(participantId);

        map.put("participant", participant);
        map.put("courses", courses);
        map.put("isEdit", isEdit);
        return "participantDetails";
    }

}