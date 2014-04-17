<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="mytags"%>
<html>
<head>
<mytags:style />
<mytags:menu />
</head>
<body>

<table width="80%" align="center">
	<form:form method="post" action="importRegistrations.htm" commandName="importFile" enctype="multipart/form-data">
	<tr>
		<td>Select <form:label path="eventId"><spring:message code="label.eventId"/></form:label></td>
		<td>
            <form:select path="eventId">
                <form:option value="NONE" label="--- Select ---"/>
                <form:options items="${allEvents}" />
            </form:select>
        </td>
        <td>Select a file to <b>Import Participants</b> :<form:input type="file" path="file"/></td>
        <td><input type="submit" value="Upload"/></td>
	</tr>
	</form:form>
    <tr height="3px" style="background-color:#E8E8E8;">
        <td colspan="4"></td>
    </tr>
	<form:form method="post" action="importRowMeta.htm" commandName="importFile" enctype="multipart/form-data">
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
        <td>Select a file to <b>Import Row Meta</b> :<form:input type="file" path="file"/></td>
        <td><input type="submit" value="Upload"/></td>
	</tr>
	</form:form>
    <tr height="3px" style="background-color:#E8E8E8;">
        <td colspan="4"></td>
    </tr>
	<form:form method="post" action="importCustomSeats.htm" commandName="importFile" enctype="multipart/form-data">
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
        <td>Select a file to <b>Import Custom Seats</b> :<form:input type="file" path="file"/></td>
        <td><input type="submit" value="Upload"/></td>
	</tr>
	</form:form>
    <tr height="3px" style="background-color:#E8E8E8;">
        <td colspan="4"></td>
    </tr>
	<form:form method="post" action="importUpdateRegistration.htm" commandName="importFile" enctype="multipart/form-data">
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
        <td>Select a file to <b>Import Update Registrations</b> :<form:input type="file" path="file"/></td>
        <td><input type="submit" value="Upload"/></td>
	</tr>
	</form:form>
</table>

<mytags:footer/>
</body>
</html>
