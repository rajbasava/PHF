/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.common;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public abstract class AutoWiringQuartzJobBean extends QuartzJobBean
{

    @Override
    protected final void executeInternal (JobExecutionContext jobExecutionContext) throws
                                                                                   JobExecutionException
    {
        ApplicationContext context = ApplicationContextUtils.getApplicationContext();
        AutowireCapableBeanFactory beanFactory = context.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);

        run(jobExecutionContext);
    }

    protected abstract void run (JobExecutionContext jobExecutionContext) throws
                                                                          JobExecutionException;
}
