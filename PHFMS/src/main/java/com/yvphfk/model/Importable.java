/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model;

import com.yvphfk.model.form.Event;

public interface Importable
{
    public void initializeForImport (String email);

    public void applyEvent (Event event);

}
