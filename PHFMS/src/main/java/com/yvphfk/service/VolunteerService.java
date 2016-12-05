/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.service;

import com.yvphfk.model.Login;
import com.yvphfk.model.form.AccessControl;
import com.yvphfk.model.form.Volunteer;
import com.yvphfk.model.form.AccessFilter;
import com.yvphfk.model.form.VolunteerKit;

import java.util.List;
import java.util.Map;


public interface VolunteerService extends CommonService
{
    public void addVolunteer (Volunteer volunteer);

    public void addVolunteerKit (VolunteerKit volunteerKit);

    public List<Volunteer> listVolunteer ();

    public Map<String, String> listVolunteerWithoutKits (Integer eventKitId);

    public void removeVolunteer (Integer id);

    public int processLogin (Login login);

    public boolean isValidLogin (Login login);

    public void processLogout (Login login);

    public Volunteer getVolunteer (Integer volunteerId);

    public List<AccessFilter> getAccessFilterList (Integer volunteerId);

    public List<AccessControl> getAccessControlList (Integer volunteerId);

}
