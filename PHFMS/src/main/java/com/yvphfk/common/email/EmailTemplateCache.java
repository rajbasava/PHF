package com.yvphfk.common.email;/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

import com.yvphfk.common.Log;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.core.io.ClassPathResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class EmailTemplateCache
{
    private static EmailTemplateCache ourInstance = new EmailTemplateCache();

    private Map<String, EmailTemplate> cache = new HashedMap();

    public static EmailTemplateCache getInstance ()
    {
        return ourInstance;
    }

    private EmailTemplateCache ()
    {
        try {
            ClassPathResource classPathResource = new ClassPathResource("emailTemplates.xml");
            InputStream is = classPathResource.getInputStream();

            JAXBContext jaxbContext = JAXBContext.newInstance(com.yvphfk.common.email.EmailTemplates.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            EmailTemplates emailTemplates = (EmailTemplates) jaxbUnmarshaller.unmarshal(is);

            List<EmailTemplate> templates = emailTemplates.getTemplate();
            for (EmailTemplate template: templates) {
                cache.put(template.getName(), template);
            }
        }
        catch (IOException ex) {
            Log.email.error("Exception loading the tamplates config", ex);
        }
        catch (JAXBException ex) {
            Log.email.error("Exception loading the tamplates config", ex);
        }
    }

    public EmailTemplate getTemplate (String name)
    {
        return cache.get(name);
    }
}
