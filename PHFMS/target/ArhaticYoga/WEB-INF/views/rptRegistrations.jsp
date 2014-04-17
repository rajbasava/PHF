<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<html>
<head>
    <title>Arhatic Yoga Retreat - Export Registrations</title>
    <mytags:style/>
    <mytags:menu/>
    <script type="text/javascript">
        $(function() {
            $("a#exportRegistrations").button();
            $("a#exportRegistrations").css("font-size", "11px");
            $("a#exportRegistrations" ).click(function() {
                 $("#registrationCriteria").get(0).setAttribute('action', 'exportRegistrations.htm');
                 $("#registrationCriteria").submit();
            });

            $("a#exportConsolRegistrations").button();
            $("a#exportConsolRegistrations").css("font-size", "11px");
            $("a#exportConsolRegistrations" ).click(function() {
                 $("#registrationCriteria").get(0).setAttribute('action', 'exportConsolRegistrations.htm');
                 $("#registrationCriteria").submit();
            });

            $("a#exportRegistrationsForImp").button();
            $("a#exportRegistrationsForImp").css("font-size", "11px");
            $("a#exportRegistrationsForImp" ).click(function() {
                 $("#registrationCriteria").get(0).setAttribute('action', 'exportRegistrationsForImp.htm');
                 $("#registrationCriteria").submit();
            });


            $(document).ready(function() {
                $("#registrationCriteria input[name='fromRegistrationDate']").datepicker({
                    showOn: 'button',
                    dateFormat: 'dd/mm/yy',
                    buttonImageOnly: true,
                    buttonImage: '<c:url value="/resources/img/calendar.gif"/>'
                });

                $("#registrationCriteria input[name='toRegistrationDate']").datepicker({
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
<h2 align="center">Export Registrations</h2>

<form:form method="post" action="genRptRegistrations.htm" commandName="registrationCriteria">

<table align="center" cellpadding="3" cellspacing="3">
    <tr>
        <td><form:label path="name"><spring:message code="label.name"/></form:label></td>
        <td><form:input path="name" /></td>
        <td><form:label path="foundation"><spring:message code="label.foundation"/></form:label></td>
        <td>
            <form:select path="foundation">
                <form:option value="" label="--- Select ---"/>
                <form:options items="${allFoundations}" />
            </form:select>
        </td>
    </tr>
    <tr>
        <td><form:label path="email"><spring:message code="label.email"/></form:label></td>
        <td><form:input path="email" /></td>
        <td><form:label path="courseTypeId"><spring:message code="label.courseType"/></form:label></td>
        <td>
            <form:select path="courseTypeId">
                <form:option value="" label="--- Select ---"/>
                <form:options items="${allParticipantCourseTypes}" />
            </form:select>
        </td>
    </tr>
    <tr>
        <td><form:label path="mobile"><spring:message code="label.mobile"/></form:label></td>
        <td><form:input path="mobile" /></td>
        <td><form:label path="seat"><spring:message code="label.seat"/></form:label></td>
        <td><form:input path="seat" /></td>
    </tr>
    <tr>
        <td><form:label path="eventId"><spring:message code="label.eventId"/></form:label></td>
        <td>
            <form:select path="eventId">
                <form:option value="" label="--- Select ---"/>
                <form:options items="${allEvents}" />
            </form:select>
        </td>
        <td><form:label path="vip"><spring:message code="label.vip"/></form:label></td>
        <td><form:checkbox path="vip"/></td>
    </tr>
    <tr>
        <td><form:label path="amountPaidCategory"><spring:message code="label.amountPaidCategory"/></form:label></td>
        <td>
            <form:select path="amountPaidCategory">
                <form:option value="" label="--- Select ---"/>
                <form:options items="${allAmountPaidCategories}" />
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
        <td><form:label path="foodCoupon"><spring:message code="label.foodCoupon"/></form:label></td>
        <td>
            <form:radiobutton path="foodCoupon" value="true"/>True &nbsp;
            <form:radiobutton path="foodCoupon" value="false"/>False &nbsp;
            <form:radiobutton path="foodCoupon" value=""/>Both
        </td>
        <td><form:label path="eventKit"><spring:message code="label.eventKit"/></form:label></td>
        <td>
            <form:radiobutton path="eventKit" value="true"/>True &nbsp;
            <form:radiobutton path="eventKit" value="false"/>False &nbsp;
            <form:radiobutton path="eventKit" value=""/>Both
        </td>
    </tr>
    <tr>
        <td><form:label path="status"><spring:message code="label.status"/></form:label></td>
        <td>
            <form:select path="status">
                <form:option value="" label="--- Select ---"/>
                <form:options items="${allStatuses}" />
            </form:select>
        </td>
        <td><spring:message code="label.registrationDate"/></td>
        <td>From: <form:input path="fromRegistrationDate"/> To: <form:input path="toRegistrationDate"/></td>
    </tr>
    <tr>
        <td colspan="4" align="center">
            <div id="button">
                <a id="exportRegistrations" href="#"><spring:message code="label.report"/></a>
                <a id="exportConsolRegistrations" href="#"><spring:message code="label.exportConsolRegistrations"/></a>
                <a id="exportRegistrationsForImp" href="#"><spring:message code="label.exportRegistrationsForImp"/></a>
            </div>
        </td>
    </tr>
</table>
</form:form>
