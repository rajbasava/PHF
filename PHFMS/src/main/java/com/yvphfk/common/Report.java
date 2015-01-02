/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.common;

import java.util.ArrayList;
import java.util.List;

public class Report
{
    public static Report Registrations =
            new Report("Registrations", "com.yvphfk.model.form.EventRegistration");

    public static Report ConsolidatedRegistrations =
            new Report("ConsolidatedRegistrations", "com.yvphfk.model.form.EventRegistration");

    public static Report RegistrationsForImport =
            new Report("RegistrationsForImport", "com.yvphfk.model.form.EventRegistration");

    public static Report Payments =
            new Report("Payments", "com.yvphfk.model.form.EventPayment");

    public static Report Seats =
            new Report("Seats", "com.yvphfk.model.form.ParticipantSeat");

    private String reportName;
    private String classType;
    private List<ReportSheet> sheets = new ArrayList<ReportSheet>();

    private Report (String reportName,
                    String classType)
    {
        this.reportName = reportName;
        this.classType = classType;
        this.initialize();
    }

    private void initialize ()
    {
        if ("Registrations".equals(this.getReportName())) {
            ReportSheet registrationSheet = getRegistrationSheet();
            this.addSheet(registrationSheet);
        }

        if ("ConsolidatedRegistrations".equals(this.getReportName())) {
            ReportSheet registrationSheet = getRegistrationSheet();
            this.addSheet(registrationSheet);

            ReportSheet registrationPMTSheet = getRegistrationPaymentsSheet();
            this.addSheet(registrationPMTSheet);
        }

        if ("RegistrationsForImport".equals(this.getReportName())) {
            ReportSheet registrationSheet = getRegistrationSheetForImport();
            this.addSheet(registrationSheet);

            ReportSheet registrationPMTSheet = getRegistrationPaymentsSheetForImport();
            this.addSheet(registrationPMTSheet);
        }

        if ("Payments".equals(this.getReportName())) {
            ReportSheet paymentSheet = getPaymentsSheet();
            this.addSheet(paymentSheet);
        }

        if ("Seats".equals(this.getReportName())) {
            ReportSheet seatsSheet = getSeatsSheet();
            this.addSheet(seatsSheet);
        }
    }

    private ReportSheet getSeatsSheet ()
    {
        List<String> paymentFieldPaths = new ArrayList<String>();
        paymentFieldPaths.add("registration.id");
        paymentFieldPaths.add("registration.participant.name");
        paymentFieldPaths.add("registration.courseType.shortName");
        paymentFieldPaths.add("event.name");
        paymentFieldPaths.add("courseType.shortName");
        paymentFieldPaths.add("alpha");
        paymentFieldPaths.add("seat");
        paymentFieldPaths.add("custom");
        return new ReportSheet("Seats", paymentFieldPaths);
    }

    private ReportSheet getPaymentsSheet ()
    {
        List<String> paymentFieldPaths = new ArrayList<String>();
        paymentFieldPaths.add("registration.id");
        paymentFieldPaths.add("registration.participant.name");
        paymentFieldPaths.add("registration.participant.email");
        paymentFieldPaths.add("registration.participant.mobile");
        paymentFieldPaths.add("registration.event.name");
        paymentFieldPaths.add("registration.amountPayable");
        paymentFieldPaths.add("amountPaid");
        paymentFieldPaths.add("mode");
        paymentFieldPaths.add("receiptInfo");
        paymentFieldPaths.add("receiptDate");
        paymentFieldPaths.add("pdcNotClear");
        paymentFieldPaths.add("pdc");
        paymentFieldPaths.add("pdcDate");
        paymentFieldPaths.add("timeCreated");
        return new ReportSheet("Payments", paymentFieldPaths);
    }

    private ReportSheet getRegistrationPaymentsSheet ()
    {
        List<String> registrationPMTFieldPaths = new ArrayList<String>();
        registrationPMTFieldPaths.add("registration.id");
        registrationPMTFieldPaths.add("amountPaid");
        registrationPMTFieldPaths.add("mode");
        registrationPMTFieldPaths.add("receiptInfo");
        registrationPMTFieldPaths.add("receiptDate");
        registrationPMTFieldPaths.add("pdcNotClear");
        registrationPMTFieldPaths.add("pdc");
        registrationPMTFieldPaths.add("pdcDate");
        registrationPMTFieldPaths.add("timeCreated");
        return new ReportSheet("Payments", registrationPMTFieldPaths,
                "payments", "com.yvphfk.model.form.EventPayment");
    }

    private ReportSheet getRegistrationSheet ()
    {
        List<String> registrationFieldPaths = new ArrayList<String>();
        registrationFieldPaths.add("id");
        registrationFieldPaths.add("participant.name");
        registrationFieldPaths.add("participant.email");
        registrationFieldPaths.add("participant.mobile");
        registrationFieldPaths.add("participant.home");
        registrationFieldPaths.add("foundation.shortName");
        registrationFieldPaths.add("participant.vip");
        registrationFieldPaths.add("participant.vipDesc");
        registrationFieldPaths.add("event.name");
        registrationFieldPaths.add("amountPayable");
        registrationFieldPaths.add("totalAmountPaid");
        registrationFieldPaths.add("amountDue");
        registrationFieldPaths.add("pendingPdc");
        registrationFieldPaths.add("review");
        registrationFieldPaths.add("courseType.ShortName");
        registrationFieldPaths.add("reference");
        registrationFieldPaths.add("application");
        registrationFieldPaths.add("certificates");
        registrationFieldPaths.add("foodCoupon");
        registrationFieldPaths.add("eventKit");
        registrationFieldPaths.add("registrationDate");
        registrationFieldPaths.add("status");
        registrationFieldPaths.add("Category");
        return new ReportSheet("Registrations", registrationFieldPaths);
    }

    private ReportSheet getRegistrationSheetForImport ()
    {
        List<String> registrationFieldPaths = new ArrayList<String>();
        registrationFieldPaths.add("id");
        registrationFieldPaths.add("participant.name");
        registrationFieldPaths.add("participant.email");
        registrationFieldPaths.add("participant.mobile");
        registrationFieldPaths.add("participant.home");
        registrationFieldPaths.add("participant.address");
        registrationFieldPaths.add("foundation.shortName");
        registrationFieldPaths.add("participant.vip");
        registrationFieldPaths.add("participant.vipDesc");
        registrationFieldPaths.add("amountPayable");
        registrationFieldPaths.add("review");
        registrationFieldPaths.add("courseType.shortName");
        registrationFieldPaths.add("reference");
        registrationFieldPaths.add("refOrder");
        registrationFieldPaths.add("application");
        registrationFieldPaths.add("certificates");
        registrationFieldPaths.add("registrationDate");
        registrationFieldPaths.add("status");
        return new ReportSheet("Registrations", registrationFieldPaths);
    }

    private ReportSheet getRegistrationPaymentsSheetForImport ()
    {
        List<String> registrationPMTFieldPaths = new ArrayList<String>();
        registrationPMTFieldPaths.add("registration.id");
        registrationPMTFieldPaths.add("amountPaid");
        registrationPMTFieldPaths.add("mode");
        registrationPMTFieldPaths.add("receiptInfo");
        registrationPMTFieldPaths.add("receiptDate");
        registrationPMTFieldPaths.add("pdcNotClear");
        registrationPMTFieldPaths.add("pdc");
        registrationPMTFieldPaths.add("pdcDate");
        registrationPMTFieldPaths.add("remarks");
        return new ReportSheet("Payments", registrationPMTFieldPaths,
                "payments", "com.yvphfk.model.form.EventPayment");
    }


    public String getReportName ()
    {
        return reportName;
    }

    public void setReportName (String reportName)
    {
        this.reportName = reportName;
    }

    public String getClassType ()
    {
        return classType;
    }

    public void setClassType (String classType)
    {
        this.classType = classType;
    }

    public List<ReportSheet> getSheets ()
    {
        return sheets;
    }

    public void addSheet (ReportSheet sheet)
    {
        getSheets().add(sheet);
    }

    public class ReportSheet
    {
        private String sheetName;
        private List<String> fieldPaths;
        private String baseFieldPath;
        private String classType;


        private ReportSheet (String sheetName,
                             List<String> fieldPaths)
        {
            this.sheetName = sheetName;
            this.fieldPaths = fieldPaths;
        }

        private ReportSheet (String sheetName,
                             List<String> fieldPaths,
                             String baseFieldPath,
                             String classType)
        {
            this.sheetName = sheetName;
            this.fieldPaths = fieldPaths;
            this.baseFieldPath = baseFieldPath;
            this.classType = classType;
        }

        public String getSheetName ()
        {
            return sheetName;
        }

        public void setSheetName (String sheetName)
        {
            this.sheetName = sheetName;
        }

        public List<String> getFieldPaths ()
        {
            return fieldPaths;
        }

        public void setFieldPaths (List<String> fieldPaths)
        {
            this.fieldPaths = fieldPaths;
        }

        public String getBaseFieldPath ()
        {
            return baseFieldPath;
        }

        public void setBaseFieldPath (String baseFieldPath)
        {
            this.baseFieldPath = baseFieldPath;
        }

        public String getClassType ()
        {
            return classType;
        }

        public void setClassType (String classType)
        {
            this.classType = classType;
        }
    }
}
