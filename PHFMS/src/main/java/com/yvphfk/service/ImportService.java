/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.service;

import com.yvphfk.model.ImportFile;
import com.yvphfk.model.Login;

public interface ImportService
{
    public void processImportFile (ImportFile importFile, Login login);

}
