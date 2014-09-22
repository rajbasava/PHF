/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.common.email;

import java.io.Serializable;
import java.util.List;

public class EmailTemplate implements Serializable
{

    private String name;
    private String bodyFile;
    private String subject;
    private List<Image> image;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getBodyFile ()
    {
        return bodyFile;
    }

    public void setBodyFile (String bodyFile)
    {
        this.bodyFile = bodyFile;
    }

    public String getSubject ()
    {
        return subject;
    }

    public void setSubject (String subject)
    {
        this.subject = subject;
    }

    public List<Image> getImage ()
    {
        return image;
    }

    public void setImage (List<Image> image)
    {
        this.image = image;
    }
}
