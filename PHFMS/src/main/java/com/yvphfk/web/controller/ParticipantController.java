/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.web.controller;

import com.yvphfk.model.Login;
import com.yvphfk.model.ParticipantCriteria;
import com.yvphfk.model.ParticipantCourseForm;
import com.yvphfk.model.form.ParticipantCourse;
import com.yvphfk.service.EventService;
import com.yvphfk.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
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
    public String createParticipant (Map<String, Object> map,
                                     ParticipantCourseForm participantCourseForm,
                                     HttpServletRequest request)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);
        participantCourseForm.initialize(login.getEmail());
        ParticipantCourse participantCourse = participantCourseForm.getParticipantCourse();
        participantCourse.setCourseType(eventService.getCourseType(participantCourse.getCourseTypeId()));
        participantService.addParticipantCourse(participantCourseForm);
        return "redirect:/showParticipantCourses.htm";
    }

    @RequestMapping("/showAddParticipantCourse")
    public String showAddParticipantCourse (HttpServletRequest request, Map<String, Object> map)
    {
        map.put("participantCourseForm", new ParticipantCourseForm());
        map.put("allParticipantCourseTypes", allCourseTypes());
        map.put("allFoundations", allFoundations());
        return "addParticipantCourse";
    }

}