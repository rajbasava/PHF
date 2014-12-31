/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.common;

import com.yvphfk.model.Importable;

public interface ImportableProcessor
{
    public void preLoad (Importable importable);

    public void postLoad (Importable importable);
}
