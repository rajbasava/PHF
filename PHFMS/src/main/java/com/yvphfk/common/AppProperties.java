/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Properties;

@Component
public class AppProperties
{
    public static final String ServiceName = "appProperties";

    @Value("${mail.server.username}")
    private String fromAddress;

    @Value("${mail.maxSendLimitPerRun}")
    private int maxSendLimitPerRun;

    @Value("${mail.maxRetryLimit}")
    private int maxRetryLimit;

    @Value("${app.docRoot}")
    private String docRoot;

    @Value("${default.foundationId}")
    private Integer defaultFoundationId;

    public String getFromAddress ()
    {
        return fromAddress;
    }

    public int getMaxSendLimitPerRun ()
    {
        return maxSendLimitPerRun;
    }

    public int getMaxRetryLimit ()
    {
        return maxRetryLimit;
    }

    public String getDocRoot ()
    {
        return docRoot;
    }

    public Integer getDefaultFoundationId ()
    {
        return defaultFoundationId;
    }
}
