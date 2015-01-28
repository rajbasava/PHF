/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/
package com.yvphfk.common;

import java.util.ArrayList;
import java.util.List;

public class ImportObjectMeta
{
    public static final String KeyRegistrations = "Registrations";
    public static final String KeyRegistrationsUpdate = "RegistrationsUpdate";
    public static final String KeyRowMeta = "RowMeta";
    public static final String KeyCustomSeats = "CustomSeats";

    public static ImportObjectMeta Registrations =
            new ImportObjectMeta(KeyRegistrations,
                    "com.yvphfk.model.RegisteredParticipant",
                    "registerParticipant",
                    "registeredParticipantImportableProcessor");

    public static ImportObjectMeta RowMeta =
            new ImportObjectMeta(KeyRowMeta,
                    "com.yvphfk.model.form.RowMeta",
                    "addRowMeta");

    public static ImportObjectMeta CustomSeats =
            new ImportObjectMeta(KeyCustomSeats,
                    "com.yvphfk.model.form.ParticipantSeat",
                    "addParticipantSeat",
                    "participantSeatImportableProcessor");

    public static ImportObjectMeta RegistrationsUpdate =
            new ImportObjectMeta(KeyRegistrationsUpdate,
                    "com.yvphfk.model.RegistrationForm",
                    "updateRegistration");

    private String reportName;
    private String baseClass;
    private String methodName;
    private String processorBean;
    private List<ImportObjectSheet> sheets = new ArrayList<ImportObjectSheet>();

    private ImportObjectMeta (String reportName, String baseClass, String methodName)
    {
        this(reportName, baseClass, methodName, null);
    }

    private ImportObjectMeta (String reportName, String baseClass, String methodName, String processorBean)
    {
        this.reportName = reportName;
        this.baseClass = baseClass;
        this.methodName = methodName;
        this.processorBean = processorBean;
        this.initialize();
    }

    private void initialize ()
    {
        if (KeyRegistrations.equals(this.getReportName())) {
            ImportObjectSheet registrationsSheet = getRegistrationSheet();
            ImportObjectSheet paymentsSheet = getPaymentsSheet();
            this.addSheet(registrationsSheet);
            this.addSheet(paymentsSheet);
        }

        if (KeyRowMeta.equals(this.getReportName())) {
            ImportObjectSheet rowMetaSheet = getRowMetaSheet();
            this.addSheet(rowMetaSheet);
        }

        if (KeyCustomSeats.equals(this.getReportName())) {
            ImportObjectSheet customSeatsSheet = getCustomSeatsSheet();
            this.addSheet(customSeatsSheet);
        }

        if (KeyRegistrationsUpdate.equals(this.getReportName())) {
            ImportObjectSheet customSeatsSheet = getRegistrationsUpdateSheet();
            this.addSheet(customSeatsSheet);
        }
    }

    private ImportObjectSheet getRegistrationSheet ()
    {
        //{"Name","Email","Mobile","Home","Foundation","Vip","VipDesc"};
        List<String> registrationFieldPaths = new ArrayList<String>();
        registrationFieldPaths.add("participant.name");
        registrationFieldPaths.add("participant.email");
        registrationFieldPaths.add("participant.mobile");
        registrationFieldPaths.add("participant.home");
        registrationFieldPaths.add("participant.address");
        registrationFieldPaths.add("foundationShortName");
        registrationFieldPaths.add("participant.vip");
        registrationFieldPaths.add("participant.vipDesc");

        //{"AmountPayable","Review","Level","Reference","Application","Certificates","RegistrationDate","Comments"};
        registrationFieldPaths.add("registration.amountPayable");
        registrationFieldPaths.add("registration.review");
        registrationFieldPaths.add("courseTypeShortName");
        registrationFieldPaths.add("registration.reference");
        registrationFieldPaths.add("registration.refOrder");
        registrationFieldPaths.add("registration.application");
        registrationFieldPaths.add("registration.certificates");
        registrationFieldPaths.add("registration.registrationDate");
        registrationFieldPaths.add("registration.status");

        return new ImportObjectSheet("Registrations", registrationFieldPaths, null, null);
    }

    private ImportObjectSheet getPaymentsSheet ()
    {
        //{"AmountPaid","Mode","ReceiptInfo","ReceiptDate","PdcNotClear","Pdc","PdcDate","Remarks"};
        List<String> paymentFieldPaths = new ArrayList<String>();
        paymentFieldPaths.add("impPayment.amountPaid");
        paymentFieldPaths.add("impPayment.mode");
        paymentFieldPaths.add("impPayment.receiptInfo");
        paymentFieldPaths.add("impPayment.receiptDate");
        paymentFieldPaths.add("impPayment.pdcNotClear");
        paymentFieldPaths.add("impPayment.pdc");
        paymentFieldPaths.add("impPayment.pdcDate");
        paymentFieldPaths.add("impPayment.remarks");
        return new ImportObjectSheet("Payments", paymentFieldPaths, "impPayment", "payment");
    }

    private ImportObjectSheet getRowMetaSheet ()
    {
        //{"Name","Email","Mobile","Home","Foundation","Vip","VipDesc"};
        List<String> rowMetaFieldPaths = new ArrayList<String>();
        rowMetaFieldPaths.add("name");
        rowMetaFieldPaths.add("sortOrder");
        rowMetaFieldPaths.add("rowName");
        rowMetaFieldPaths.add("rowMax");
        return new ImportObjectSheet("RowMeta", rowMetaFieldPaths, null, null);
    }

    private ImportObjectSheet getCustomSeatsSheet ()
    {
        //{"Name","Email","Mobile","Home","Foundation","Vip","VipDesc"};
        List<String> rowMetaFieldPaths = new ArrayList<String>();
        rowMetaFieldPaths.add("registrationId");
        rowMetaFieldPaths.add("CourseTypeShortName");
        rowMetaFieldPaths.add("alpha");
        rowMetaFieldPaths.add("seat");
        rowMetaFieldPaths.add("custom");
        return new ImportObjectSheet("CustomSeats", rowMetaFieldPaths, null, null);
    }

    private ImportObjectSheet getRegistrationsUpdateSheet ()
    {
        List<String> rowMetaFieldPaths = new ArrayList<String>();
        rowMetaFieldPaths.add("registrationId");
        rowMetaFieldPaths.add("refOrder");
        return new ImportObjectSheet("RegistrationsUpdate", rowMetaFieldPaths, null, null);
    }

    public String getReportName ()
    {
        return reportName;
    }

    public String getBaseClass ()
    {
        return baseClass;
    }

    public String getMethodName ()
    {
        return methodName;
    }

    public String getProcessorBean ()
    {
        return processorBean;
    }

    public void setProcessorBean (String processorBean)
    {
        this.processorBean = processorBean;
    }

    public List<ImportObjectSheet> getSheets ()
    {
        return sheets;
    }

    public void addSheet (ImportObjectSheet sheet)
    {
        getSheets().add(sheet);
    }

    public class ImportObjectSheet
    {
        private String sheetName;
        private List<String> fieldPaths;
        private String fieldPathGetter;
        private String fieldPathSetter;


        private ImportObjectSheet (String sheetName,
                                   List<String> firstFieldPaths,
                                   String fieldPathGetter,
                                   String fieldPathSetter)
        {
            this.sheetName = sheetName;
            this.fieldPaths = firstFieldPaths;
            this.fieldPathGetter = fieldPathGetter;
            this.fieldPathSetter = fieldPathSetter;
        }

        public String getSheetName ()
        {
            return sheetName;
        }

        public List<String> getFieldPaths ()
        {
            return fieldPaths;
        }

        public String getFieldPathGetter ()
        {
            return fieldPathGetter;
        }

        public String getFieldPathSetter ()
        {
            return fieldPathSetter;
        }
    }
}
