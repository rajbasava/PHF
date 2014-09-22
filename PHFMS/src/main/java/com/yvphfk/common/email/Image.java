/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.common.email;

public class Image
{
    private String fileName;
    private String contentType;

    public Image ()
    {

    }

    public Image (String fileName, String contentType)
    {
        this.fileName = fileName;
        this.contentType = contentType;
    }


    public String getFileName ()
    {
        return fileName;
    }

    public void setFileName (String fileName)
    {
        this.fileName = fileName;
    }

    public String getContentType ()
    {
        return contentType;
    }

    public void setContentType (String contentType)
    {
        this.contentType = contentType;
    }
}
