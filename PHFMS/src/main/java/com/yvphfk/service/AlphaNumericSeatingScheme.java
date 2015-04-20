/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.service;

import com.yvphfk.model.form.Event;
import com.yvphfk.model.form.EventRegistration;

import java.util.List;

public interface AlphaNumericSeatingScheme
{
    public List<EventRegistration> fetchList (Event event);
}
