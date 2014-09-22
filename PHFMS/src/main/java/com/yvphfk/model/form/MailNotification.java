/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model.form;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name = "phk_mail")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class MailNotification extends BaseForm
{
    public static final String ClassName = "com.yvphfk.model.form.MailNotification";

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @Column(name = "message")
    private String message;

    @Column(name = "subject")
    private String subject;

    @Column(name = "toaddr")
    private String toAddress;

    @Column(name = "fromaddr")
    private String fromAddress;

    @Column(name = "templateName")
    private String templateName;

    @Column(name = "retry")
    private int retry;

    @Column(name = "PREPAREDBY", updatable = false)
    private String preparedBy;

    @Column(name = "TIMECREATED", updatable = false)
    private Date timeCreated;

    @Column(name = "TIMEUPDATED")
    private Date timeUpdated;

    @Column(name = "ACTIVE")
    private boolean active;


    @Override
    public Integer getId ()
    {
        return id;
    }

    @Override
    public String getType ()
    {
        return ClassName;
    }

    public void setId (Integer id)
    {
        this.id = id;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getSubject ()
    {
        return subject;
    }

    public void setSubject (String subject)
    {
        this.subject = subject;
    }

    public String getToAddress ()
    {
        return toAddress;
    }

    public void setToAddress (String toAddress)
    {
        this.toAddress = toAddress;
    }

    public String getFromAddress ()
    {
        return fromAddress;
    }

    public void setFromAddress (String fromAddress)
    {
        this.fromAddress = fromAddress;
    }

    public String getTemplateName ()
    {
        return templateName;
    }

    public void setTemplateName (String templateName)
    {
        this.templateName = templateName;
    }

    public int getRetry ()
    {
        return retry;
    }

    public void setRetry (int retry)
    {
        this.retry = retry;
    }

    public String getPreparedBy ()
    {
        return preparedBy;
    }

    public void setPreparedBy (String preparedBy)
    {
        this.preparedBy = preparedBy;
    }

    public Date getTimeCreated ()
    {
        return timeCreated;
    }

    public void setTimeCreated (Date timeCreated)
    {
        this.timeCreated = timeCreated;
    }

    public Date getTimeUpdated ()
    {
        return timeUpdated;
    }

    public void setTimeUpdated (Date timeUpdated)
    {
        this.timeUpdated = timeUpdated;
    }

    public boolean isActive ()
    {
        return active;
    }

    public void setActive (boolean active)
    {
        this.active = active;
    }
}