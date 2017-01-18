/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.web.controller;

import com.yvphfk.common.PaymentMode;
import com.yvphfk.common.Util;
import com.yvphfk.model.Login;
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
import com.yvphfk.model.form.ParticipantSeat;
import com.yvphfk.model.form.WorkshopLevel;
import com.yvphfk.model.form.validator.ParticipantValidator;
import com.yvphfk.model.form.validator.PaymentValidator;
import com.yvphfk.model.form.validator.RegistrationValidator;
import com.yvphfk.service.EventService;
import com.yvphfk.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class RegistrationController extends CommonController
{
    @Autowired
    private ParticipantService participantService;

    @Autowired
    private EventService eventService;

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

    @RequestMapping(value = "/registerTab", method = RequestMethod.GET)
    public String register ()
    {
        return "registerTab";
    }

    @RequestMapping("/register")
    public String newParticipant (Map<String, Object> map, HttpServletRequest request)
    {
        String strParticipantId = request.getParameter("participantId");
        String strEventId = request.getParameter("eventId");
        String strReview = request.getParameter("review");
        Event event = null;
        boolean review = false;
        Participant participant = new Participant();
        boolean newbie = true;
        if (!Util.nullOrEmptyOrBlank(strParticipantId) &&
                !Util.nullOrEmptyOrBlank(strEventId) &&
                !Util.nullOrEmptyOrBlank(strReview)) {
            participant = participantService.getParticipant(Integer.parseInt(strParticipantId));
            event = eventService.getEvent(Integer.parseInt(strEventId));
            review = Boolean.parseBoolean(strReview);
            newbie = false;
        }

        RegisteredParticipant registeredParticipant = new RegisteredParticipant();
        registeredParticipant.getRegistration().setReview(review);
        if (event != null) {
            registeredParticipant.setEventId(event.getId());
        }

        if (participant != null) {
            registeredParticipant.setParticipant(participant);
        }
        registeredParticipant.setAction(RegisteredParticipant.ActionRegister);
        registeredParticipant.setNewbie(newbie);
        map.put("participant", participant);
        map.put("registeredParticipant", registeredParticipant);
        map.put("allParticipantCourseTypes", allArhaticCourseTypes());
        map.put("allPaymentModes", PaymentMode.allPaymentModes());
        map.put("allFoundations", allFoundations());
        map.put("allEvents", getAllEventMap(eventService.allEvents()));
        map.put("allReferenceGroups", getAllReferenceGroups(eventService.listReferenceGroups()));
        map.put("newbie", newbie);
        return "register";
    }

    @RequestMapping("/search")
    public String search (Map<String, Object> map)
    {
        RegistrationCriteria criteria = new RegistrationCriteria();
        map.put("registrationCriteria", criteria);
        map.put("allParticipantCourseTypes", allCourseTypes());
        map.put("allFoundations", allFoundations());
        map.put("allEvents", getAllEventMap(eventService.allEvents()));
        map.put("allStatuses", getRegistrationStatusMap());
        map.put("allReferenceGroups", getAllReferenceGroups(eventService.listReferenceGroups()));
        return "search";
    }

    @RequestMapping("/list")
    public String searchRegistration (Map<String, Object> map,
                                      RegistrationCriteria registrationCriteria)
    {
        map.put("registrationCriteria", registrationCriteria);
        if (registrationCriteria != null) {
            map.put("registrationList", participantService.listRegistrations(registrationCriteria));
            map.put("allParticipantCourseTypes", allCourseTypes());
            map.put("allFoundations", allFoundations());
            map.put("allEvents", getAllEventMap(eventService.allEvents()));
            map.put("allStatuses", getRegistrationStatusMap());
            map.put("allReferenceGroups", getAllReferenceGroups(eventService.listReferenceGroups()));
        }
        return "search";
    }

    @RequestMapping(value = "/addRegistration", method = RequestMethod.POST)
    public String addRegistration (RegisteredParticipant registeredParticipant,
                                   BindingResult errors,
                                   Map<String, Object> map,
                                   HttpServletRequest request)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);
        String action = registeredParticipant.getAction();

        EventRegistration eventRegistration = registeredParticipant.getRegistration();
        Event event = eventService.getEvent(registeredParticipant.getEventId());
        eventRegistration.setEvent(event);

        WorkshopLevel workshopLevel = eventService.getWorkshopLevel(eventRegistration.getWorkshopLevelId());
        eventRegistration.setWorkshopLevel(workshopLevel);
        eventRegistration.setWorkshopLevelId(workshopLevel.getId());

        eventRegistration.setFoundation(
                eventService.getFoundation(eventRegistration.getFoundationId()));

        EventFee eventFee = eventService.getEventFee(registeredParticipant.getEventFeeId());
        if (eventFee != null) {
            eventRegistration.setEventFee(eventFee);
            eventRegistration.setEventFeeId(eventFee.getEventId());
        }

        Participant participant = null;
        if (registeredParticipant.getParticipant().getId() != null) {
            participant = participantService.getParticipant(registeredParticipant.getParticipant().getId());
            registeredParticipant.setParticipant(participant);
        }

        RegistrationValidator validator = new RegistrationValidator();
        validator.validate(registeredParticipant, errors);

        if (errors.hasErrors()) {
            map.put("participant", registeredParticipant.getParticipant());
            if (event != null) {
                map.put("workshopLevels", getWorkshopLevelMap(event.getId()));
                map.put("allEventFees", getAllEventFees(registeredParticipant.getEventId(),
                        eventRegistration.isReview(),
                        eventRegistration.getWorkshopLevelId(), Boolean.FALSE));
            }
            map.put("allPaymentModes", PaymentMode.allPaymentModes());
            map.put("allFoundations", allFoundations());
            map.put("allEvents", getAllEventMap(eventService.allEvents()));
            map.put("allReferenceGroups", getAllReferenceGroups(eventService.listReferenceGroups()));
            map.put("newbie", registeredParticipant.isNewbie());

            if (RegisteredParticipant.ActionRegister.equals(action)) {
                map.put("registeredParticipant", registeredParticipant);
                return "register";
            }
            else if (RegisteredParticipant.ActionUpdate.equals(action)) {
                EventRegistration registration =
                        participantService.getEventRegistration(
                                registeredParticipant.getRegistration().getId());
                registeredParticipant.getRegistration().setTotalAmountPaid(registration.getTotalAmountPaid());
                registeredParticipant.getRegistration().setAmountDue(registration.getAmountDue());
                map.put("registeredParticipant", registeredParticipant);

                return "registerTab";
            }
        }
        
        String returnPage = "summary";

        boolean isAttend =  request.getParameter("attend") != null &&
                request.getParameter("attend").equalsIgnoreCase("true");

        if (isAttend) {
            registeredParticipant.getRegistration().setAttend(true);
            returnPage = "summary";
        }
        else if (RegisteredParticipant.ActionUpdate.equals(action)) {
            returnPage = "redirect:/search.htm";
        }

        if (RegisteredParticipant.ActionRegister.equals(action)) {
            registeredParticipant.getRegistration().setRefOrder(1);
            registeredParticipant.initialize(login.getEmail());
            ParticipantSeat seat =
                    eventService.nextSeat(
                            registeredParticipant.getRegistration().getEvent(),
                            registeredParticipant.getRegistration());
            registeredParticipant.setCurrentSeat(seat);
        }

        if (RegisteredParticipant.ActionUpdate.equals(action)) {
            registeredParticipant.initializeForUpdate(login.getEmail());
        }

        EventRegistration registration = participantService.registerParticipant(registeredParticipant, login);

        if (isAttend) {
            participantService.createAndAddHistoryRecord(
                    messageSource.getMessage("key.registrationAttend",
                            new Object[]{registration.getId()}, null),
                    login.getEmail(),
                    registration
            );
        }

        registeredParticipant = populateRegisteredParticipant(String.valueOf(registration.getId()));
        map.put("registeredParticipant", registeredParticipant);

        return returnPage;
    }

    @RequestMapping("/updateRegistration")
    public String updateRegistration (HttpServletRequest request, Map<String, Object> map)
    {
        String strRegistrationId = request.getParameter("registrationId");
        RegisteredParticipant registeredParticipant = populateRegisteredParticipant(strRegistrationId);
        if (registeredParticipant != null) {
            map.put("registeredParticipant", registeredParticipant);
            map.put("participant", registeredParticipant.getParticipant());
            map.put("workshopLevels", getWorkshopLevelMap(registeredParticipant.getEventId()));
            map.put("allPaymentModes", PaymentMode.allPaymentModes());
            map.put("allFoundations", allFoundations());
            map.put("allEvents", getAllEventMap(eventService.allEvents()));
            map.put("allEventFees", getAllEventFees(registeredParticipant.getEventId(), null, null, Boolean.TRUE));
            map.put("allReferenceGroups", getAllReferenceGroups(eventService.listReferenceGroups()));
            return "registerTab";
        }
        return "null";
    }

    private RegisteredParticipant populateRegisteredParticipant (String strRegistrationId)
    {
        if (!Util.nullOrEmptyOrBlank(strRegistrationId)) {
            Integer registrationId = Integer.parseInt(strRegistrationId);
            EventRegistration registration = participantService.getEventRegistration(registrationId);

            if (registration.getWorkshopLevel() != null) {
                registration.setWorkshopLevelId(registration.getWorkshopLevel().getId());
            }

            registration.setFoundationId(registration.getFoundation().getId());
            RegisteredParticipant registeredParticipant = new RegisteredParticipant();
            registeredParticipant.setRegistration(registration);
            registeredParticipant.setParticipant(registration.getParticipant());
            registeredParticipant.setAllHistoryRecords(registration.getHistoryRecords());
            registeredParticipant.setEventId(registration.getEvent().getId());
            registeredParticipant.setEventFeeId(registration.getEventFee().getId());
            registeredParticipant.setAction(RegisteredParticipant.ActionUpdate);
            registeredParticipant.setStatus(registration.getStatus());
            return registeredParticipant;
        }

        return null;
    }

    @RequestMapping("/showPayments")
    public String showPayments (HttpServletRequest request, Map<String, Object> map)
    {
        String strRegistrationId = request.getParameter("registration.id");
        String strPaymentId = request.getParameter("paymentId");
        RegistrationPayments registrationPayments =
                populateRegistrationPayments(strRegistrationId, strPaymentId);
        map.put("registrationPayments", registrationPayments);
        map.put("allPaymentModes", PaymentMode.allPaymentModes());
        return "payments";
    }

    @RequestMapping("/processPayments")
    public ModelAndView processPayments (RegistrationPayments registrationPayments, BindingResult errors, HttpServletRequest request)
    {
        ModelAndView mv = null;
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);
        EventPayment payment = registrationPayments.getCurrentPayment();
        boolean isAdd = RegistrationPayments.Add.equals(registrationPayments.getAction());
        if (isAdd) {
            payment.initialize(login.getEmail());
        }
        else {
            payment.initializeForUpdate(login.getEmail());
        }
        PaymentValidator val = new PaymentValidator();
        val.validate(registrationPayments, errors);
        if (errors.hasErrors()) {
            /*String strRegistrationId = request.getParameter("registration.id");
            String strPaymentId = request.getParameter("paymentId");*/
            /*RegistrationPayments registrationPayments =
                    populateRegistrationPayments(strRegistrationId, strPaymentId);*/
            mv = new ModelAndView("payments");
            mv.addObject("errors", errors);
//        	mv.addObject("registrationPayments", registrationPayments);
            mv.addObject("allPaymentModes", PaymentMode.allPaymentModes());
            return mv;
        }
        else {
            participantService.processPayment(payment, registrationPayments.getRegistrationId(), isAdd);
        }
        mv = new ModelAndView("forward:/updateRegistration.htm");
        request.setAttribute("registrationId", registrationPayments.getRegistrationId());
        return mv;
    }

    private RegistrationPayments populateRegistrationPayments (String strRegistrationId, String strPaymentId)
    {
        if (Util.nullOrEmptyOrBlank(strRegistrationId)) {
            return null;
        }

        Integer registrationId = Integer.parseInt(strRegistrationId);

        EventRegistration registration = participantService.getEventRegistration(registrationId);

        RegistrationPayments registrationPayments = new RegistrationPayments();
        registrationPayments.setRegistration(registration);
        registrationPayments.setRegistrationId(registration.getId());

        List<EventPayment> payments = new ArrayList<EventPayment>();
        payments.addAll(registration.getPayments());
        registrationPayments.setPayments(payments);

        if (!Util.nullOrEmptyOrBlank(strPaymentId)) {
            Integer paymentId = Integer.parseInt(strPaymentId);
            for (EventPayment payment : payments) {
                // testing
                if (payment.getId().equals(paymentId)) {
                    registrationPayments.setCurrentPayment(payment);
                    registrationPayments.setAction(RegistrationPayments.Update);
                }
            }
            if (registrationPayments.getCurrentPayment() == null) {
                registrationPayments.setCurrentPayment(new EventPayment());
                registrationPayments.setAction(RegistrationPayments.Add);
            }
        }
        else {
            registrationPayments.setCurrentPayment(new EventPayment());
            registrationPayments.setAction(RegistrationPayments.Add);
        }
        return registrationPayments;
    }

    @RequestMapping("/cancelRegistration")
    public String cancelRegistration (Map<String, Object> map,
                                      RegisteredParticipant registeredParticipant,
                                      HttpServletRequest request)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);
        if (registeredParticipant.getRegistration() != null) {
            EventRegistration registration =
                    participantService.getEventRegistration(
                            registeredParticipant.getRegistration().getId());
            registeredParticipant.initializeHistoryRecords(login.getEmail());
            participantService.cancelRegistration(registration,
                    registeredParticipant.getCurrentHistoryRecord());
        }
        return "redirect:/search.htm";
    }

    @RequestMapping("/onHoldRegistration")
    public String onHoldRegistration (Map<String, Object> map,
                                      RegisteredParticipant registeredParticipant,
                                      HttpServletRequest request)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);
        if (registeredParticipant.getRegistration() != null) {
            EventRegistration registration =
                    participantService.getEventRegistration(
                            registeredParticipant.getRegistration().getId());
            registeredParticipant.initializeHistoryRecords(login.getEmail());
            participantService.onHoldRegistration(registration,
                    registeredParticipant.getCurrentHistoryRecord());
        }
        return "redirect:/search.htm";
    }

    @RequestMapping("/changeToRegistered")
    public String changeToRegistered (Map<String, Object> map,
                                      RegisteredParticipant registeredParticipant,
                                      HttpServletRequest request)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);
        if (registeredParticipant.getRegistration() != null) {
            EventRegistration registration =
                    participantService.getEventRegistration(
                            registeredParticipant.getRegistration().getId());
            registeredParticipant.initializeHistoryRecords(login.getEmail());
            if (registration.getSeats().size() == 0) {
                ParticipantSeat seat =
                        eventService.nextSeat(
                                registration.getEvent(),
                                registration);
                if (seat != null) {
                    seat.setRegistrationId(registration.getId());
                    participantService.addParticipantSeat(seat);
                }
            }
            participantService.changeToRegistered(registration,
                    registeredParticipant.getCurrentHistoryRecord());
        }
        return "redirect:/search.htm";
    }

    @RequestMapping(value = "/attendRegistration", method = RequestMethod.POST)
    public String attendRegistration (RegisteredParticipant registeredParticipant,
                                      Map<String, Object> map,
                                      HttpServletRequest request)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);

        String strRegistrationId = request.getParameter("registrationId");
        Integer registrationId = null;
        if (Util.nullOrEmptyOrBlank((strRegistrationId))) {
            registrationId = registeredParticipant.getRegistration().getId();
        }
        else {
            registrationId = Integer.parseInt(strRegistrationId);
        }

        EventRegistration registration = participantService.getEventRegistration(registrationId);
        registration.initializeForUpdate(login.getEmail());
        registration.setAttend(true);

        participantService.saveOrUpdate(registration);

        participantService.createAndAddHistoryRecord(
                messageSource.getMessage("key.registrationAttend",
                        new Object[]{registration.getId()}, null),
                login.getEmail(),
                registration);

        if (registeredParticipant != null &&
                registeredParticipant.getCurrentHistoryRecord() != null &&
                !Util.nullOrEmptyOrBlank(registeredParticipant.getCurrentHistoryRecord().getComment())) {
            participantService.createAndAddHistoryRecord(
                    registeredParticipant.getCurrentHistoryRecord().getComment(),
                    Util.getCurrentUser().getEmail(),
                    registration);
        }

        RegisteredParticipant registeredParticipantTmp =
                populateRegisteredParticipant(String.valueOf(registration.getId()));
        map.put("registeredParticipant", registeredParticipantTmp);

        return "summary";
    }

    @RequestMapping("/unattendRegistration")
    public String unattendRegistration (Map<String, Object> map,
                                        HttpServletRequest request)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);

        String strRegistrationId = request.getParameter("registrationId");
        Integer registrationId = Integer.parseInt(strRegistrationId);

        EventRegistration registration = participantService.getEventRegistration(registrationId);
        registration.initializeForUpdate(login.getEmail());
        registration.setAttend(false);

        participantService.saveOrUpdate(registration);

        participantService.createAndAddHistoryRecord(
                messageSource.getMessage("key.registrationUnattend",
                        new Object[]{registration.getId()}, null),
                login.getEmail(),
                registration);

        RegisteredParticipant registeredParticipant =
                populateRegisteredParticipant(String.valueOf(registration.getId()));
        map.put("registeredParticipant", registeredParticipant);

        return "redirect:/search.htm";
    }

    @RequestMapping("/showAttendanceSummary")
    public String showAttendanceSummary (Map<String, Object> map,
                                         HttpServletRequest request)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);

        String strRegistrationId = request.getParameter("registrationId");
        Integer registrationId = Integer.parseInt(strRegistrationId);

        EventRegistration registration = participantService.getEventRegistration(registrationId);

        RegisteredParticipant registeredParticipant =
                populateRegisteredParticipant(String.valueOf(registration.getId()));
        map.put("registeredParticipant", registeredParticipant);

        return "summary";
    }

    @RequestMapping("/showReplaceRegistration")
    public String showReplaceRegistration (Map<String, Object> map,
                                           HttpServletRequest request)
    {

        map.put("participantCriteria", new ParticipantCriteria());
        map.put("allParticipantCourseTypes", allArhaticCourseTypes());
        map.put("allFoundations", allFoundations());
        map.put("page", "replaceRegistration");

        String strRegistrationId = request.getParameter("registration.id");
        RegisteredParticipant registeredParticipant = populateRegisteredParticipant(strRegistrationId);
        if (registeredParticipant != null) {
            map.put("participantName", registeredParticipant.getParticipant().getName());
            map.put("eventId", registeredParticipant.getRegistration().getEvent().getId());
            map.put("eventName", registeredParticipant.getRegistration().getEvent().getName());
            map.put("registrationId", registeredParticipant.getRegistration().getId());
        }
        return "replaceRegistration";
    }

    @RequestMapping("/showAddParticipantForReplacement")
    public String showAddParticipantForReplacement (Map<String, Object> map,
                                                    HttpServletRequest request)
    {

        map.put("participant", new Participant());
        String strRegistrationId = request.getParameter("registrationId");
        RegisteredParticipant registeredParticipant = populateRegisteredParticipant(strRegistrationId);
        if (registeredParticipant != null) {
            map.put("participantName", registeredParticipant.getParticipant().getName());
            map.put("eventId", registeredParticipant.getRegistration().getEvent().getId());
            map.put("eventName", registeredParticipant.getRegistration().getEvent().getName());
        }
        map.put("registrationId", strRegistrationId);
        return "addParticipantForReplacement";
    }

    @RequestMapping("/addParticipantForReplacement")
    public String addParticipantForReplacement(@ModelAttribute("participant")
                                                       Participant participant,
                                               BindingResult errors,
                                               Map<String, Object> map,
                                               HttpServletRequest request)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);
        participant.initialize(login.getEmail());

        ParticipantValidator val = new ParticipantValidator();
        val.validate(participant, errors);

        if (errors.hasErrors()) {
            map.put("errors", errors);
            map.put("participant", participant);
            map.put("registrationId", request.getParameter("registrationId"));
            map.put("participantName", request.getParameter("participantName"));
            map.put("eventName", request.getParameter("eventName"));
            return "addParticipantForReplacement";
        }

        participant = participantService.saveOrUpdateParticipant(participant);

        request.setAttribute("registrationId", request.getParameter("registrationId"));
        request.setAttribute("participantId", String.valueOf(participant.getId()));
        request.setAttribute("comments", request.getParameter("comments"));
        return "forward:/replaceRegistration.htm";
    }



    @RequestMapping("/replaceRegistration")
    public String replaceRegistration (Map<String, Object> map,
                                       ParticipantCriteria participantCriteria,
                                       Participant participant,
                                       BindingResult errors,
                                       HttpServletRequest request)
    {
        String strRegistrationId = (String) request.getParameter("registrationId");
        String strParticipantId =  request.getParameter("participantId");
        if (Util.nullOrEmptyOrBlank(strParticipantId)) {
            strParticipantId = (String) request.getAttribute("participantId");
        }
        String comment = (String) request.getParameter("comments");
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);

        if (Util.nullOrEmptyOrBlank(strRegistrationId) || Util.nullOrEmptyOrBlank(strParticipantId)) {
            errors.reject("participant.name.unique");

            map.put("participantCriteria", participantCriteria);
            map.put("allParticipantCourseTypes", allArhaticCourseTypes());
            map.put("allFoundations", allFoundations());
            map.put("page", "replaceRegistration");

            strRegistrationId = request.getParameter("registrationId");
            RegisteredParticipant registeredParticipant = populateRegisteredParticipant(strRegistrationId);
            if (registeredParticipant != null) {
                map.put("participantName", registeredParticipant.getParticipant().getName());
                map.put("eventId", registeredParticipant.getRegistration().getEvent().getId());
                map.put("eventName", registeredParticipant.getRegistration().getEvent().getName());
                map.put("registrationId", registeredParticipant.getRegistration().getId());
            }

            return "replaceRegistration";
        }

        Integer registrationId = Integer.parseInt(strRegistrationId);
        Integer participantId = Integer.parseInt(strParticipantId);
        EventRegistration registration = participantService.getEventRegistration(registrationId);
        Participant participantToReplace = participantService.getParticipant(participantId);

        HistoryRecord record = null;
        if (!Util.nullOrEmptyOrBlank(comment)) {
            record = new HistoryRecord();
            record.setComment(comment);
            record.initialize(login.getEmail());
        }

        participantService.replaceParticipant(registration, participantToReplace, record);

        map.put("registrationId", strRegistrationId);
        return "forward:/updateRegistration.htm";
    }

}
