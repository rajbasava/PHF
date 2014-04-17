/*
    Copyright (c) 2012-2015 Yoga Vidya Pranic Healing Foundation of Karnataka.
    All rights reserved. Patents pending.
*/

package com.yvphfk.service;

import com.yvphfk.model.form.Event;
import com.yvphfk.model.PaymentCriteria;
import com.yvphfk.model.RegistrationCriteria;

import javax.servlet.http.HttpServletResponse;

public interface DownloadService
{
    public void downloadRegistrationsReport (HttpServletResponse response, RegistrationCriteria criteria);

    public void downloadRegistrationsReportForImport (HttpServletResponse response, RegistrationCriteria criteria);

    public void downloadPaymentsReport (HttpServletResponse response, PaymentCriteria criteria);

    public void downloadSeats (HttpServletResponse response, Event event);
}
