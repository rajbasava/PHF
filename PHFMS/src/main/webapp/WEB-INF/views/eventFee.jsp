<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<html>
<head>
	<title>Events - Yoga Vidya Pranic Healing Foundation of Karnataka</title>
    <mytags:style/>
    <mytags:menu/>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#cutOffDate").datepicker({ showOn: 'button', dateFormat: 'dd/mm/yy', buttonImageOnly: true, buttonImage: '<c:url value="/resources/img/calendar.gif"/>' });
        });
    </script>
</head>
</head>
<body>
<h2 align="center">Event Fees for <c:out value="${event.name}"/></h2>

<form:form method="post" action="addEventFee.htm" commandName="eventFee">

<table align="center" cellspacing="2">
    <tr>
		<td><form:label path="name"><spring:message code="label.name"/></form:label></td>
		<td><form:input path="name" size="30"/></td>
        <td><form:label path="cutOffDate"><spring:message code="label.cutOffDate"/></form:label></td>
        <td><form:input path="cutOffDate"/></td>
	</tr>
	<tr>
        <td><form:label path="amount"><spring:message code="label.amount"/></form:label></td>
        <td><form:input path="amount" /></td>
        <td><form:label path="review"><spring:message code="label.review"/></form:label></td>
        <td><form:checkbox path="review" /></td>
	</tr>
    <tr>
        <td><form:label path="workshopLevelId"><spring:message code="label.workshopLevel"/></form:label></td>
        <td>
            <form:select path="workshopLevelId">
                <form:option value="0" label="--- Select ---"/>
                <form:options items="${workshopLevels}" />
            </form:select>
        </td>
	</tr>
	<tr>
		<td colspan="4" align="center">
		    <form:hidden path="eventId"/>
			<input type="submit" value="<spring:message code="label.addEventFee"/>"/>
		</td>
	</tr>
</table>
</form:form>

<mytags:footer/>
</body>
</html>
