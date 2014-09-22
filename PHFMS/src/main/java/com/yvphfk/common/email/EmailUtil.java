/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.common.email;

import com.yvphfk.common.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmailUtil
{
    public static EmailTemplate getTemplate (String templateName)
    {
        EmailTemplate template =
                EmailTemplateCache.getInstance().getTemplate(templateName);
        return template;
    }

}
