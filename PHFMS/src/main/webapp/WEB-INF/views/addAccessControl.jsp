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
<h2 align="center">Access Control List for <c:out value="${volunteerName}"/></h2>

<form:form method="post" action="addAccessControl.htm" commandName="accessControl">
<input type="hidden" name="volunteerId" value="<c:out value="${volunteerId}"/>" />
<table align="center" cellspacing="2">
    <tr>
        <td><form:label path="permission"><spring:message code="label.permission"/></form:label></td>
        <td>
            <form:select path="permission">
                <form:option value="NONE" label="--- Select ---"/>
                <form:options items="${allVolunteerPermissions}" />
            </form:select>
        </td>
    </tr>
	<tr>
		<td colspan="2" align="center">
			<input type="submit" value="Add Access Control"/>
		</td>
	</tr>
</table>
</form:form>
