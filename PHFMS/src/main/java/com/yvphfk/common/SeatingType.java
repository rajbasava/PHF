/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.common;

import org.springframework.context.ApplicationContext;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public enum SeatingType
{
    Numerical("0", "Numerical - 1,2, ...", "numericSeating"),
    AlphaNumerical("1", "AlphaNumerical - A1, A2, ...", "alphaNumericSeating");

    private String key;
    private String name;
    private String clazz;

    private static Map<String, SeatingType> allSeatingTypes;

    private SeatingType (String key, String name, String clazz)
    {
        this.key = key;
        this.name = name;
        this.clazz = clazz;
    }

    public String getKey ()
    {
        return key;
    }

    public String getName ()
    {
        return name;
    }

    public String getClazz ()
    {
        return clazz;
    }

    static {
        if (allSeatingTypes == null) {
            allSeatingTypes = new LinkedHashMap<String, SeatingType>();

            allSeatingTypes.put(SeatingType.Numerical.getKey(), SeatingType.Numerical);
            allSeatingTypes.put(SeatingType.AlphaNumerical.getKey(), SeatingType.AlphaNumerical);
        }
    }

    public static Map<String, String> allSeatingTypes ()
    {
        Map seatingTypes = new LinkedHashMap<String, String>();
        Set keys = allSeatingTypes.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            seatingTypes.put(key, ((SeatingType) allSeatingTypes.get(key)).getName());
        }
        return seatingTypes;
    }

    public static String getName (String key)
    {
        return (allSeatingTypes.get(key)).getName();
    }

    public static Object createService (String key)
    {
        String serviceClassName = (allSeatingTypes.get(key)).getClazz();
        ApplicationContext context = ApplicationContextUtils.getApplicationContext();
        return context.getBean(serviceClassName);
    }

}
