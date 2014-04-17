/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum ParticipantLevel
{
    PH1("PH1", "Basic Pranic Healing"),
    PH2("PH2", "Advanced Pranic Healing"),
    PH3("PH3", "Pranic Psychotherapy"),
    AYL0("AYL0", "Arahtic Yoga Preparation"),
    AYL1("AYL1", "Arahtic Yoga Level 1"),
    AYL2("AYL2", "Arahtic Yoga Level 2"),
    AYL3_1("AYL3.1", "Arahtic Yoga Level 3.1"),
    AYL3_2("AYL3.2", "Arahtic Yoga Level 3.2"),
    AYL4_1("AYL4.1", "Arahtic Yoga Level 4.1"),
    AYL4_2("AYL4.2", "Arahtic Yoga Level 4.2"),
    AYL5("AYL5", "Arahtic Yoga Level 5 And Above");

    private String key;
    private String name;

    private static Map<String, String> allParticipantLevels;

    private ParticipantLevel (String key, String name)
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

    public static Map<String, String> allParticipantLevels ()
    {
        if (allParticipantLevels == null) {
            allParticipantLevels = new LinkedHashMap<String, String>();

            allParticipantLevels.put(ParticipantLevel.PH1.getKey(), ParticipantLevel.PH1.getName());
            allParticipantLevels.put(ParticipantLevel.PH2.getKey(), ParticipantLevel.PH2.getName());
            allParticipantLevels.put(ParticipantLevel.PH3.getKey(), ParticipantLevel.PH3.getName());
            allParticipantLevels.put(ParticipantLevel.AYL0.getKey(), ParticipantLevel.AYL0.getName());
            allParticipantLevels.put(ParticipantLevel.AYL1.getKey(), ParticipantLevel.AYL1.getName());
            allParticipantLevels.put(ParticipantLevel.AYL2.getKey(), ParticipantLevel.AYL2.getName());
            allParticipantLevels.put(ParticipantLevel.AYL3_1.getKey(), ParticipantLevel.AYL3_1.getName());
            allParticipantLevels.put(ParticipantLevel.AYL3_2.getKey(), ParticipantLevel.AYL3_2.getName());
            allParticipantLevels.put(ParticipantLevel.AYL4_1.getKey(), ParticipantLevel.AYL4_1.getName());
            allParticipantLevels.put(ParticipantLevel.AYL4_2.getKey(), ParticipantLevel.AYL4_2.getName());
            allParticipantLevels.put(ParticipantLevel.AYL5.getKey(), ParticipantLevel.AYL5.getName());
        }

        return allParticipantLevels;
    }

    public static String getName (String key)
    {
        return allParticipantLevels().get(key);
    }

    public static List<String> getAllLessLevels (String startLevel, String endLevel)
    {
        List<String> list = new ArrayList<String>();
        list.addAll(allParticipantLevels().keySet());

        return list.subList(list.indexOf(startLevel) + 1, list.indexOf(endLevel) + 1);
    }
}
