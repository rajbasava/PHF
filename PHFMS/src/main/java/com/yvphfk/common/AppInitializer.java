/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.common;

import com.yvphfk.common.email.EmailTemplate;
import com.yvphfk.common.email.EmailTemplateCache;
import com.yvphfk.common.email.EmailTemplates;
import com.yvphfk.common.email.Image;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@Component
public class AppInitializer
{
    public void initialize () throws Exception
    {
        initializeEmailTemplates();
        initializeCache();
    }

    private void initializeEmailTemplates ()
    {
        EmailTemplateCache.getInstance();
    }
    private void initializeCache()
    {
        CommonCache.getInstance();
    }
}
