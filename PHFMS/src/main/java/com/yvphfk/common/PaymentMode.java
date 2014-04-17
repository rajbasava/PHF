/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.common;

import java.util.LinkedHashMap;
import java.util.Map;

public enum PaymentMode
{
    Cash("cash", "Cash"),
    Cheque("cheque", "Cheque"),
    CreditCard("creditcard", "Credit Card"),
    DebitCard("debitcard", "Debit Card"),
    Online("online", "Online"),
    Journal("journal", "Journal");

    private String key;
    private String name;

    private static Map<String, String> allPaymentModes;

    private PaymentMode (String key, String name)
    {
        this.key = key;
        this.name = name;
    }

    public String getKey ()
    {
        return key;
    }

    public String getName ()
    {
        return name;
    }

    public static Map<String, String> allPaymentModes ()
    {
        if (allPaymentModes == null) {
            allPaymentModes = new LinkedHashMap<String, String>();

            allPaymentModes.put(PaymentMode.Cash.getKey(),
                    PaymentMode.Cash.getName());
            allPaymentModes.put(PaymentMode.Cheque.getKey(),
                    PaymentMode.Cheque.getName());
            allPaymentModes.put(PaymentMode.CreditCard.getKey(),
                    PaymentMode.CreditCard.getName());
            allPaymentModes.put(PaymentMode.DebitCard.getKey(),
                    PaymentMode.DebitCard.getName());
            allPaymentModes.put(PaymentMode.Online.getKey(),
                    PaymentMode.Online.getName());
            allPaymentModes.put(PaymentMode.Journal.getKey(),
                    PaymentMode.Journal.getName());
        }

        return allPaymentModes;
    }

    public static String getName (String key)
    {
        return allPaymentModes().get(key);
    }
}
