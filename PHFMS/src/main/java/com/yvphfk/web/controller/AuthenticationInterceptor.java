/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.web.controller;

import com.yvphfk.common.Util;
import com.yvphfk.model.Login;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter
{
    @Override
    public boolean preHandle (HttpServletRequest request,
                              HttpServletResponse response,
                              Object handler) throws Exception
    {
        String uri = request.getRequestURI();
        if (!uri.endsWith("index.htm") &&
                !uri.endsWith("login.htm") &&
                !uri.endsWith("logout.htm")) {
            Login userData = (Login) request.getSession().getAttribute(Login.class.getName());
            if (userData == null ||
                    Util.nullOrEmptyOrBlank(userData.getEmail()) ||
                    (new Date().getTime() - userData.getLastAccessed()) > 15 * 60 * 1000) {
                response.sendRedirect("index.htm");
                return false;
            }
            else {
                userData.setLastAccessed(new Date().getTime());
            }
        }
        return true;
    }
}
