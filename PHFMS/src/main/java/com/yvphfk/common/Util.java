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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.StringTokenizer;

public class Util
{

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
        return (Login) session.getAttribute(Login.ClassName);
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
}