/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.common.email;

import com.yvphfk.common.AutoWiringQuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ThankQNotifier extends RegisteredParticipantNotifier
{
    @Override
    protected Map getNotificationParameters ()
    {
        HashMap map = new HashMap();
        map.put(NotificationManager.TemplateName, "ThankQ");
        return  map;
    }
}
