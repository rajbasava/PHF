<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<html>
<head>
	<title>Events - Yoga Vidya Pranic Healing Foundation of Karnataka</title>
    <mytags:style/>
    <mytags:menu/>
</head>
</head>
<body>
<h2 align="center">Access Filter List for <c:out value="${volunteerName}"/></h2>

<form:form method="post" action="addAccessFilter.htm" commandName="accessFilterForm">
<input type="hidden" name="volunteerId" value="<c:out value="${volunteerId}"/>" />
<table align="center" cellspacing="2">
	<tr>
        <td ><form:label path="eventId"><spring:message code="label.eventId"/></form:label></td>
        <td >
            <form:select path="eventId">
                <form:option value="" label="--- Select ---"/>
                <form:options items="${allEvents}" />
            </form:select>
        </td>
	</tr>
	<tr>
        <td ><form:label path="foundationIds">
                <spring:message code="label.foundation"/></form:label></td>
        <td >
            <form:select multiple="true" path="foundationIds" size="15">
                <form:options items="${allFoundations}" />
            </form:select>
        </td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<input type="submit" value="Add Access Filter"/>
		</td>
	</tr>
</table>
</form:form>
