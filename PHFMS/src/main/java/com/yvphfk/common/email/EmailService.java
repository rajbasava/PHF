/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.common.email;

import com.yvphfk.common.AppProperties;
import com.yvphfk.common.ApplicationContextUtils;
import com.yvphfk.common.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
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
    private AppProperties appProperties;


    public void sendMail (String recipientEmail,
                          String fromEmail,
                          String subject,
                          String msgBody)
            throws MessagingException
    {
        sendMail(recipientEmail, fromEmail, subject, msgBody, null, null, null);
    }

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
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
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
                          String msgBody,
                          List<Image> images)
            throws MessagingException
    {
        ApplicationContext appContext = ApplicationContextUtils.getApplicationContext();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setSubject(subject);
        message.setFrom(fromEmail);
        message.setTo(recipientEmail);
        message.setText(msgBody, true);

        for (Image image : images) {
            Resource resource = appContext.getResource("file:"+appProperties.getDocRoot() +"/" + image.getFileName());
            try {
                message.addInline(image.getFileName(),resource.getFile());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Send email
        mailSender.send(mimeMessage);
    }

    public String getMessageContent (Map parameters)
    {

        String templateName = (String) parameters.get(NotificationManager.TemplateName);
        EmailTemplate template = EmailUtil.getTemplate(templateName);

        Context ctx = new Context();
        Set set = parameters.keySet();

        Iterator itr = set.iterator();
        while (itr.hasNext()) {
            String key = (String) itr.next();
            Object objValue = parameters.get(key);
            ctx.setVariable(key, objValue);
        }

        String htmlContent = templateEngine.process(template.getBodyFile(), ctx);

        return htmlContent;
    }
}
