/*
    Copyright (c) 2012-2017 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.common;

import com.yvphfk.model.Login;
import com.yvphfk.service.VolunteerService;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class UserSessionListener implements HttpSessionListener
{
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent)
    {
        HttpSession session = httpSessionEvent.getSession();
        Login login = (Login) session.getAttribute(Login.ClassName);
        VolunteerService volunteerService = (VolunteerService) Util.getBean("volunteerServiceImpl");
        volunteerService.processLogout(login);
    }
}
