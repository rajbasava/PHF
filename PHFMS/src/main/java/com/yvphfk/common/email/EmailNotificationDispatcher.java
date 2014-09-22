/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.common.email;

import com.google.common.util.concurrent.RateLimiter;
import com.yvphfk.common.AppProperties;
import com.yvphfk.common.AutoWiringQuartzJobBean;
import com.yvphfk.common.Log;
import com.yvphfk.model.form.MailNotification;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.List;

@Component
public class EmailNotificationDispatcher extends AutoWiringQuartzJobBean
{

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private EmailService emailService;

    public static final int CommitSize = 50;

    @Override
    protected void run (JobExecutionContext jobExecutionContext) throws JobExecutionException
    {
        processNotifications();
    }

    private List getNotifications ()
    {
        Log.event.warn("search of events called");

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(MailNotification.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.addOrder(Order.asc("timeCreated"));
        criteria.setMaxResults(appProperties.getMaxSendLimitPerRun());

        List<MailNotification> results = criteria.list();

        session.close();
        return results;
    }

    private void processNotifications ()
    {
        List<MailNotification> notifications = getNotifications();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        RateLimiter rateLimiter = RateLimiter.create(3);

        int count = 0;
        for (MailNotification notification : notifications) {
            rateLimiter.acquire();
            boolean result = sendNotification(notification);

            if (result ||
                    notification.getRetry() == appProperties.getMaxRetryLimit()) {
                session.delete(notification);
            }
            else {
                session.save(notification);
            }

            count++;

            if (count % CommitSize == 0) {
                session.flush();
                session.clear();
            }
        }

        transaction.commit();
        session.flush();
        session.close();
    }

    private boolean sendNotification (MailNotification notification)
    {
        EmailTemplate template =
                EmailUtil.getTemplate(notification.getTemplateName());

        List<Image> images = template.getImage();

        try {
            emailService.sendMail(
                    notification.getToAddress(),
                    notification.getFromAddress(),
                    notification.getSubject(),
                    notification.getMessage(),
                    images);
        }
        catch (MessagingException e) {
            Log.email.error("Exception while sending the mail id " + notification.getId(), e);
            processNotificationErrors(notification, e);
            return false;
        }
        return true;
    }

    private void processNotificationErrors (MailNotification notification, Exception ex)
    {
        if (ex != null) {
            notification.setRetry(notification.getRetry() + 1);
        }
    }
}
