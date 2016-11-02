/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.model.form;

import com.yvphfk.common.AmountPaidCategory;
import com.yvphfk.common.Util;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "phk_eventregstrn")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class EventRegistration extends BaseForm
{
    public static final String StatusRegistered = "Registered";
    public static final String StatusCancelled = "Cancelled";
    public static final String StatusOnHold = "OnHold";

    public static final int VegFood = 0;
    public static final int JainFood = 1;

    public static final String ClassName = "com.yvphfk.model.form.EventRegistration";

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer id;

    @Column(name = "AMOUNTPAYABLE")
    private Long amountPayable;

    @Column(name = "TOTALAMOUNTPAID")
    private Long totalAmountPaid;

    @Column(name = "AMOUNTDUE")
    private Long amountDue;

    @Column(name = "PENDINGPDC")
    private int pendingPdc;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "foundation")
    private PHFoundation foundation;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "eventFee")
    private EventFee eventFee;

    @Column(name = "REVIEW", columnDefinition = "default false")
    private boolean review = false;

    @Column(name = "FOODCOUPON", columnDefinition = "default false")
    private boolean foodCoupon = false;

    @Column(name = "EVENTKIT", columnDefinition = "default false")
    private boolean eventKit = false;

    @Column(name = "APPLICATION", columnDefinition = "default false")
    private boolean application = false;

    @Column(name = "CERTIFICATES", columnDefinition = "default false")
    private boolean certificates = false;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workshoplevel")
    private WorkshopLevel workshopLevel;

    @Column(name = "REFERENCE")
    private String reference;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "REFORDER", columnDefinition = "default 1")
    private Integer refOrder;

    @Column(name = "REGISTRATIONDATE", updatable = false)
    private Date registrationDate;

    @Column(name = "foodtype")
    private int foodType;

    @Column(name = "attend", columnDefinition = "default false")
    private boolean attend;

    @Column(name = "PREPAREDBY", updatable = false)
    private String preparedBy;

    @Column(name = "TIMECREATED", updatable = false)
    private Date timeCreated;

    @Column(name = "TIMEUPDATED")
    private Date timeUpdated;

    @Column(name = "ACTIVE")
    private boolean active;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EVENTID")
    private Event event;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARTICIPANTID")
    private Participant participant;

    @OneToMany(mappedBy = "registration")
    private Set<EventPayment> payments;

    @Transient
    private List<HistoryRecord> historyRecords;

    @OneToMany(mappedBy = "registration")
    private Set<ParticipantSeat> seats;

    @Transient
    private boolean localEventKitStatus = false;

    @Transient
    private Integer foundationId;

    @Transient
    private Integer workshopLevelId;

    @Transient
    private Integer eventFeeId;

    public Integer getId ()
    {
        return id;
    }

    public void setId (Integer id)
    {
        this.id = id;
    }

    public String getType ()
    {
        return ClassName;
    }

    public Long getAmountPayable ()
    {
        return amountPayable;
    }

    public void setAmountPayable (Long amountPayable)
    {
        this.amountPayable = amountPayable;
    }

    public Long getTotalAmountPaid ()
    {
        return totalAmountPaid;
    }

    public void setTotalAmountPaid (Long totalAmountPaid)
    {
        this.totalAmountPaid = totalAmountPaid;
    }

    public Long getAmountDue ()
    {
        return amountDue;
    }

    public void setAmountDue (Long amountDue)
    {
        this.amountDue = amountDue;
    }

    public int getPendingPdc ()
    {
        return pendingPdc;
    }

    public void setPendingPdc (int pendingPdc)
    {
        this.pendingPdc = pendingPdc;
    }

    public boolean isReview ()
    {
        return review;
    }

    public void setReview (boolean review)
    {
        this.review = review;
    }

    public boolean isFoodCoupon ()
    {
        return foodCoupon;
    }

    public void setFoodCoupon (boolean foodCoupon)
    {
        this.foodCoupon = foodCoupon;
    }

    public boolean isEventKit ()
    {
        return eventKit;
    }

    public void setEventKit (boolean eventKit)
    {
        this.eventKit = eventKit;
    }

    public boolean isApplication ()
    {
        return application;
    }

    public void setApplication (boolean application)
    {
        this.application = application;
    }

    public boolean isCertificates ()
    {
        return certificates;
    }

    public void setCertificates (boolean certificates)
    {
        this.certificates = certificates;
    }

    public WorkshopLevel getWorkshopLevel ()
    {
        return workshopLevel;
    }

    public void setWorkshopLevel (WorkshopLevel workshopLevel)
    {
        this.workshopLevel = workshopLevel;
    }

    public String getLevelName ()
    {
        if (getWorkshopLevel() == null) {
            return null;
        }

        return getWorkshopLevel().getName();
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public Date getRegistrationDate ()
    {
        return registrationDate;
    }

    public void setRegistrationDate (Date registrationDate)
    {
        this.registrationDate = registrationDate;
    }

    public String getPreparedBy ()
    {
        return preparedBy;
    }

    public void setPreparedBy (String preparedBy)
    {
        this.preparedBy = preparedBy;
    }

    public Date getTimeCreated ()
    {
        return timeCreated;
    }

    public void setTimeCreated (Date timeCreated)
    {
        this.timeCreated = timeCreated;
    }

    public Date getTimeUpdated ()
    {
        return timeUpdated;
    }

    public void setTimeUpdated (Date timeUpdated)
    {
        this.timeUpdated = timeUpdated;
    }

    public boolean isActive ()
    {
        return active;
    }

    public void setActive (boolean active)
    {
        this.active = active;
    }

    public Event getEvent ()
    {
        return event;
    }

    public void setEvent (Event event)
    {
        this.event = event;
    }

    public Participant getParticipant ()
    {
        return participant;
    }

    public void setParticipant (Participant participant)
    {
        this.participant = participant;
    }

    public Set<EventPayment> getPayments ()
    {
        return payments;
    }

    public void setPayments (Set<EventPayment> payments)
    {
        this.payments = payments;
    }

    public List<HistoryRecord> getHistoryRecords ()
    {
        return historyRecords;
    }

    public void setHistoryRecords (List<HistoryRecord> historyRecords)
    {
        this.historyRecords = historyRecords;
    }

    public Set<ParticipantSeat> getSeats ()
    {
        return seats;
    }

    public void setSeats (Set<ParticipantSeat> seats)
    {
        this.seats = seats;
    }

    public String getReference ()
    {
        return reference;
    }

    public void setReference (String reference)
    {
        this.reference = reference;
    }

    public Integer getRefOrder ()
    {
        return refOrder;
    }

    public void setRefOrder (Integer refOrder)
    {
        this.refOrder = refOrder;
    }

    public PHFoundation getFoundation ()
    {
        return foundation;
    }

    public void setFoundation (PHFoundation foundation)
    {
        this.foundation = foundation;
    }

    public Integer getFoundationId ()
    {
        return foundationId;
    }

    public EventFee getEventFee ()
    {
        return eventFee;
    }

    public void setEventFee (EventFee eventFee)
    {
        this.eventFee = eventFee;
    }

    public void setFoundationId (Integer foundationId)
    {
        this.foundationId = foundationId;
    }

    public String getCategory ()
    {
        String[] args = {"totalAmountPaid", "amountPayable"};
        Map amountPaidCategories = AmountPaidCategory.allAmountPaidCategories();
        Set keys = amountPaidCategories.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String template = AmountPaidCategory.getConditionTemplate(key, false);
            MessageFormat format = new MessageFormat(template);
            String expression = format.format(args);
            if (Util.evaluate(expression, this)) {
                return AmountPaidCategory.getName(key);
            }
        }

        return null;
    }

    public boolean isLocalEventKitStatus ()
    {
        return localEventKitStatus;
    }

    public void setLocalEventKitStatus (boolean localEventKitStatus)
    {
        this.localEventKitStatus = localEventKitStatus;
    }

    public Integer getWorkshopLevelId ()
    {
        return workshopLevelId;
    }

    public void setWorkshopLevelId (Integer workshopLevelId)
    {
        this.workshopLevelId = workshopLevelId;
    }

    public Integer getEventFeeId ()
    {
        return eventFeeId;
    }

    public void setEventFeeId (Integer eventFeeId)
    {
        this.eventFeeId = eventFeeId;
    }

    public int getFoodType ()
    {
        return foodType;
    }

    public void setFoodType (int foodType)
    {
        this.foodType = foodType;
    }

    public boolean isAttend ()
    {
        return attend;
    }

    public void setAttend (boolean attend)
    {
        this.attend = attend;
    }

    public boolean isAmountDue ()
    {
        return getAmountDue() == null || getAmountDue() > 0;
    }

    public boolean isJainFood ()
    {
        return getFoodType() == JainFood;
    }

    public boolean isVIP ()
    {
        return getParticipant().isVip();
    }

    public boolean isRegistered ()
    {
        return StatusRegistered.equals(getStatus());
    }

    public String foodType ()
    {
        return JainFood == getFoodType() ? "Jain Food" : "Veg Food";
    }
}
