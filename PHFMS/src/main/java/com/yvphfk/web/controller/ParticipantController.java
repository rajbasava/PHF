/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.web.controller;

import com.yvphfk.common.PaymentMode;
import com.yvphfk.common.Util;
import com.yvphfk.model.Login;
import com.yvphfk.model.Option;
import com.yvphfk.model.ParticipantCourseForm;
import com.yvphfk.model.ParticipantCriteria;
import com.yvphfk.model.RegisteredParticipant;
import com.yvphfk.model.RegistrationCriteria;
import com.yvphfk.model.RegistrationPayments;
import com.yvphfk.model.form.Event;
import com.yvphfk.model.form.EventFee;
import com.yvphfk.model.form.EventPayment;
import com.yvphfk.model.form.EventRegistration;
import com.yvphfk.model.form.HistoryRecord;
import com.yvphfk.model.form.Participant;
import com.yvphfk.model.form.ParticipantCourse;
import com.yvphfk.model.form.ParticipantSeat;
import com.yvphfk.model.form.ReferenceGroup;
import com.yvphfk.model.form.validator.PaymentValidator;
import com.yvphfk.model.form.validator.RegistrationValidator;
import com.yvphfk.service.EventService;
import com.yvphfk.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        map.put("participantCriteria", new ParticipantCriteria());
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