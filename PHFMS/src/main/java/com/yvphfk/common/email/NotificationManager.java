package com.yvphfk.common.email;/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

import com.yvphfk.common.Log;
import com.yvphfk.common.Util;
import org.quartz.SchedulerException;
import org.springframework.util.Assert;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.fragment.ElementAndAttributeNameFragmentSpec;
import org.thymeleaf.fragment.IFragmentSpec;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class NotificationManager implements NotificationConstants
{

    private static NotificationManager ourInstance = new NotificationManager();

    public static NotificationManager get ()
    {
        return ourInstance;
    }

    private NotificationManager ()
    {
    }

    public void notify (Map parameters)
    {
        Assert.isTrue(parameters.size() > 1, "Please specify notification event");

        Assert.isTrue(parameters.get(NotificationJob) != null, "Please specify the notification job name");

        String jobName = (String) parameters.get(NotificationJob);

        try{
            Util.executeQJobImmediately(jobName, parameters);
        }
        catch (SchedulerException e) {
            String msg = "Exception during executing the job with parameters "+ parameters;
            Log.email.error(msg, e);
        }
    }

}
