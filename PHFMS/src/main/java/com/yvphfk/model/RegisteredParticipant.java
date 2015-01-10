/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.model;

import com.yvphfk.common.AppProperties;
import com.yvphfk.common.ApplicationContextUtils;
import com.yvphfk.model.form.Event;
import com.yvphfk.model.form.EventPayment;
import com.yvphfk.model.form.EventRegistration;
import com.yvphfk.model.form.HistoryRecord;
import com.yvphfk.model.form.Participant;
import com.yvphfk.model.form.ParticipantSeat;
import org.springframework.context.ApplicationContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RegisteredParticipant implements Serializable, Importable
{
    public static final String ActionRegister = "Register";
    public static final String ActionUpdate = "Update";

    private Participant participant;
    private EventRegistration registration = new EventRegistration();
    private EventPayment currentPayment;
    private List<EventPayment> allPayments = new ArrayList<EventPayment>();
    private HistoryRecord currentHistoryRecord;
    private List<HistoryRecord> allHistoryRecords = new ArrayList<HistoryRecord>();
    private ParticipantSeat currentSeat;
    private List<ParticipantSeat> allSeats;
    private String action;
    private Integer eventId;
    private Integer eventFeeId;
    private String status;
    private boolean newbie;
    private EventPayment impPayment;  // need to merge currentPayment, impPayment, payment,allpayments
    private String foundationShortName;
    private String courseTypeShortName;


    public void initialize (String email)
    {
        getParticipant().initialize(email);
        getRegistration().initialize(email);
        initializePayments(email);
        initializeHistoryRecords(email);
    }

    public void initializePayments (String email)
    {
        List<EventPayment> list = getAllPayments();
        for (EventPayment payment : list) {
            payment.initialize(email);
        }
    }

    public void initializeHistoryRecords (String email)
    {
        List<HistoryRecord> list = getAllHistoryRecords();
        for (HistoryRecord record : list) {
            record.initialize(email);
        }
    }

    public void initializeForUpdate (String email)
    {
        getParticipant().initializeForUpdate(email);
        getRegistration().initializeForUpdate(email);
        initializePayments(email);
        initializeHistoryRecords(email);
    }

    public Participant getParticipant ()
    {
        return participant;
    }

    public void setParticipant (Participant participant)
    {
        this.participant = participant;
    }

    public EventRegistration getRegistration ()
    {
        return registration;
    }

    public void setRegistration (EventRegistration registration)
    {
        this.registration = registration;
    }

    public EventPayment getCurrentPayment ()
    {
        return currentPayment;
    }

    public void setCurrentPayment (EventPayment currentPayment)
    {
        this.currentPayment = currentPayment;
        setPayment(currentPayment);
    }

    public List<EventPayment> getAllPayments ()
    {
        return allPayments;
    }

    public void setAllPayments (List<EventPayment> allPayments)
    {
        this.allPayments = allPayments;
    }

    public HistoryRecord getCurrentHistoryRecord ()
    {
        return currentHistoryRecord;
    }

    public void setCurrentHistoryRecord (HistoryRecord currentHistoryRecord)
    {
        this.currentHistoryRecord = currentHistoryRecord;
        setHistoryRecord(currentHistoryRecord);
    }

    public List<HistoryRecord> getAllHistoryRecords ()
    {
        return allHistoryRecords;
    }

    public void setAllHistoryRecords (List<HistoryRecord> allHistoryRecords)
    {
        this.allHistoryRecords = allHistoryRecords;
    }

    public ParticipantSeat getCurrentSeat ()
    {
        return currentSeat;
    }

    public void setCurrentSeat (ParticipantSeat currentSeat)
    {
        this.currentSeat = currentSeat;
    }

    public List<ParticipantSeat> getAllSeats ()
    {
        return allSeats;
    }

    public void setAllSeats (List<ParticipantSeat> allSeats)
    {
        this.allSeats = allSeats;
    }

    public String getAction ()
    {
        return action;
    }

    public void setAction (String action)
    {
        this.action = action;
    }

    public Integer getEventId ()
    {
        return eventId;
    }

    public void setEventId (Integer eventId)
    {
        this.eventId = eventId;
    }

    public Integer getEventFeeId ()
    {
        return eventFeeId;
    }

    public void setEventFeeId (Integer eventFeeId)
    {
        this.eventFeeId = eventFeeId;
    }

    public void setPayment (EventPayment payment)
    {
        allPayments.add(payment);
    }

    public void setHistoryRecord (HistoryRecord record)
    {
        allHistoryRecords.add(record);
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public void initializeForImport (String email)
    {
        initialize(email);
        setAction(RegisteredParticipant.ActionRegister);
    }

    public void applyEvent (Event event)
    {
        getRegistration().setEvent(event);
    }

    public EventPayment getImpPayment ()
    {
        return impPayment;
    }

    public void setImpPayment (EventPayment impPayment)
    {
        this.impPayment = impPayment;
    }

    public boolean isDisplaySeat ()
    {
        ApplicationContext context = ApplicationContextUtils.getApplicationContext();

        if (context == null) {
            return false;
        }

        AppProperties appProperties =
                (AppProperties) context.getBean("appProperties");
        if (appProperties != null) {
            int days = appProperties.getDisplaySeatDays().intValue();

            if (days < 1) {
                return true;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, days);
            Date validToShow = calendar.getTime();
            return validToShow.after(getRegistration().getEvent().getStartDate());
        }

        return false;


    }

    public boolean isNewbie ()
    {
        return newbie;
    }

    public void setNewbie (boolean newbie)
    {
        this.newbie = newbie;
    }

    public String getFoundationShortName ()
    {
        return foundationShortName;
    }

    public void setFoundationShortName (String foundationShortName)
    {
        this.foundationShortName = foundationShortName;
    }

    public String getCourseTypeShortName ()
    {
        return courseTypeShortName;
    }

    public void setCourseTypeShortName (String courseTypeShortName)
    {
        this.courseTypeShortName = courseTypeShortName;
    }
}