/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextUtils implements ApplicationContextAware
{

    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext (ApplicationContext appContext)
            throws BeansException
    {
        ctx = appContext;

    }

    public static ApplicationContext getApplicationContext ()
    {
        return ctx;
    }
}