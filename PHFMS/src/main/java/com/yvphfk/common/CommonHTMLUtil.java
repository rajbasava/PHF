/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.common;

import java.text.MessageFormat;

public class CommonHTMLUtil
{
    private static final String SelectOptionPattern = "<option value=\"{0}\"> {1} </option>";
    public static final String DefaultSelectOption = "<option value=\"-1\"> --- Select --- </option>";

    public static String htmlSelectOption (Object obj, String idPath, String valuePath)
    {

        if (obj == null) {
            return DefaultSelectOption;
        }

        Object optionId = Util.getDottedFieldValue(idPath, obj);
        Object optionValue = Util.getDottedFieldValue(valuePath, obj);

        if (optionId == null || optionValue == null) {
            return DefaultSelectOption;
        }

        return MessageFormat.format(SelectOptionPattern, String.valueOf(optionId), String.valueOf(optionValue));

    }

}
