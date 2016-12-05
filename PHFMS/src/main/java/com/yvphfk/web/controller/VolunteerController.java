/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.web.controller;

import com.yvphfk.common.CommonCache;
import com.yvphfk.common.Util;
import com.yvphfk.common.VolunteerPermission;
import com.yvphfk.model.AccessFilterForm;
import com.yvphfk.model.Login;
import com.yvphfk.model.form.AccessControl;
import com.yvphfk.model.form.AccessFilter;
import com.yvphfk.model.form.BaseForm;
import com.yvphfk.model.form.Event;
import com.yvphfk.model.form.PHFoundation;
import com.yvphfk.model.form.Volunteer;
import com.yvphfk.service.EventService;
import com.yvphfk.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes({"user"})
public class VolunteerController extends CommonController
{
    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private EventService eventService;

    @RequestMapping("/volunteer")
    public String listVolunteer (Map<String, Object> map)
    {
        map.put("volunteer", new Volunteer());
        map.put("volunteerList", volunteerService.listVolunteer());
        map.put("allVolunteerPermissions", VolunteerPermission.allVolunteerPermissions());
        return "volunteer";
    }

    @RequestMapping(value = "/addVolunteer", method = RequestMethod.POST)
    public String newVolunteer (@ModelAttribute("volunteer") Volunteer volunteer,
                                HttpServletRequest request)
    {
        Login login = (Login) request.getSession().getAttribute(Login.ClassName);
        volunteer.setPreparedBy(login.getEmail());
        volunteerService.addVolunteer(volunteer);
        return "redirect:/volunteer.htm";
    }

    @RequestMapping("/delete")
    public String removeVolunteer (HttpServletRequest request)
    {
        String strVolunteerId = request.getParameter("volunteerId");
        if (!Util.nullOrEmptyOrBlank(strVolunteerId)) {
            Integer volunteerId = Integer.parseInt(strVolunteerId);
            volunteerService.removeVolunteer(volunteerId);
        }
        return "redirect:/volunteer.htm";
    }

    @RequestMapping("/showVolunteerAccess")
    public String showVolunteerAccess (HttpServletRequest request, Map<String, Object> map)
    {
        String strVolunteerId = request.getParameter("volunteerId");
        if (!Util.nullOrEmptyOrBlank(strVolunteerId)) {
            Integer volunteerId = Integer.parseInt(strVolunteerId);
            Volunteer volunteer = volunteerService.getVolunteer(volunteerId);
            map.put("volunteerId", volunteer.getId());
            map.put("volunteerName", volunteer.getName());
            map.put("accessControlList", volunteerService.getAccessControlList(volunteerId));
            map.put("accessFilterList", volunteerService.getAccessFilterList(volunteerId));
            return "volunteerAccess";
        }

        return "redirect:/volunteer.htm";
    }

    @RequestMapping("/showAccessFilter")
    public String showAccessFilter (HttpServletRequest request, Map<String, Object> map)
    {
        String strVolunteerId = request.getParameter("volunteerId");
        if (!Util.nullOrEmptyOrBlank(strVolunteerId)) {
            Integer volunteerId = Integer.parseInt(strVolunteerId);
            Volunteer volunteer = volunteerService.getVolunteer(volunteerId);
            map.put("volunteerId", volunteer.getId());
            map.put("volunteerName", volunteer.getName());
            map.put("allEvents", getAllEventMap(eventService.allEvents()));
            map.put("allFoundations", allFoundations());
            map.put("accessFilterForm", new AccessFilterForm());
            return "addAccessFilter";
        }

        return "redirect:/volunteer.htm";
    }

    @RequestMapping("/showAccessControl")
    public String showAccessControl (HttpServletRequest request, Map<String, Object> map)
    {
        String strVolunteerId = request.getParameter("volunteerId");
        if (!Util.nullOrEmptyOrBlank(strVolunteerId)) {
            Integer volunteerId = Integer.parseInt(strVolunteerId);
            Volunteer volunteer = volunteerService.getVolunteer(volunteerId);
            map.put("volunteerId", volunteer.getId());
            map.put("volunteerName", volunteer.getName());
            map.put("allVolunteerPermissions", VolunteerPermission.allVolunteerPermissions());
            map.put("accessControl", new AccessControl());
            return "addAccessControl";
        }

        return "redirect:/volunteer.htm";
    }

    @RequestMapping("/addAccessFilter")
    public String addAccessFilter (@ModelAttribute("accessFilterForm") AccessFilterForm accessFilterForm,
                                   HttpServletRequest request)
    {
        Volunteer volunteer = volunteerService.getVolunteer(accessFilterForm.getVolunteerId());
        Event event = eventService.getEvent(accessFilterForm.getEventId());
        List<Integer> foundationIds = accessFilterForm.getFoundationIds();

        List<BaseForm> volunteerAccessList = new ArrayList<BaseForm>();

        if (foundationIds == null || foundationIds.isEmpty()) {
            AccessFilter accessFilter = new AccessFilter();
            accessFilter.initialize(Util.getCurrentUser().getEmail());
            accessFilter.setVolunteer(volunteer);
            accessFilter.setEvent(event);
            volunteerAccessList.add(accessFilter);
        }
        else {
            for (Integer foundationId : foundationIds) {
                PHFoundation foundation = eventService.getFoundation(foundationId);
                AccessFilter accessFilter = new AccessFilter();
                accessFilter.initialize(Util.getCurrentUser().getEmail());
                accessFilter.setVolunteer(volunteer);
                accessFilter.setEvent(event);
                accessFilter.setFoundation(foundation);
                volunteerAccessList.add(accessFilter);
            }
        }

        volunteerService.saveOrUpdate(volunteerAccessList);
        Login.initializeAccessFilterList(volunteer.getEmail());

        request.setAttribute("volunteerId", volunteer.getId());
        return "forward:/showVolunteerAccess.htm";
    }

    @RequestMapping("/addAccessControl")
    public String addAccessControl (@ModelAttribute("accessControl") AccessControl accessControl,
                                    HttpServletRequest request)
    {
        Volunteer volunteer = null;
        String strVolunteerId = request.getParameter("volunteerId");

        if (!Util.nullOrEmptyOrBlank(strVolunteerId)) {
            Integer volunteerId = Integer.parseInt(strVolunteerId);
            volunteer = volunteerService.getVolunteer(volunteerId);
        }

        String permission = accessControl.getPermission();

        accessControl.initialize(Util.getCurrentUser().getEmail());
        accessControl.setVolunteer(volunteer);
        accessControl.setPermission(permission);

        volunteerService.saveOrUpdate(accessControl);
        Login.initializeAccessControlList(volunteer.getEmail());

        request.setAttribute("volunteerId", volunteer.getId());
        return "forward:/showVolunteerAccess.htm";
    }

    @RequestMapping("/clearCache")
    public String clearCache (HttpServletRequest request)
    {
        Volunteer volunteer = null;
        String strVolunteerId = request.getParameter("volunteerId");

        if (!Util.nullOrEmptyOrBlank(strVolunteerId)) {
            Integer volunteerId = Integer.parseInt(strVolunteerId);
            volunteer = volunteerService.getVolunteer(volunteerId);
            CommonCache.getInstance().remove(Login.getSessionCacheKey(volunteer.getEmail()));
        }

        return "redirect:/volunteer.htm";
    }

    @RequestMapping("/deleteAccessFilter")
    public String deleteAccessFilter (HttpServletRequest request)
    {
        String strAccessFilterId = request.getParameter("accessFilterId");
        String strVolunteerId = request.getParameter("volunteerId");
        if (!Util.nullOrEmptyOrBlank(strAccessFilterId) &&
                !Util.nullOrEmptyOrBlank(strVolunteerId)) {
            Volunteer volunteer = volunteerService.getVolunteer(Integer.parseInt(strVolunteerId));
            Integer accessFilterId = Integer.parseInt(strAccessFilterId);
            volunteerService.delete(accessFilterId, AccessFilter.class);
            Login.initializeAccessFilterList(volunteer.getEmail());
        }
        request.setAttribute("volunteerId", Integer.parseInt(strVolunteerId));
        return "forward:/showVolunteerAccess.htm";
    }

    @RequestMapping("/deleteAccessControl")
    public String deleteAccessControl (HttpServletRequest request)
    {
        String strAccessControlId = request.getParameter("accessControlId");
        String strVolunteerId = request.getParameter("volunteerId");
        if (!Util.nullOrEmptyOrBlank(strAccessControlId) &&
                !Util.nullOrEmptyOrBlank(strVolunteerId)) {
            Volunteer volunteer = volunteerService.getVolunteer(Integer.parseInt(strVolunteerId));
            Integer accessControlId = Integer.parseInt(strAccessControlId);
            volunteerService.delete(accessControlId, AccessControl.class);
            Login.initializeAccessControlList(volunteer.getEmail());
        }
        request.setAttribute("volunteerId", Integer.parseInt(strVolunteerId));
        return "forward:/showVolunteerAccess.htm";
    }

    @RequestMapping("/index")
    public String login (Map<String, Object> map)
    {
        map.put("login", new Login());
        return "login";
    }

    @RequestMapping("/login")
    public ModelAndView processLogin (@ModelAttribute("login") Login login,
                                      BindingResult errors,
                                      HttpSession session)
    {
        ModelAndView mv = new ModelAndView("login");

        if (login == null || login.getEmail() == null) {
            return mv;
        }

        login.setSessionId(session.getId());

        int loginState = volunteerService.processLogin(login);

        if (loginState == Login.UserAlreadyLoggedIn) {
            login.setLastAccessed(new Date().getTime());
            login.setStatus(Login.UserAlreadyLoggedIn);
            session.setAttribute(Login.ClassName, login);
            mv = new ModelAndView("welcome");
            mv.addObject("user", login);
            return mv;

        }
        else if (loginState == Login.Success) {
            login.setLastAccessed(new Date().getTime());
            login.setStatus(Login.Success);
            session.setAttribute(Login.ClassName, login);
            mv = new ModelAndView("welcome");
            mv.addObject("user", login);
            return mv;
        }
        else if (loginState == Login.InvalidUsernamePassword) {
            errors.reject("login.invalidUser", "login.invalidUser");
        }
        else if (loginState == Login.UserHasNoAccess) {
            errors.reject("login.noAccess", "login.noAccess");
        }
        mv.addObject("errors", errors);
        return mv;
    }

    @RequestMapping("/logout")
    public String processlogout (HttpSession session)
    {
        Login login = (Login) session.getAttribute(Login.ClassName);
        volunteerService.processLogout(login);
        session.invalidate();
//        CommonCache.getInstance().remove(login.getSessionCacheKey());
        return "redirect:/index.htm";
    }

    @RequestMapping("/overrideLogin")
    public String overrideLogin (HttpSession session, Map<String, Object> map)
    {
        Login login = (Login) session.getAttribute(Login.ClassName);
        login.setOverrideCurrentLogin(true);

        if (login == null || login.getEmail() == null) {
            return "redirect:/index.htm";
        }

        int loginState = volunteerService.processLogin(login);

        if (loginState == Login.Success) {
            login.setLastAccessed(new Date().getTime());
            login.setStatus(Login.Success);
            login.setOverrideCurrentLogin(false);
            session.setAttribute(Login.ClassName, login);
            map.put("user", login);
            return "redirect:/welcome.htm";
        }

        return "redirect:/index.htm";
    }

    @RequestMapping("/welcome")
    public String welcome ()
    {
        return "welcome";
    }

    @RequestMapping(value = "/")
    public String index ()
    {
        return "redirect:/index.htm";
    }
}