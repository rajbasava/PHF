/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.model.dao;

import com.yvphfk.common.AccessUtil;
import com.yvphfk.common.AmountPaidCategory;
import com.yvphfk.common.SeatingType;
import com.yvphfk.common.Util;
import com.yvphfk.model.Login;
import com.yvphfk.model.ParticipantCourseForm;
import com.yvphfk.model.ParticipantCriteria;
import com.yvphfk.model.PaymentCriteria;
import com.yvphfk.model.RegisteredParticipant;
import com.yvphfk.model.RegistrationCriteria;
import com.yvphfk.model.RegistrationForm;
import com.yvphfk.model.TrainerCriteria;
import com.yvphfk.model.form.BaseForm;
import com.yvphfk.model.form.Event;
import com.yvphfk.model.form.EventPayment;
import com.yvphfk.model.form.EventRegistration;
import com.yvphfk.model.form.HistoryRecord;
import com.yvphfk.model.form.Participant;
import com.yvphfk.model.form.ParticipantCourse;
import com.yvphfk.model.form.ParticipantSeat;
import com.yvphfk.model.form.Trainer;
import com.yvphfk.model.form.TrainerCourse;
import com.yvphfk.model.form.VolunteerKit;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Repository
public class ParticipantDAOImpl extends CommonDAOImpl implements ParticipantDAO
{
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

    public Participant saveOrUpdateParticipant (Participant participant)
    {
        Session session = sessionFactory.openSession();
        //todo check the uniqueness of the participant before adding.
        session.saveOrUpdate(participant);
//        createAndAddHistoryRecord(
//                messageSource.getMessage("key.participantAdded",
//                        new Object[]{participant.getId(),
//                                participant.getName()},
//                        null),
//                Util.getCurrentUser().getEmail(),
//                participant,
//                session);
        session.flush();
        session.close();
        return participant;
    }

    public Trainer addTrainer (Trainer trainer)
    {
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(trainer);
        Participant participant = trainer.getParticipant();
        createAndAddHistoryRecord(
                messageSource.getMessage("key.trainerAdded",
                        new Object[]{participant.getId(),
                                participant.getName()},
                        null),
                Util.getCurrentUser().getEmail(),
                trainer,
                session);
        session.flush();
        session.close();
        return trainer;
    }

    public Trainer getTrainer (Integer trainerId)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Trainer.class);
        criteria.add(Restrictions.eq("id", trainerId));
        List results = criteria.list();
        Trainer trainer = null;

        if (results != null && !results.isEmpty()) {
            trainer = (Trainer) results.get(0);
        }
        session.close();
        return trainer;
    }

    public Trainer getTrainerByParticipantId (Integer participantId)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Trainer.class);
        criteria.add(Restrictions.eq("participant.id", participantId));
        List results = criteria.list();
        Trainer trainer = null;

        if (results != null && !results.isEmpty()) {
            trainer = (Trainer) results.get(0);
        }
        session.close();
        return trainer;
    }

    public List<TrainerCourse> getTrainerCourses (Integer trainerId)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(TrainerCourse.class);
        criteria.add(Restrictions.eq("trainer.id", trainerId));
        List<TrainerCourse> results = criteria.list();

        session.close();
        return results;
    }

    public TrainerCourse addTrainerCourse (TrainerCourse trainerCourse)
    {
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(trainerCourse);
        Trainer trainer = trainerCourse.getTrainer();
        createAndAddHistoryRecord(
                messageSource.getMessage("key.trainerCourseAdded",
                        new Object[]{trainer.getParticipant().getName(),
                                trainerCourse.getCourseType().getShortName()},
                        null),
                Util.getCurrentUser().getEmail(),
                trainer,
                session);
        session.flush();
        session.close();
        return trainerCourse;
    }

    public ParticipantCourse addParticipantCourse (ParticipantCourseForm participantCourseForm)
    {
        Session session = sessionFactory.openSession();
        //todo check the uniqueness of the participant before adding.
        Participant participant = participantCourseForm.getParticipant();
        if (participant != null) {
            saveOrUpdateParticipant(participant);
        }

        ParticipantCourse participantCourse = participantCourseForm.getParticipantCourse();

        session.save(participantCourse);
//        createAndAddHistoryRecord(
//                messageSource.getMessage("key.participantAdded",
//                        new Object[]{participant.getId(),
//                                participant.getName()},
//                        null),
//                Util.getCurrentUser().getEmail(),
//                participant,
//                session);
        session.flush();
        session.close();
        return participantCourse;
    }

    public EventRegistration registerParticipant (RegisteredParticipant registeredParticipant, Login login)
    {
        Participant participant = registeredParticipant.getParticipant();
        EventRegistration registration = registeredParticipant.getRegistration();

        boolean partNoSave = false;
        if (participant.getId() != null) {
            participant = getParticipant(participant.getId());
            registration.setParticipant(participant);
            partNoSave = true;
        }

        List<HistoryRecord> records = registeredParticipant.getAllHistoryRecords();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        if (RegisteredParticipant.ActionRegister.equals(registeredParticipant.getAction())) {

            //todo check the uniqueness of the participant before adding.
            if (!partNoSave) {
                session.saveOrUpdate(participant);
                registration.setParticipant(participant);
            }

            if (Util.nullOrEmptyOrBlank(registration.getStatus())) {
                registration.setStatus(EventRegistration.StatusRegistered);
            }

            if (registration.getRegistrationDate() == null) {
                registration.setRegistrationDate(new Date());
            }
            session.save(registration);
            HistoryRecord record =
                    createHistoryRecord(
                            messageSource.getMessage("key.registrationAdded",
                                    new Object[]{registration.getId(),
                                            registration.getParticipant().getId(),
                                            registration.getEvent().getName()},
                                    null),
                            Util.getCurrentUser().getEmail(),
                            registration);
            records.add(record);

            ParticipantSeat participantSeat = registeredParticipant.getCurrentSeat();
            if (participantSeat != null) {
                if (participantSeat.getRegistration() == null) {
                    participantSeat.setRegistration(registration);
                    participantSeat.setEvent(registration.getEvent());
                    participantSeat.setCourseType(registration.getEvent().getPrimaryEligibility());
                }
                session.save(participantSeat);
            }
        }
        else if (RegisteredParticipant.ActionUpdate.equals(registeredParticipant.getAction())) {
            //  todo update changes properties of registration objects to comments
            registration.setParticipant(participant);
            HistoryRecord record =
                    createHistoryRecord(
                            messageSource.getMessage("key.registrationUpdated",
                                    new Object[]{registration.getId(),
                                            registration.getParticipant().getId(),
                                            registration.getEvent().getName()},
                                    null),
                            Util.getCurrentUser().getEmail(),
                            registration);
            records.add(record);
            session.update(registration);
        }

        transaction.commit();

        session.flush();
        session.close();

//        updateKits(login, registration);

        addHistoryRecords(records, registration);

        List<EventPayment> payments = registeredParticipant.getAllPayments();
        for (EventPayment payment : payments) {
            processPayment(payment, registration.getId(), true);
        }

        if (payments.isEmpty()) {
            updateTotalPayments(registration);
            updatePDCCount(registration);
        }

        return registration;
    }

    private void updateKits (Login login, EventRegistration registration)
    {
        Session session = sessionFactory.openSession();
        // local = false and eventKit = true update else event kit dont do anything
        // local = true and eventKit = false update event kit else dont do anything
        if (login != null && (registration.isEventKit() ^ registration.isLocalEventKitStatus())) {
            VolunteerKit volunteerKit =
                    getVolunteerKit(session, login.getEmail(), String.valueOf(registration.getEvent().getId()));
            if (volunteerKit != null) {
                if (registration.isEventKit()) {
                    if (volunteerKit != null) {
                        volunteerKit.setKitsGiven(volunteerKit.getKitsGiven() + 1);
                    }
                }
                else {
                    if (volunteerKit != null) {
                        volunteerKit.setKitsGiven(volunteerKit.getKitsGiven() - 1);
                    }
                }
                session.update(volunteerKit);
            }
            else {
                throw new NullPointerException("Contact Administrator to allocate kits");
            }
        }

        session.flush();
        session.close();

    }

    private VolunteerKit getVolunteerKit (Session session, String email, String eventId)
    {
        VolunteerKit volunteerKit = null;
//  *** Nested query ***
//        String queryStr = "select vk.id  " +
//                       "from phk_volkit vk " +
//                       "where vk.volloginid = (select vl.id  " +
//                                              "from phk_volunteer v, phk_vollogin vl  " +
//                                              "where vl.volunteerid = v.id and v.email = '"+email+"' ) " +
//                       "and vk.kitid = (select k.id  " +
//                                       "from phk_kit k  " +
//                                       "where k.eventid = "+eventId+")";
//  *** Query with join ***
        String queryStr = "select vk.id  " +
                "from phk_volkit vk " +
                "inner join phk_vollogin vl " +
                "on vk.volloginid = vl.id " +
                "inner join phk_volunteer v " +
                "on vl.volunteerid = v.id  " +
                "inner join phk_kit k " +
                "on vk.kitid = k.id  " +
                "where v.email = '" + email + "' " +
                "AND k.eventid = " + eventId;

        Query query = session.createSQLQuery(queryStr);
        List resultList = query.list();

        if (resultList != null & !resultList.isEmpty()) {
            Criteria criteria = session.createCriteria(VolunteerKit.class);

            criteria.add(Restrictions.eq("id", resultList.get(0)));
            List<VolunteerKit> volunteerKits = criteria.list();

            if (volunteerKits != null && !volunteerKits.isEmpty()) {
                volunteerKit = volunteerKits.get(0);
            }
        }
        return volunteerKit;
    }

    public void addParticipantSeat (ParticipantSeat participantSeat)
    {
        EventRegistration registration = getEventRegistration(participantSeat.getRegistrationId());
        participantSeat.setRegistration(registration);
        participantSeat.setEvent(registration.getEvent());
        if (participantSeat.getCourseType() == null) {
            participantSeat.setCourseType(registration.getEvent().getPrimaryEligibility());
        }
        saveOrUpdate(participantSeat);
    }

//    private void addParticipantSeats (ParticipantSeat participantSeat,
//                                      EventRegistration registration,
//                                      Session session)
//    {
//        Event event = registration.getEvent();
//        populateSeatNo(participantSeat, registration, event.getPrimaryEligibility());
//        session.save(participantSeat);
//        if (!registration.getEvent().getPrimaryEligibility().equals(registration.getPrimaryEligibility())) {
//            if (!registration.getEvent().isSeatPerLevel()) {
//                return;
//            }
//
//            List<String> lessLevels =
//                    ParticipantLevel.getAllLessLevels(
//                            registration.getEvent().getPrimaryEligibility(),
//                            registration.getPrimaryEligibility());
//
//            for (String level: lessLevels) {
//                ParticipantSeat seat = new ParticipantSeat();
//                populateSeatNo(seat, registration, level);
//                session.save(seat);
//            }
//        }
//    }

    private void populateSeatNo (ParticipantSeat participantSeat,
                                 EventRegistration registration, String level)
    {
        Event event = registration.getEvent();
        if (participantSeat.getSeat() == null) {
            Integer greatestSeat = getGreatestSeat(event, level);
            Integer greatestSeatNo = new Integer(1);
            if (greatestSeat != null) {
                greatestSeatNo = greatestSeat.intValue() + 1;
            }
            participantSeat.setSeat(greatestSeatNo);
        }
//        participantSeat.setCourseType(level);
        participantSeat.setEvent(registration.getEvent());
        participantSeat.setRegistration(registration);
    }

    private void updatePDCCount (EventRegistration registration)
    {
        int pendingPdcCount = 0;
        Session session = sessionFactory.openSession();
        session.refresh(registration);

        Set<EventPayment> paymentsSet = registration.getPayments();
        if (paymentsSet == null) {
            return;
        }
        Iterator<EventPayment> itr = paymentsSet.iterator();
        while (itr.hasNext()) {
            EventPayment payment = itr.next();
            if (payment.isPdcNotClear()) {
                pendingPdcCount++;
            }
        }

        registration.setPendingPdc(pendingPdcCount);

        session.update(registration);

        session.flush();
        session.close();
    }

    public void addHistoryRecords (List<HistoryRecord> records,
                                   BaseForm object)
    {
        Session session = sessionFactory.openSession();
        for (HistoryRecord record : records) {
            addHistoryRecord(record, object, session);
        }
        session.flush();
        session.close();
    }

    public void addHistoryRecord (HistoryRecord historyRecord,
                                  BaseForm object,
                                  Session session)
    {
        if (historyRecord != null &&
                !Util.nullOrEmptyOrBlank(historyRecord.getComment())) {
            historyRecord.setObject(object);
            session.save(historyRecord);
        }
    }


    public void createAndAddHistoryRecord (String comment,
                                           String preparedBy,
                                           BaseForm object,
                                           Session session)
    {
        HistoryRecord record =
                createHistoryRecord(comment, preparedBy, object);
        session.save(record);
    }

    public void processPayment (EventPayment payment,
                                Integer registrationId,
                                boolean isAdd)
    {
        if (payment.getAmountPaid() == null || payment.getAmountPaid() == 0) {
            return;
        }

        Session session = sessionFactory.openSession();
        EventRegistration registration = (EventRegistration) session.load(EventRegistration.class, registrationId);

        payment.setRegistration(registration);

        if (isAdd) {
            session.save(payment);
            createAndAddHistoryRecord(
                    messageSource.getMessage("key.paymentAdded",
                            new Object[]{payment.getId(), payment.getAmountPaid()},
                            null),
                    payment.getPreparedBy(),
                    registration, session);
        }
        else {
            session.update(payment);
            createAndAddHistoryRecord(
                    messageSource.getMessage("key.paymentUpdated",
                            new Object[]{payment.getId(), payment.getAmountPaid()},
                            null),
                    Util.getCurrentUser().getEmail(),
                    registration, session);
        }

        session.flush();
        session.close();

        updateTotalPayments(registration);

        updatePDCCount(registration);

    }

    private void updateTotalPayments (EventRegistration registration)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(EventPayment.class);
        criteria.add(Restrictions.eq("registration", registration));
        criteria.setProjection(Projections.sum("amountPaid"));
        Long totalAmountPaid = (Long) criteria.uniqueResult();

        if (totalAmountPaid == null) {
            totalAmountPaid = new Long(0);
        }

        registration.setTotalAmountPaid(totalAmountPaid);
        registration.setAmountDue(registration.getAmountPayable() - registration.getTotalAmountPaid());

        session.update(registration);
        session.flush();
        session.close();
    }

    public Participant getParticipant (Integer participantId)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Participant.class);
        criteria.setFetchMode("comments", FetchMode.EAGER);
        criteria.setFetchMode("seats", FetchMode.EAGER);
        criteria.add(Restrictions.eq("id", participantId));
        List results = criteria.list();
        Participant participant = null;

        if (results != null && !results.isEmpty()) {
            participant = (Participant) results.get(0);
        }
        session.close();
        return participant;
    }

    public Participant getParticipant (String name, String mobile)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Participant.class);
        criteria.setFetchMode("comments", FetchMode.EAGER);
        criteria.setFetchMode("seats", FetchMode.EAGER);
        criteria.add(Restrictions.eq("name", name).ignoreCase());
        criteria.add(Restrictions.eq("mobile", mobile));
        List results = criteria.list();
        Participant participant = null;

        if (results != null && !results.isEmpty()) {
            participant = (Participant) results.get(0);
        }
        session.close();

        return participant;
    }

    public void cancelRegistration (EventRegistration registration, HistoryRecord historyRecord)
    {
        Session session = sessionFactory.openSession();
        registration.setStatus(EventRegistration.StatusCancelled);
        session.update(registration);
        createAndAddHistoryRecord(
                messageSource.getMessage("key.registrationCancelled",
                        new Object[]{registration.getId(),
                                registration.getParticipant().getName()},
                        null),
                Util.getCurrentUser().getEmail(),
                registration, session);

        addHistoryRecord(historyRecord, registration, session);

        session.flush();
        session.close();
    }

    public void onHoldRegistration (EventRegistration registration, HistoryRecord historyRecord)
    {
        Session session = sessionFactory.openSession();
        registration.setStatus(EventRegistration.StatusOnHold);
        session.update(registration);
        createAndAddHistoryRecord(
                messageSource.getMessage("key.registrationOnHold",
                        new Object[]{registration.getId(),
                                registration.getParticipant().getName()},
                        null),
                Util.getCurrentUser().getEmail(),
                registration, session);

        addHistoryRecord(historyRecord, registration, session);

        session.flush();
        session.close();
    }

    public void changeToRegistered (EventRegistration registration, HistoryRecord historyRecord)
    {
        Session session = sessionFactory.openSession();
        registration.setStatus(EventRegistration.StatusRegistered);
        session.update(registration);
        createAndAddHistoryRecord(
                messageSource.getMessage("key.registrationUpdated",
                        new Object[]{registration.getId(),
                                registration.getParticipant().getName(),
                                registration.getEvent().getName()},
                        null),
                Util.getCurrentUser().getEmail(),
                registration, session);

        addHistoryRecord(historyRecord, registration, session);

        session.flush();
        session.close();
    }

    public EventRegistration getEventRegistration (Integer registrationId)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(EventRegistration.class);
        criteria.setFetchMode("event", FetchMode.EAGER);
        criteria.setFetchMode("payments", FetchMode.EAGER);
        criteria.setFetchMode("seats", FetchMode.EAGER);
        criteria.setFetchMode("participant", FetchMode.EAGER);
        criteria.add(Restrictions.eq("id", registrationId));
        List results = criteria.list();
        EventRegistration registration = null;

        if (results != null || !results.isEmpty()) {
            registration = (EventRegistration) results.get(0);
        }
        registration.setHistoryRecords(
                getHistoryRecords(registration.getId(), registration.getType(), session));
        session.close();
        registration.setLocalEventKitStatus(registration.isEventKit());
        return registration;
    }

    public List<Participant> listParticipants (ParticipantCriteria participantCriteria)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Participant.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.eq("active", true));

        if (participantCriteria.getMaxResults() != -1) {
            criteria.setMaxResults(participantCriteria.getMaxResults());
        }

        if (participantCriteria.getParticipantId() != null) {
            criteria.add(Restrictions.eq("id", participantCriteria.getParticipantId()));
            List<Participant> results = criteria.list();

            session.close();
            return results;
        }

        if (!Util.nullOrEmptyOrBlank(participantCriteria.getName())) {
            criteria.add(Restrictions.ilike("name", participantCriteria.getName(), MatchMode.ANYWHERE));
        }

        if (!Util.nullOrEmptyOrBlank(participantCriteria.getEmail())) {
            criteria.add(Restrictions.ilike("email", participantCriteria.getEmail(), MatchMode.ANYWHERE));
        }

        if (!Util.nullOrEmptyOrBlank(participantCriteria.getMobile())) {
            criteria.add(Restrictions.like("mobile", "%" + participantCriteria.getMobile() + "%"));
        }

        if (participantCriteria.isVip()) {
            criteria.add(Restrictions.eq("vip", participantCriteria.isVip()));
        }

        boolean hasCourseAlias = false;
        if (participantCriteria.getFoundationId() != null) {
            criteria.createAlias("courses", "courses");
            Integer foundationId = participantCriteria.getFoundationId();
            criteria.add(Restrictions.eq("courses.foundation.id", foundationId));
            hasCourseAlias = true;
        }

        if (participantCriteria.getCourseTypeId() != null) {
            if (!hasCourseAlias) {
                criteria.createAlias("courses", "courses");
            }
            criteria.add(Restrictions.eq("courses.courseType.id", participantCriteria.getCourseTypeId()));
        }

        List<Participant> results = criteria.list();

        session.close();
        return results;
    }

    public List<HistoryRecord> getHistoryRecords (Integer objectId, String objectType, Session session)
    {
        Criteria criteria = session.createCriteria(HistoryRecord.class);
        criteria.add(Restrictions.eq("objectId", objectId));
        criteria.add(Restrictions.eq("objectType", objectType));
        List<HistoryRecord> results = criteria.list();
        return results;
    }

    public List<EventRegistration> listRegistrations (RegistrationCriteria registrationCriteria)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(EventRegistration.class);
        criteria.createAlias("event", "event");
        criteria.addOrder(Order.asc("id"));

        if (registrationCriteria.getMaxResults() != -1) {
            criteria.setMaxResults(registrationCriteria.getMaxResults());
        }

        criteria.createAlias("participant", "participant");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        if (!registrationCriteria.isIncludeInactive()) {
            criteria.add(Restrictions.eq("active", true));
        }

        if (registrationCriteria.getParticipantId() != null) {
            criteria.add(Restrictions.eq("participant.id", registrationCriteria.getParticipantId()));
        }

        if (registrationCriteria.getId() != null) {
            criteria.add(Restrictions.eq("id", registrationCriteria.getId()));
        }

        if (registrationCriteria.isConsolidated()) {
            criteria.setFetchMode("payments", FetchMode.EAGER);
        }

        if (registrationCriteria.getSeat() != null) {
            criteria.createAlias("seats", "seats");
            criteria.add(Restrictions.eq("seats.seat", registrationCriteria.getSeat()));

            if (registrationCriteria.getCourseTypeId() != null) {
                criteria.add(Restrictions.eq("seats.courseType.id", registrationCriteria.getCourseTypeId()));
            }
        }
        else {
            if (registrationCriteria.getCourseTypeId() != null) {
                criteria.add(Restrictions.eq("courseType.id", registrationCriteria.getCourseTypeId()));
            }
        }

        if (!Util.nullOrEmptyOrBlank(registrationCriteria.getName())) {
            criteria.add(Restrictions.ilike("participant.name", registrationCriteria.getName(), MatchMode.ANYWHERE));
        }

        List<Integer> eventIds = AccessUtil.getEventFilterList();
        if (!eventIds.isEmpty()) {
            criteria.add(Restrictions.in("event.id", eventIds));
        }
        else if (registrationCriteria.getEventId() != null && registrationCriteria.getEventId() != -1) {
            criteria.add(Restrictions.eq("event.id", registrationCriteria.getEventId()));
        }

        if (registrationCriteria.getFromEventStartDate() != null) {
            criteria.add(Restrictions.ge("event.startDate", registrationCriteria.getFromEventStartDate()));
        }

        if (registrationCriteria.getToEventStartDate() != null) {
            criteria.add(Restrictions.le("event.endDate", registrationCriteria.getToEventStartDate()));
        }

        if (!Util.nullOrEmptyOrBlank(registrationCriteria.getEmail())) {
            criteria.add(Restrictions.ilike("participant.email", registrationCriteria.getEmail(), MatchMode.ANYWHERE));
        }

        List<Integer> foundationIds = AccessUtil.getFoundationIdFilterList();
        if (!foundationIds.isEmpty()) {
            criteria.add(Restrictions.in("foundation.id", foundationIds));
        }
        else if (!Util.nullOrEmptyOrBlank(registrationCriteria.getFoundationId())) {
            String foundationId = registrationCriteria.getFoundationId();
            criteria.add(Restrictions.eq("foundation.id", Integer.parseInt(foundationId)));
        }

        if (!Util.nullOrEmptyOrBlank(registrationCriteria.getMobile())) {
            criteria.add(Restrictions.like("participant.mobile", "%" + registrationCriteria.getMobile() + "%"));
        }

        if (registrationCriteria.isVip()) {
            criteria.add(Restrictions.eq("participant.vip", registrationCriteria.isVip()));
        }

        if (!Util.nullOrEmptyOrBlank(registrationCriteria.getReference())) {
            criteria.add(Restrictions.eq("reference", registrationCriteria.getReference()));
        }

        if (!Util.nullOrEmptyOrBlank(registrationCriteria.getAmountPaidCategory())) {
            String[] args = {"totalAmountPaid", "amountPayable"};
            String conditionTemplate =
                    AmountPaidCategory.getConditionTemplate(registrationCriteria.getAmountPaidCategory(), true);
            MessageFormat format = new MessageFormat(conditionTemplate);
            String sql = format.format(args);
            criteria.add(Restrictions.sqlRestriction(sql));
        }

        if (!Util.nullOrEmptyOrBlank(registrationCriteria.getFoodCoupon())) {
            criteria.add(Restrictions.eq("foodCoupon", Boolean.valueOf(registrationCriteria.getFoodCoupon()).booleanValue()));
        }

        if (!Util.nullOrEmptyOrBlank(registrationCriteria.getEventKit())) {
            criteria.add(Restrictions.eq("eventKit", Boolean.valueOf(registrationCriteria.getEventKit()).booleanValue()));
        }

        if (registrationCriteria.getFromRegistrationDate() != null) {
            criteria.add(Restrictions.ge("registrationDate", registrationCriteria.getFromRegistrationDate()));
        }

        if (registrationCriteria.getToRegistrationDate() != null) {
            criteria.add(Restrictions.le("registrationDate", registrationCriteria.getToRegistrationDate()));
        }

        if (registrationCriteria.getAmountDue() != null) {
            criteria.add(Restrictions.or(
                    Restrictions.ge("amountDue", registrationCriteria.getAmountDue()),
                    Restrictions.isNull("amountDue"))) ;
        }

        if (!Util.nullOrEmptyOrBlank(registrationCriteria.getStatus())) {
            criteria.add(Restrictions.eq("status", registrationCriteria.getStatus()));
        }

        List<EventRegistration> results = criteria.list();

        session.close();
        return results;
    }

    public List<EventRegistration> allUnallocatedRegistrations (Event event, boolean vip, int countryCode)
    {
        if (event == null) {
            return null;
        }

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(EventRegistration.class);
        criteria.createAlias("event", "event");
        criteria.createAlias("participant", "participant");
        criteria.createAlias("seats", "seats", CriteriaSpecification.LEFT_JOIN);

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.addOrder(Order.asc("registrationDate"));
        criteria.addOrder(Order.asc("refOrder"));
        criteria.addOrder(Order.asc("id"));

        criteria.add(Restrictions.eq("event.id", event.getId()));
        criteria.add(Restrictions.isNull("seats.seat"));
        criteria.add(Restrictions.eq("participant.vip", vip));

        switch (countryCode) {
            case Indians: {
                Criterion indCond = Restrictions.eq("participant.country", "India");
                Criterion emptyCond = Restrictions.eq("participant.country", " ");
                Criterion nullCond = Restrictions.isNull("participant.country");
                Criterion or1 = Restrictions.or(indCond, nullCond);
                Criterion or2 = Restrictions.or(or1, emptyCond);
                criteria.add(or2);
                break;
            }
            case NonIndians: {
                Criterion forgnCond = Restrictions.ne("participant.country", "India");
                Criterion notEmptyCond = Restrictions.ne("participant.country", "");
                Criterion notNullCond = Restrictions.isNotNull("participant.country");
                Criterion and1 = Restrictions.and(forgnCond, notNullCond);
                Criterion and2 = Restrictions.and(and1, notEmptyCond);
                criteria.add(and2);
                break;
            }
        }

        criteria.add(Restrictions.eq("status", EventRegistration.StatusRegistered));
        criteria.add(Restrictions.eq("active", true));

        List<EventRegistration> results = criteria.list();

        session.close();
        return results;
    }

    public List<EventRegistration> allRefOrderBasedUnallocatedRegistrations (Event event, boolean vip)
    {
        if (event == null) {
            return null;
        }

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(EventRegistration.class);
        criteria.createAlias("event", "event");
        criteria.createAlias("participant", "participant");
        criteria.createAlias("seats", "seats", CriteriaSpecification.LEFT_JOIN);

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.addOrder(Order.asc("refOrder"));
        criteria.addOrder(Order.asc("id"));

        criteria.add(Restrictions.eq("event.id", event.getId()));
        criteria.add(Restrictions.isNull("seats.seat"));
        criteria.add(Restrictions.eq("participant.vip", vip));

        criteria.add(Restrictions.eq("status", EventRegistration.StatusRegistered));
        criteria.add(Restrictions.eq("active", true));

        List<EventRegistration> results = criteria.list();

        session.close();
        return results;
    }

    private Integer getGreatestSeat (Event event, String level)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(ParticipantSeat.class);
        criteria.setProjection(Projections.max("seat"));

        criteria.add(Restrictions.like("event", event));
        criteria.add(Restrictions.eq("level", level));

        List seats = criteria.list();
        session.close();
        if (!seats.isEmpty()) {
            return (Integer) seats.get(0);
        }
        else {
            return null;
        }
    }

    public List<ParticipantSeat> getAllSeats (Event event, String level)
    {
        Session session = sessionFactory.openSession();

        Criteria criteria = session.createCriteria(ParticipantSeat.class);
        criteria.add(Restrictions.eq("event", event));
        criteria.add(Restrictions.eq("level", level));
        criteria.addOrder(Order.asc("seat"));

        List<ParticipantSeat> seats = criteria.list();

        return seats;
    }


    public List<EventPayment> listPayments (PaymentCriteria paymentCriteria)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(EventPayment.class);
        criteria.createAlias("registration", "registration");
        criteria.createAlias("registration.participant", "participant");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        List<Integer> eventIds = AccessUtil.getEventFilterList();
        if (!eventIds.isEmpty()) {
            criteria.add(Restrictions.in("registration.event.id", eventIds));
            criteria.add(Restrictions.eq("registration.event.active", true));
        }
        else if (paymentCriteria.getEventId() != null) {
            criteria.add(Restrictions.eq("registration.event.id", paymentCriteria.getEventId()));
            if (!paymentCriteria.isIncludeInactive()) {
                criteria.add(Restrictions.eq("registration.event.active", true));
            }
        }

        List<Integer> foundationIds = AccessUtil.getFoundationIdFilterList();
        if (!foundationIds.isEmpty()) {
            criteria.add(Restrictions.in("registration.foundation.id", foundationIds));
        }
        else if (!Util.nullOrEmptyOrBlank(paymentCriteria.getFoundation())) {
            String foundation = paymentCriteria.getFoundation();
            criteria.add(Restrictions.eq("registration.foundation.id", foundation));      // todo this is an integer
        }

        if (paymentCriteria.getCourseTypeId() != null) {
            criteria.add(Restrictions.eq("registration.courseType.id", paymentCriteria.getCourseTypeId()));
        }

        if (!Util.nullOrEmptyOrBlank(paymentCriteria.getReference())) {
            criteria.add(Restrictions.eq("registration.reference", paymentCriteria.getReference()));
        }
        if (!Util.nullOrEmptyOrBlank(paymentCriteria.getMode())) {
            criteria.add(Restrictions.eq("mode", paymentCriteria.getMode()));
        }

        if (paymentCriteria.isPdcNotClear()) {
            criteria.add(Restrictions.eq("pdcNotClear", paymentCriteria.isPdcNotClear()));
        }

        if (paymentCriteria.getFromReceiptDate() != null) {
            criteria.add(Restrictions.ge("receiptDate", paymentCriteria.getFromReceiptDate()));
        }

        if (paymentCriteria.getToReceiptDate() != null) {
            criteria.add(Restrictions.le("receiptDate", paymentCriteria.getToReceiptDate()));
        }

        if (paymentCriteria.getFromPdcDate() != null) {
            criteria.add(Restrictions.ge("pdcDate", paymentCriteria.getFromPdcDate()));
        }

        if (paymentCriteria.getToPdcDate() != null) {
            criteria.add(Restrictions.le("pdcDate", paymentCriteria.getToPdcDate()));
        }

        List<EventPayment> results = criteria.list();

        session.close();
        return results;
    }

    public void replaceParticipant (EventRegistration registration,
                                    Participant participantToReplace,
                                    HistoryRecord record)
    {
        Session session = sessionFactory.openSession();
        session.refresh(registration);
        Participant oldParticipant = registration.getParticipant();
        registration.setParticipant(participantToReplace);
        createAndAddHistoryRecord(
                messageSource.getMessage("key.registrationReplace",
                        new Object[]{registration.getId(),
                                registration.getEvent().getName(),
                                participantToReplace.getName(),
                                oldParticipant.getName()},
                        null
                ),
                Util.getCurrentUser().getEmail(),
                registration,
                session
        );
        addHistoryRecord(record, registration, session);
        session.update(registration);
        session.flush();
        session.close();
    }

    @Override
    public void removeEventRegistrations (Integer id)
    {
        Session session = sessionFactory.openSession();
        Event event = (Event) session.load(Event.class, id);
        Criteria criteria = session.createCriteria(EventRegistration.class);
        criteria.add(Restrictions.eq("event", event));
        List<EventRegistration> results = criteria.list();
        for (EventRegistration reg : results) {
            reg.setActive(false);
            session.update(reg);
        }
        session.flush();
        session.close();
    }

    public List<ParticipantSeat> getAllocatedSeats (Event event, String alpha, String suffix)
    {
        if (event == null) {
            return null;
        }

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(ParticipantSeat.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.addOrder(Order.asc("seat"));

        criteria.add(Restrictions.eq("event", event));

        if (!Util.nullOrEmptyOrBlank(alpha)) {
            criteria.add(Restrictions.eq("alpha", alpha));
        }

        if (!Util.nullOrEmptyOrBlank(suffix)) {
            criteria.add(Restrictions.eq("suffix", suffix));
        }

        List<ParticipantSeat> results = criteria.list();
        session.flush();
        session.close();
        return results;
    }

    public ParticipantSeat getMaxAllocatedSeat (Event event)
    {
        if (event == null) {
            return null;
        }

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(ParticipantSeat.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.addOrder(Order.desc("seat"));
        criteria.setMaxResults(1);

        criteria.add(Restrictions.eq("event", event));

        ParticipantSeat seat = (ParticipantSeat) criteria.uniqueResult();
        session.flush();
        session.close();
        return seat;
    }

    public List<ParticipantSeat> getAllSeats (Event event)
    {
        if (event == null) {
            return null;
        }

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(ParticipantSeat.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        if (SeatingType.AlphaNumerical.getKey().equals(event.getSeatingType())) {
            criteria.addOrder(Order.asc("alpha"));
        }
        criteria.addOrder(Order.asc("seat"));

        criteria.add(Restrictions.eq("event", event));

        List<ParticipantSeat> results = criteria.list();
        session.flush();
        session.close();
        return results;
    }

    public void updateRegistration (RegistrationForm registrationForm)
    {
        EventRegistration registration = getEventRegistration(registrationForm.getRegistrationId());

        Session session = sessionFactory.openSession();

        registration.setRefOrder(registrationForm.getRefOrder());
        if (registrationForm.getRegistrationDate() != null) {
            registration.setRegistrationDate(registrationForm.getRegistrationDate());
        }
        registration.initializeForImport(Util.getCurrentUser().getEmail());
        session.update(registration);
        session.flush();
        session.close();
    }

    public List<ParticipantCourse> getCourses (Integer participantId)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(ParticipantCourse.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.eq("active", true));

        criteria.add(Restrictions.eq("participant.id", participantId));
        List<ParticipantCourse> results = criteria.list();

        session.close();
        return results;
    }

    public List<Trainer> listTrainers (TrainerCriteria trainerCriteria)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Trainer.class);
        criteria.createAlias("participant", "participant");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.eq("active", true));

        if (trainerCriteria.getMaxResults() != -1) {
            criteria.setMaxResults(trainerCriteria.getMaxResults());
        }

        if (trainerCriteria.getParticipantId() != null) {
            criteria.add(Restrictions.eq("participant.id", trainerCriteria.getParticipantId()));
        }

        if (!Util.nullOrEmptyOrBlank(trainerCriteria.getName())) {
            criteria.add(Restrictions.ilike("participant.name", trainerCriteria.getName(), MatchMode.ANYWHERE));
        }

        if (!Util.nullOrEmptyOrBlank(trainerCriteria.getEmail())) {
            criteria.add(Restrictions.ilike("participant.email", trainerCriteria.getEmail(), MatchMode.ANYWHERE));
        }

        if (!Util.nullOrEmptyOrBlank(trainerCriteria.getMobile())) {
            criteria.add(Restrictions.like("participant.mobile", "%" + trainerCriteria.getMobile() + "%"));
        }

        boolean coursesFlag = false;
        if (trainerCriteria.getFoundationId() != null) {
            criteria.createAlias("courses", "courses");
            coursesFlag = true;
            Integer foundationId = trainerCriteria.getFoundationId();
            criteria.add(Restrictions.eq("courses.foundation.id", foundationId));
        }

        if (trainerCriteria.getCourseTypeId() != null) {
            if (!coursesFlag) {
                criteria.createAlias("courses", "courses");
            }
            criteria.add(Restrictions.eq("courses.courseType.id", trainerCriteria.getCourseTypeId()));
        }

        List<Trainer> results = criteria.list();

        session.close();
        return results;
    }

    public List<EventRegistration> allAttendedRegistrationsTill (Date tillDate)
    {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(EventRegistration.class);
        criteria.createAlias("event", "event");

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.eq("active", true));
        criteria.add(Restrictions.le("event.endDate", tillDate));
        criteria.add(Restrictions.eq("event.eventType", Event.EventTypeCourse));

        List<EventRegistration> results = criteria.list();

        session.close();
        return results;
    }


}