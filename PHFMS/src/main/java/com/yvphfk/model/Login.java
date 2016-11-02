/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.model;

import com.yvphfk.common.CommonCache;
import com.yvphfk.common.Util;
import com.yvphfk.common.VolunteerPermission;
import com.yvphfk.common.helper.Access;
import com.yvphfk.model.form.AccessControl;
import com.yvphfk.model.form.AccessFilter;
import com.yvphfk.service.VolunteerService;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Login implements Serializable
{
    public static final String ClassName = "com.yvphfk.model.Login";
    public static final int Success = 1;
    public static final int InvalidUsernamePassword = 2;
    public static final int UserHasNoAccess = 3;

    private String email;
    private String name;
    private String password;
    private String counter;
    private Integer volunteerId;
    private List<AccessControl> accessControlList;
    private List<AccessFilter> accessFilterList;
    private long lastAccessed;
    private Access access = new Access();

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getCounter ()
    {
        return counter;
    }

    public void setCounter (String counter)
    {
        this.counter = counter;
    }

    /**
     set user permission which can be accessed in jstl tags in the views
     */
    public void setPermission ()
    {
        this.access.setAdmin(hasAccess(VolunteerPermission.Admin.getKey()));
        this.access.setSpotRegVolunteer(hasAccess(VolunteerPermission.SptRegVol.getKey()));
        this.access.setRegVolunteer(hasAccess(VolunteerPermission.RegVol.getKey()));
        this.access.setInfoVolunteer(hasAccess(VolunteerPermission.InfVol.getKey()));
    }

    public Integer getVolunteerId ()
    {
        return volunteerId;
    }

    public void setVolunteerId (Integer volunteerId)
    {
        this.volunteerId = volunteerId;
    }

    public long getLastAccessed ()
    {
        return lastAccessed;
    }

    public void setLastAccessed (long lastAccessed)
    {
        this.lastAccessed = lastAccessed;
    }

    public Access getAccess ()
    {
        return access;
    }

    public void setAccess (Access access)
    {
        this.access = access;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public List<AccessControl> getAccessControlList ()
    {
        return accessControlList;
    }

    public void setAccessControlList (List<AccessControl> accessControlList)
    {
        this.accessControlList = accessControlList;
        setPermission();
    }

    public List<AccessFilter> getAccessFilterList ()
    {
        return accessFilterList;
    }

    public void setAccessFilterList (List<AccessFilter> accessFilterList)
    {

        this.accessFilterList = accessFilterList;
    }

    public boolean canCheckAccess ()
    {
        return getAccessControlList() == null || getAccessFilterList().isEmpty();
    }

    public boolean hasAccess (String permission)
    {
        List<AccessControl> aclist = getAccessControlList();

        if (aclist == null) {
            return false;
        }

        for(AccessControl ac : aclist) {
            if (permission.equalsIgnoreCase(ac.getPermission())) {
                return true;
            }
        }

        return false;
    }

    public boolean hasNoAccess ()
    {
        return accessControlList == null ||
                accessControlList.isEmpty() ||
                hasAccess(VolunteerPermission.NoAccess.getKey());
    }

    public String getSessionCacheKey ()
    {
        return getEmail().concat("-Session");
    }

    public static String getSessionCacheKey (String email)
    {
        return email.concat("-Session");
    }

    public static void initializeAccessControlList (String email)
    {
        Login login = (Login) CommonCache.getInstance().get(getSessionCacheKey(email));
        if (login == null) {
            return;
        }

        VolunteerService volunteerService = (VolunteerService) Util.getBean("volunteerServiceImpl");
        List<AccessControl> accessControlList = volunteerService.getAccessControlList(login.getVolunteerId());

        login.setAccessControlList(accessControlList);
        CommonCache.getInstance().put(getSessionCacheKey(email), login);
    }

    public static void initializeAccessControlList (Login login)
    {
        if (login == null) {
            return;
        }

        VolunteerService volunteerService = (VolunteerService) Util.getBean("volunteerServiceImpl");
        List<AccessControl> accessControlList = volunteerService.getAccessControlList(login.getVolunteerId());

        login.setAccessControlList(accessControlList);
    }

    public static void initializeAccessFilterList (String email)
    {
        Login login = (Login) CommonCache.getInstance().get(getSessionCacheKey(email));
        if (login == null) {
            return;
        }

        VolunteerService volunteerService = (VolunteerService) Util.getBean("volunteerServiceImpl");
        List<AccessFilter> accessFilterList = volunteerService.getAccessFilterList(login.getVolunteerId());

        login.setAccessFilterList(accessFilterList);
        CommonCache.getInstance().put(getSessionCacheKey(email), login);
    }

    public static void initializeAccessFilterList (Login login)
    {
        if (login == null) {
            return;
        }

        VolunteerService volunteerService = (VolunteerService) Util.getBean("volunteerServiceImpl");
        List<AccessFilter> accessFilterList = volunteerService.getAccessFilterList(login.getVolunteerId());

        login.setAccessFilterList(accessFilterList);
    }

    public static boolean isValidCacheEntry (String email)
    {
        Login login = (Login) CommonCache.getInstance().get(getSessionCacheKey(email));
        if (login == null) {
            return false;
        }

        if ((new Date().getTime() - login.getLastAccessed()) > 60 * 60 * 1000) {
            return false;
        }

        return true;
    }
}
