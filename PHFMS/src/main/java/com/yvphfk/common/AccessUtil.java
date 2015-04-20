/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.common;

import com.yvphfk.model.Login;
import com.yvphfk.model.form.AccessFilter;
import com.yvphfk.model.form.Event;
import com.yvphfk.model.form.PHFoundation;

import java.util.ArrayList;
import java.util.List;

public class AccessUtil
{
    public static List<Integer> getEventFilterList ()
    {
        Login login = Util.getCurrentUser();
        List<Integer> eventIds = new ArrayList<Integer>();
        List<AccessFilter> accessFilterList = login.getAccessFilterList();
        for (AccessFilter ac : accessFilterList) {
            Event tmp = ac.getEvent();
            if (tmp != null) {
                eventIds.add(tmp.getId());
            }
        }
        return eventIds;
    }

    public static List<Integer> getFoundationIdFilterList ()
    {
        Login login = Util.getCurrentUser();
        List<Integer> foundationIds = new ArrayList<Integer>();
        List<AccessFilter> accessFilterList = login.getAccessFilterList();
        for (AccessFilter ac : accessFilterList) {
            PHFoundation tmp = ac.getFoundation();
            if (tmp != null) {
                foundationIds.add(tmp.getId());
            }
        }
        return foundationIds;
    }
}
