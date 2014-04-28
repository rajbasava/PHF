/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.web.controller;

import com.yvphfk.common.Util;
import com.yvphfk.model.Login;
import com.yvphfk.model.Option;
import com.yvphfk.model.ParticipantCourseForm;
import com.yvphfk.model.ParticipantCriteria;
import com.yvphfk.model.TrainerCriteria;
import com.yvphfk.model.form.Participant;
import com.yvphfk.model.form.ParticipantCourse;
import com.yvphfk.model.form.Trainer;
import com.yvphfk.model.form.TrainerCourse;
import com.yvphfk.service.EventService;
import com.yvphfk.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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

        participantCourse = participantService.addParticipantCourse(participantCourseForm);

        request.setAttribute("isForward", "true");
        request.setAttribute("isEdit", "false");
        request.setAttribute("participantId", String.valueOf(participantCourse.getParticipant().getId()));
        return "forward:/showParticipantDetails.htm";
    }

    @RequestMapping("/listTrainers")
    public String listTrainers (Map<String, Object> map,
                                TrainerCriteria trainerCriteria,
                                HttpServletRequest request)
    {
        map.put("trainerCriteria", trainerCriteria);
        if (trainerCriteria != null) {
            //todo if the page is replace, we should search participant that are not participating in the current event.
            map.put("trainers", participantService.listTrainers(trainerCriteria));
            map.put("allParticipantCourseTypes", allCourseTypes());
            map.put("allFoundations", allFoundations());
        }

        return "manageTrainers";
    }

    @RequestMapping(value = "/getTrainersForCourse", produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    List<Option> getTrainersForCourse (Map<String, Object> map,
                                       HttpServletRequest request)
    {
        String strCourseTypeId = request.getParameter("courseTypeId");
        TrainerCriteria trainerCriteria = new TrainerCriteria();
        if (!Util.nullOrEmptyOrBlank(strCourseTypeId)) {
            trainerCriteria.setCourseTypeId(Integer.parseInt(strCourseTypeId));
        }

        List<Trainer> trainers = participantService.listTrainers(trainerCriteria);
        List options = new ArrayList();
        for (Trainer trainer : trainers) {
            options.add(new Option(trainer.getId(), trainer.getParticipant().getName()));
        }

        return options;
    }

    @RequestMapping("/showManageTrainers")
    public String showManageTrainers (Map<String, Object> map)
    {
        map.put("trainerCriteria", new TrainerCriteria());
        map.put("allParticipantCourseTypes", allCourseTypes());
        map.put("allFoundations", allFoundations());
        return "manageTrainers";
    }

    @RequestMapping("/addTrainer")
    public String addTrainer (Map<String, Object> map,
                              HttpServletRequest request)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);

        Participant participant = null;
        String strParticipantId = request.getParameter("participantId");
        if (!Util.nullOrEmptyOrBlank(strParticipantId)) {
            participant = participantService.getParticipant(Integer.parseInt(strParticipantId));
        }
        Trainer trainer = new Trainer();
        trainer.setParticipant(participant);
        trainer.initialize(login.getEmail());

        trainer = participantService.addTrainer(trainer);

        request.setAttribute("isForward", "true");
        request.setAttribute("isEdit", "false");
        request.setAttribute("participantId", String.valueOf(trainer.getParticipant().getId()));
        request.setAttribute("trainerId", String.valueOf(trainer.getId()));
        return "forward:/showTrainerDetails.htm";
    }

    @RequestMapping("/showTrainerDetails")
    public String showTrainerDetails (Map<String, Object> map,
                                      HttpServletRequest request)
    {
        boolean isFwd = false;
        String strIsFwd = (String) request.getAttribute("isForward");

        if (!Util.nullOrEmptyOrBlank(strIsFwd)) {
            isFwd = Boolean.parseBoolean(strIsFwd);
        }

        String strTrainerId = null;
        String strIsEdit = null;
        if (isFwd) {
            strTrainerId = (String) request.getAttribute("trainerId");
            strIsEdit = (String) request.getAttribute("isEdit");
        }
        else {
            strTrainerId = request.getParameter("trainerId");
            strIsEdit = request.getParameter("isEdit");
        }

        Integer trainerId = null;
        if (!Util.nullOrEmptyOrBlank(strTrainerId)) {
            trainerId = Integer.parseInt(strTrainerId);
        }

        boolean isEdit = false;
        if (!Util.nullOrEmptyOrBlank(strIsEdit)) {
            isEdit = Boolean.parseBoolean(strIsEdit);
        }

        Trainer trainer = participantService.getTrainer(trainerId);
        Participant participant = trainer.getParticipant();

        List<TrainerCourse> courses = participantService.getTrainerCourses(trainer.getId());

        map.put("participant", participant);
        map.put("trainer", trainer);
        map.put("courses", courses);
        return "trainerDetails";
    }

    @RequestMapping("/showAddTrainerCourse")
    public String showAddTrainerCourse (Map<String, Object> map,
                                        HttpServletRequest request)
    {
        String strTrainerId = request.getParameter("trainerId");

        Integer trainerId = null;
        if (!Util.nullOrEmptyOrBlank(strTrainerId)) {
            trainerId = Integer.parseInt(strTrainerId);
        }

        Trainer trainer = participantService.getTrainer(trainerId);
        TrainerCourse trainerCourse = new TrainerCourse();
        trainerCourse.setTrainer(trainer);
        trainerCourse.setTrainerId(trainer.getId());

        map.put("trainerCourse", trainerCourse);
        map.put("trainer", trainer);
        map.put("trainerId", strTrainerId);
        map.put("allParticipantCourseTypes", allCourseTypes());
        map.put("allFoundations", allFoundations());
        return "addTrainerCourse";
    }

    @RequestMapping("/addTrainerCourse")
    public String addTrainerCourse (Map<String, Object> map,
                                    HttpServletRequest request,
                                    TrainerCourse trainerCourse)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);

        Trainer trainer =
                participantService.getTrainer(
                        trainerCourse.getTrainerId());
        trainerCourse.setTrainer(trainer);
        trainerCourse.setCourseType(
                eventService.getCourseType(
                        trainerCourse.getCourseTypeId()));
        trainerCourse.setFoundation(
                eventService.getFoundation(
                        trainerCourse.getFoundationId()));

        trainerCourse.initialize(login.getEmail());
        trainerCourse = participantService.addTrainerCourse(trainerCourse);

        request.setAttribute("isForward", "true");
        request.setAttribute("isEdit", "false");
        request.setAttribute("participantId", String.valueOf(trainer.getParticipant().getId()));
        request.setAttribute("trainerId", String.valueOf(trainer.getId()));
        return "forward:/showTrainerDetails.htm";
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
        ParticipantCourseForm participantCourseForm = new ParticipantCourseForm();
        String strParticipantId = request.getParameter("participantId");
        if (!Util.nullOrEmptyOrBlank(strParticipantId)) {
            participantCourseForm.setParticipantId(Integer.parseInt(strParticipantId));
        }
        map.put("participantCourseForm", participantCourseForm);
        map.put("showParticipantDetails", Util.nullOrEmptyOrBlank(strParticipantId));
        map.put("allParticipantCourseTypes", allCourseTypes());
        map.put("allFoundations", allFoundations());
        map.put("allTrainers", participantService.listTrainers(new TrainerCriteria()));
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
        List list = participantService.getEligibleCourses(participantId);
        Trainer trainer = participantService.getTrainer(participant.getId());

        map.put("participant", participant);
        map.put("courses", courses);
        map.put("newEvents", list.get(0));
        map.put("reviewEvents", list.get(1));
        map.put("isEdit", isEdit);
        map.put("isTrainer", trainer == null ? true : false);
        return "participantDetails";
    }


}