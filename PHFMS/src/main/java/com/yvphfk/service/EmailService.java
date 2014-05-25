/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.service;

import com.yvphfk.common.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Service
public class EmailService
{
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private HttpServletRequest request;

    /*
     * Send HTML mail (simple)
     */
    public void sendMail (String recipientEmail,
                          String fromEmail,
                          String subject,
                          String msgBody,
                          byte[] imageBytes,
                          String imageName,
                          String imageContentType)
            throws MessagingException
    {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject(subject);
        message.setFrom(fromEmail);
        message.setTo(recipientEmail);
        message.setText(msgBody, true);

        if (imageBytes != null && !Util.nullOrEmptyOrBlank(imageContentType)) {
            // Add the inline image, referenced from the HTML code as "cid:${imageName}"
            final InputStreamSource imageSource = new ByteArrayResource(imageBytes);
            message.addInline(imageName, imageSource, imageContentType);
        }

        // Send email
        mailSender.send(mimeMessage);

    }

    public void sendMail (String recipientEmail,
                          String fromEmail,
                          String subject,
                          String msgBody)
            throws MessagingException
    {
        sendMail(recipientEmail, fromEmail, subject, msgBody, null, null, null);
    }

    public String getMessageContent (String templateName,
                                     Locale locale,
                                     Map parameters)
    {
        WebContext ctx = new WebContext(request, null,
                request.getServletContext(), Locale.ENGLISH);

        Set set = parameters.keySet();

        Iterator itr = set.iterator();
        while (itr.hasNext()) {
            String key = (String) itr.next();
            Object objValue = parameters.get(key);
            ctx.setVariable(key, objValue);
        }

        String htmlContent = templateEngine.process(templateName, ctx);

        return htmlContent;
    }
}
