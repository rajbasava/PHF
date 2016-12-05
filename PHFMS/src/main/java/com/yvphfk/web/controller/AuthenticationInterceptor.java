/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.web.controller;

import com.yvphfk.common.CommonCache;
import com.yvphfk.common.Util;
import com.yvphfk.model.Login;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter
{
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception
    {
        Login userData = (Login) request.getSession().getAttribute(Login.class.getName());

        if (userData != null) {
            if (Util.nullOrEmptyOrBlank(userData.getEmail())) {
                response.sendRedirect("index.htm");
            }
            else if (!request.getRequestURI().endsWith("overrideLogin.htm") &&
                    (userData.hasNoAccess() || !Login.isValidCacheEntry(userData))) {

                request.getSession().invalidate();
//                CommonCache.getInstance().remove(userData.getSessionCacheKey());
                response.sendRedirect("index.htm");
                return false;
            }
            else {
                userData.setLastAccessed(new Date().getTime());

//                if (CommonCache.getInstance().get(userData.getSessionCacheKey()) == null) {
                Login.initializeAccessControlList(userData);
                Login.initializeAccessFilterList(userData);
//                    CommonCache.getInstance().put(userData.getSessionCacheKey(), userData);
//                }

                if (isPathsToIgnore(request)) {
                    RequestDispatcher rd = request.getRequestDispatcher("welcome.htm");
                    rd.forward(request, response);
                    return false;
                }
            }
        }
        else {
            if (isPathsToIgnore(request)) {
                return true;
            }
            else {
                RequestDispatcher rd = request.getRequestDispatcher("index.htm");
                rd.forward(request, response);
                return false;
            }
        }

        return true;
    }

    private boolean isPathsToIgnore (HttpServletRequest request)
    {
        String uri = request.getRequestURI();
        if (uri.endsWith("index.htm") ||
                uri.endsWith("login.htm")) {
            return true;
        }

        if (uri.substring(request.getContextPath().length(), uri.length()).equalsIgnoreCase("/")) {
            return true;
        }

        return false;
    }
}
