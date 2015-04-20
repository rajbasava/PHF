/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.service;

import com.yvphfk.common.AppProperties;
import com.yvphfk.common.Util;

public class AlphaNumericSeatingSchemeFactory
{

    private AlphaNumericSeatingSchemeFactory ()
    {
    }


    public static AlphaNumericSeatingScheme getInstance ()
    {
        AppProperties appProperties = (AppProperties) Util.getBean("appProperties");
        String alphaNumericScheme = appProperties.getAlphaNumericScheme();

        if (Util.nullOrEmptyOrBlank(alphaNumericScheme)) {
            alphaNumericScheme = "nonIndiansFirstAlphaNumericSeatingScheme";
        }

        AlphaNumericSeatingScheme alphaNumericSeatingScheme =
                (AlphaNumericSeatingScheme) Util.getBean(alphaNumericScheme);
        return alphaNumericSeatingScheme;
    }

}
