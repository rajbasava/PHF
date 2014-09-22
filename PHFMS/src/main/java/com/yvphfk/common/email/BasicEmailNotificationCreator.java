/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.common.email;

import com.yvphfk.common.AppProperties;
import com.yvphfk.common.ApplicationContextUtils;
import com.yvphfk.common.AutoWiringQuartzJobBean;
import com.yvphfk.common.Util;
import com.yvphfk.model.form.BaseForm;
import com.yvphfk.model.form.MailNotification;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;
import java.util.Map;

public abstract class BasicEmailNotificationCreator extends AutoWiringQuartzJobBean implements NotificationConstants
{

    private static final int CommitSize = 25;

    protected abstract Map getNotificationParameters ();

    protected abstract List getParticipantListToNotify (Map parameters);

    protected abstract String getToAddress (Object valueObject);

    @Override
    protected final void run (JobExecutionContext jobExecutionContext) throws JobExecutionException
    {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        Map parameters = jobDataMap.getWrappedMap();

        List objects = getParticipantListToNotify(parameters);

        Map map = getNotificationParameters();
        if (map != null) {
            parameters.putAll(map);
        }

        processNotifications(objects, parameters);
    }

    public void processNotifications (List objects, Map parameters)
    {
        SessionFactory sessionFactory =
                (SessionFactory) ApplicationContextUtils.getApplicationContext().
                        getBean("sessionFactory");

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        AppProperties appProperties =
                (AppProperties) ApplicationContextUtils.getApplicationContext().
                        getBean(AppProperties.ServiceName);

        parameters.put(NotificationConstants.From,appProperties.getFromAddress());


        for (int i = 0; i < objects.size(); i++) {
            Object obj = objects.get(i);

            parameters.put(NotificationConstants.ValueSource, obj);

            String toAddress = getToAddress(obj);
            if (Util.nullOrEmptyOrBlank(toAddress)) {
                continue;
            }

            parameters.put(NotificationConstants.To, toAddress);

            createMessage(session, parameters);

            if (i % CommitSize == 0) {
                session.flush();
                session.clear();
            }
        }

        transaction.commit();
        session.flush();
        session.close();
    }

    protected void createMessage (Session session, Map parameters)
    {
        MailNotification notification = new MailNotification();

        notification.initialize("system");

        String templateName = (String) parameters.get(NotificationManager.TemplateName);
        EmailTemplate template = EmailUtil.getTemplate(templateName);

        String toAddress = (String) parameters.get(NotificationConstants.To);
        notification.setToAddress(toAddress);

        String fromAddress = (String) parameters.get(NotificationConstants.From);
        notification.setFromAddress(fromAddress);

        notification.setSubject(template.getSubject());

        EmailService emailService =
                (EmailService) ApplicationContextUtils.getApplicationContext().
                        getBean(NotificationConstants.EmailService);
        String message = emailService.getMessageContent(parameters);
        notification.setMessage(message);

        notification.setTemplateName(template.getName());

        session.save(notification);
    }
}
