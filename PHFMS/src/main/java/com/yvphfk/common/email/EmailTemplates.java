/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.common.email;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class EmailTemplates implements Serializable
{
    private List<EmailTemplate> template;

    public List<EmailTemplate> getTemplate ()
    {
        return template;
    }

    public void setTemplate (List<EmailTemplate> template)
    {
        this.template = template;
    }

    @Override
    public String toString ()
    {
        return "EmailTemplates{" +
                "template=" + template +
                '}';
    }
}
