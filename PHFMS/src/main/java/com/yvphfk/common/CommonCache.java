/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.common;

import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

public class CommonCache
{
    private static CommonCache ourInstance = new CommonCache();

    private Map<Object, Object> cache = new HashedMap();

    public static CommonCache getInstance ()
    {
        return ourInstance;
    }

    public Object get (Object key)
    {
        return cache.get(key);
    }

    public void put (Object key, Object value)
    {
        synchronized (cache) {
            cache.put(key, value);
        }
    }

    public void remove (Object key)
    {
        synchronized (cache) {
            cache.remove(key);
        }
    }
}
