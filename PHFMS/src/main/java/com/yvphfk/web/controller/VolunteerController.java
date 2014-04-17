/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.web.controller;

import com.yvphfk.common.Util;
import com.yvphfk.common.VolunteerPermission;
import com.yvphfk.model.Login;
import com.yvphfk.model.form.Volunteer;
import com.yvphfk.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

@Controller
@SessionAttributes({"user"})
public class VolunteerController
{
    @Autowired
    private VolunteerService volunteerService;

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

    @RequestMapping("/index")
    public String login (Map<String, Object> map)
    {
        map.put("login", new Login());
        return "login";
    }

    @RequestMapping("/login")
    public String processlogin (ModelMap map, @ModelAttribute("login") Login login,
                                HttpSession session)
    {
        boolean isValid = volunteerService.processLogin(login);
        map.addAttribute("user", login);
        if (isValid) {
            login.setLastAccessed(new Date().getTime());
            session.setAttribute(Login.ClassName, login);
            return "welcome";
        }
        else {
            return "redirect:/index.htm";
        }
    }

    @RequestMapping("/logout")
    public String processlogout (HttpSession session)
    {
        Login login = (Login) session.getAttribute(Login.ClassName);
        volunteerService.processLogout(login);
        session.invalidate();
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