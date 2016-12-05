/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.common;

import com.yvphfk.model.form.BaseForm;
import com.yvphfk.model.Login;
import ognl.Ognl;
import ognl.OgnlException;
import org.apache.poi.ss.usermodel.Workbook;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class Util
{
    public static final String DefaultDatePattern =  "dd/MM/yy";

    public static final int MaxResultCount = 500;

    public static boolean nullOrEmptyOrBlank (String toValidate)
    {
        if (toValidate == null || toValidate.equalsIgnoreCase("")) {
            return true;
        }

        return false;
    }

    public static Method getDeclaredSetter (Class clazz, String fieldName, Class fieldType)
    {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod("set" + fieldName, fieldType);
        } catch (NoSuchMethodException e) {
            return null;
        }
        return method;

    }

    public static Object getFieldValue (Object obj, String fieldName)
    {
        if (obj == null || Util.nullOrEmptyOrBlank(fieldName)) {
            return null;
        }

        Object result = null;

        try {
            Method method = obj.getClass().getDeclaredMethod("get" + fieldName);
            if (method != null) {
                result = method.invoke(obj);
            }
        } catch (NoSuchMethodException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
        return result;
    }

    public static void write (HttpServletResponse response, Workbook workbook)
    {

        try {
            // Retrieve the output stream
            ServletOutputStream outputStream = response.getOutputStream();
            // Write to the output stream
            workbook.write(outputStream);
            // Flush the stream
            outputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean evaluate (String expression, BaseForm form)
    {
        boolean result = false;
        try {
            Object obj = Ognl.getValue(expression, form);
            if (obj instanceof Boolean) {
                result = ((Boolean) obj).booleanValue();
            }
        } catch (OgnlException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Object getDottedFieldValue (String dottedFieldPath, Object obj)
    {
        Object result = null;
        try {
            result = Ognl.getValue(dottedFieldPath, obj);
        } catch (OgnlException e) {
            e.printStackTrace();
        }
        return result;
    }

    // set only values for one level participant.name
    public static Object setDottedFieldValue (String dottedFieldPath,
                                              Object obj,
                                              Object value)
    {
        return setDottedFieldValue(dottedFieldPath, obj, value, true);
    }

    public static Object setDottedFieldValue (String dottedFieldPath,
                                              Object obj,
                                              Object value,
                                              boolean create)
    {
        Object result = null;
        try {
            if (create && dottedFieldPath.contains(".")) {
                StringTokenizer tokenizer = new StringTokenizer(dottedFieldPath, ".");
                int tokenCount = tokenizer.countTokens();

                Object tmp = null;
                int i = 0;
                while (tokenizer.hasMoreTokens() && i < tokenCount - 1) {
                    String path = tokenizer.nextToken();
                    tmp = Ognl.getValue(path, obj);
                    if (tmp == null) {
                        Method method = obj.getClass().getDeclaredMethod("get" + getCapitalizedFieldName(path));
                        Class clazz = method.getReturnType();
                        Ognl.setValue(path, obj, createInstance(clazz.getName()));
                    }
                    i++;
                }
            }

            Ognl.setValue(dottedFieldPath, obj, value);
        } catch (OgnlException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getCapitalizedFieldName (String dottedFieldPath)
    {
        if (Util.nullOrEmptyOrBlank(dottedFieldPath)) {
            return null;
        }

        String lastField = dottedFieldPath.substring(dottedFieldPath.lastIndexOf(".") + 1);
        lastField = lastField.substring(0, 1).toUpperCase() + lastField.substring(1);
        return lastField;
    }

    public static Class loadClass (String classType)
    {
        Class clazz = null;
        try {
            clazz = Class.forName(classType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;

    }

    public static Login getCurrentUser ()
    {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = requestAttributes.getRequest().getSession();
        Login login = (Login) session.getAttribute(Login.ClassName);
//        login = (Login) CommonCache.getInstance().get(login.getSessionCacheKey());
        return login;
    }

    public static Object createInstance (String className)
    {
        Class clazz = loadClass(className);
        Object obj = null;

        try {
            obj = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return obj;
    }

    public static Date add (Date d, int dateField, int count)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);
        calendar.setTime(d);
        calendar.add(dateField, count);
        return calendar.getTime();
    }

    public static Date getDateWithoutTime (Date d)
    {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);
        String strDate = format.format(d);
        Date result = null;

        try {
            result = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static Date parseDate (String dateValue, String pattern)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);

        Date result = null;
        try {
            result = formatter.parse(dateValue);
        }
        catch (ParseException e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    public static boolean isQuartzJobRunning (String name, Map dataMap) throws SchedulerException
    {
        if (Util.nullOrEmptyOrBlank(name)) {
            return false;
        }

        ApplicationContext context = ApplicationContextUtils.getApplicationContext();
        Scheduler scheduler = (Scheduler) context.getBean("scheduler");

        List<JobExecutionContext> currentJobs = scheduler.getCurrentlyExecutingJobs();
        for (JobExecutionContext currentJob: currentJobs) {
            JobDetail jobDetail = currentJob.getJobDetail();
            String fullName = generateJobName(name, dataMap);

            if (fullName.equals(jobDetail.getName())) {
                return true;
            }
        }

        return false;

    }

    public static void executeQJobImmediately (String name, Map dataMap) throws SchedulerException
    {
        if (Util.nullOrEmptyOrBlank(name)) {
            return;
        }

        if (isQuartzJobRunning(name, dataMap)) {
            return;
        }

        ApplicationContext context = ApplicationContextUtils.getApplicationContext();
        Scheduler scheduler = (Scheduler) context.getBean("scheduler");

        QuartzJobBean quartzJobBean = (QuartzJobBean) context.getBean(name);
        Trigger trigger = TriggerUtils.makeImmediateTrigger(generateJobName(name, dataMap), 0, 0);

        JobDetailBean jobDetailBean = new JobDetailBean();
        jobDetailBean.setGroup("DEFAULT");
        jobDetailBean.setName(trigger.getName());
        jobDetailBean.setJobClass(quartzJobBean.getClass());
        jobDetailBean.setJobDataMap(new JobDataMap(dataMap));
        jobDetailBean.setVolatility(false);

        scheduler.scheduleJob(jobDetailBean, trigger);
        scheduler.startDelayed(1);
    }

    private static String generateJobName(String name, Map dataMap)
    {
        StringBuilder builder = new StringBuilder();
        builder.append(name);

        Set keys = dataMap.keySet();
        Iterator itr = keys.iterator();
        while(itr.hasNext()) {
            String key = (String) itr.next();
            String value = String.valueOf(dataMap.get(key));
            builder.append("#");
            builder.append(key+":"+value);
        }

        return builder.toString();
    }

    public static Object getBean (String beanName)
    {
        ApplicationContext context = ApplicationContextUtils.getApplicationContext();

        if (context == null) {
            return null;
        }

        return context.getBean(beanName);
    }
}