<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<html>
<head>
    <title>Arhatic Yoga Retreat - Export Payments</title>
    <mytags:style/>
    <mytags:menu/>
    <script type="text/javascript">
        $(function() {
            $("a#excel").button();
            $("a#excel").css("font-size", "11px");
            $( "a#excel" ).click(function() {
                 $("#paymentCriteria").get(0).setAttribute('action', 'exportPayments.htm');
                 $("#paymentCriteria").submit();
            });

            $(document).ready(function() {
                $("#paymentCriteria input[name='fromReceiptDate']").datepicker({
                    showOn: 'button',
                    dateFormat: 'dd/mm/yy',
                    buttonImageOnly: true,
                    buttonImage: '<c:url value="/resources/img/calendar.gif"/>'
                });

                $("#paymentCriteria input[name='toReceiptDate']").datepicker({
                    showOn: 'button',
                    dateFormat: 'dd/mm/yy',
                    buttonImageOnly: true,
                    buttonImage: '<c:url value="/resources/img/calendar.gif"/>'
                });

                $("#paymentCriteria input[name='fromPdcDate']").datepicker({
                    showOn: 'button',
                    dateFormat: 'dd/mm/yy',
                    buttonImageOnly: true,
                    buttonImage: '<c:url value="/resources/img/calendar.gif"/>'
                });

                $("#paymentCriteria input[name='toPdcDate']").datepicker({
                    showOn: 'button',
                    dateFormat: 'dd/mm/yy',
                    buttonImageOnly: true,
                    buttonImage: '<c:url value="/resources/img/calendar.gif"/>'
                });
            });
        });
    </script>
</head>
</head>
<body>
<h2 align="center">Export Payments</h2>

<form:form method="post" commandName="paymentCriteria">

<table align="center" cellspacing="2">
    <tr>
        <td><form:label path="eventId"><spring:message code="label.eventId"/></form:label></td>
        <td>
            <form:select path="eventId">
                <form:option value="" label="--- Select ---"/>
                <form:options items="${allEvents}" />
            </form:select>
        </td>
        <td><form:label path="foundation"><spring:message code="label.foundation"/></form:label></td>
        <td>
            <form:select path="foundation">
                <form:option value="" label="--- Select ---"/>
                <form:options items="${allFoundations}" />
            </form:select>
        </td>
    </tr>
    <tr>
        <td><form:label path="courseTypeId"><spring:message code="label.courseType"/></form:label></td>
        <td>
            <form:select path="courseTypeId">
                <form:option value="" label="--- Select ---"/>
                <form:options items="${allParticipantCourseTypes}" />
            </form:select>
        </td>
        <td><form:label path="reference"><spring:message code="label.reference"/></form:label></td>
        <td>
            <form:select path="reference">
                <form:option value="" label="--- Select ---"/>
                <form:options items="${allReferenceGroups}" />
            </form:select>
        </td>
    </tr>
    <tr>
        <td><form:label path="mode"><spring:message code="label.mode"/></form:label></td>
        <td>
            <form:select path="mode">
                <form:option value="" label="--- Select ---"/>
                <form:options items="${allPaymentModes}" />
            </form:select>
        </td>
        <td><spring:message code="label.receiptDate"/></td>
        <td>From: <form:input path="fromReceiptDate"/>
            To: <form:input path="toReceiptDate"/></td>
    </tr>
    <tr>
        <td><form:label path="pdcNotClear"><spring:message code="label.pdcNotClear"/></form:label></td>
        <td><form:checkbox path="pdcNotClear"/></td>
        <td><spring:message code="label.pdcDate"/></td>
        <td>From: <form:input path="fromPdcDate"/> To: <form:input path="toPdcDate"/></td>
    </tr>
    <tr>
        <td colspan="4" align="center">
            <div id="button">
                <a id="excel" href="#"><spring:message code="label.report"/></a>
            </div>
        </td>
    </tr>
</table>
</form:form>