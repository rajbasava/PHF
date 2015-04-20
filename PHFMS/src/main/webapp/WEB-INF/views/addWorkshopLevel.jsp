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
<h2 align="center">Workshop Levels for <c:out value="${event.name}"/></h2>

<form:form method="post" action="addWorkshopLevel.htm" commandName="workshopLevel">

<table align="center" cellspacing="2">
    <tr>
        <td><form:label path="courseTypeId">Course</form:label></td>
        <td>
            <form:select path="courseTypeId">
                <form:option value="-1" label="--- Select ---"/>
                <form:options items="${allParticipantCourseTypes}" />
            </form:select>
        </td>
	</tr>
	<tr>
		<td><form:label path="start"><spring:message code="label.start"/></form:label></td>
        <td >
            <form:radiobutton path="start" value="true"/>True &nbsp;
            <form:radiobutton path="start" value="false"/>False &nbsp;
        </td>
	</tr>
	<tr>
        <td><form:label path="levelOrder"><spring:message code="label.levelOrder"/></form:label></td>
        <td><form:input path="levelOrder"/></td>
	</tr>
	<tr>
		<td colspan="4" align="center">
		    <form:hidden path="eventId"/>
			<input type="submit" value="<spring:message code="label.addWorkshopLevel"/>"/>
		</td>
	</tr>
</table>
</form:form>

<mytags:footer/>
</body>
</html>
