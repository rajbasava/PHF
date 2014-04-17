/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.common;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public enum AmountPaidCategory
{
    APC_1("-1", "Undefined", "{1} == 0"),
    APC0("0", "Full Payment", "{0} >= {1}"),
    APC1("1", "Zero Payment", "{0} == 0"),
    APC2("2", "Less than 1000", "{0} <= 1000"),
    APC3("3", "Between 1001 and 2000", "{0} > 1000 and {0} <= 2000"),
    APC4("4", "Between 2001 and 3000", "{0} > 2000 and {0} <= 3000"),
    APC5("5", "Between 3001 and 4000", "{0} > 3000 and {0} <= 4000"),
    APC6("6", "Between 4001 and 5000", "{0} > 4000 and {0} <= 5000"),
    APC7("7", "Between 5001 and 10000", "{0} > 5000 and {0} <= 10000"),
    APC8("8", "Above 10000", "{0} > 10000 and {0} < {1}");

    private String key;
    private String name;
    private String condition;

    private static Map<String, AmountPaidCategory> allAmountPaidCategories;

    private AmountPaidCategory (String key,
                                String name,
                                String condition)
    {
        this.key = key;
        this.name = name;
        this.condition = condition;
    }

    public String getKey ()
    {
        return key;
    }

    public String getName ()
    {
        return name;
    }

    public String getCondition ()
    {
        return condition;
    }

    static {
        if (allAmountPaidCategories == null) {
            allAmountPaidCategories = new LinkedHashMap<String, AmountPaidCategory>();

            allAmountPaidCategories.put(AmountPaidCategory.APC_1.getKey(), AmountPaidCategory.APC_1);
            allAmountPaidCategories.put(AmountPaidCategory.APC0.getKey(), AmountPaidCategory.APC0);
            allAmountPaidCategories.put(AmountPaidCategory.APC1.getKey(), AmountPaidCategory.APC1);
            allAmountPaidCategories.put(AmountPaidCategory.APC2.getKey(), AmountPaidCategory.APC2);
            allAmountPaidCategories.put(AmountPaidCategory.APC3.getKey(), AmountPaidCategory.APC3);
            allAmountPaidCategories.put(AmountPaidCategory.APC4.getKey(), AmountPaidCategory.APC4);
            allAmountPaidCategories.put(AmountPaidCategory.APC5.getKey(), AmountPaidCategory.APC5);
            allAmountPaidCategories.put(AmountPaidCategory.APC6.getKey(), AmountPaidCategory.APC6);
            allAmountPaidCategories.put(AmountPaidCategory.APC7.getKey(), AmountPaidCategory.APC7);
            allAmountPaidCategories.put(AmountPaidCategory.APC8.getKey(), AmountPaidCategory.APC8);
        }
    }

    public static Map<String, String> allAmountPaidCategories ()
    {
        Map amountPaidCategories = new LinkedHashMap<String, String>();
        Set keys = allAmountPaidCategories.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            amountPaidCategories.put(key, ((AmountPaidCategory) allAmountPaidCategories.get(key)).getName());
        }
        return amountPaidCategories;
    }

    public static String getName (String key)
    {
        return ((AmountPaidCategory) allAmountPaidCategories.get(key)).getName();
    }

    public static String getConditionTemplate (String key, boolean forSQL)
    {
        String template = ((AmountPaidCategory) allAmountPaidCategories.get(key)).getCondition();
        if (forSQL &&
                ("0".equals(key) || "1".equals(key))) {
            template = template.replaceFirst("==", "=");

        }
        return template;
    }
}
