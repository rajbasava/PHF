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
<h2 align="center">Kits for Event: <c:out value="${event.name}"/></h2>

<table class="data" border="1" cellpadding="1" cellspacing="1" width="50%" align="center">
    <tr>
        <th><spring:message code="label.name"/></th>
        <th><spring:message code="label.stock"/></th>
        <th>&nbsp;</th>
    </tr>
	<tr>
		<td><c:out value="${event.name}"/> </td>
		<td><c:out value="${eventKit.stock}"/></td>
		<td class="YLink">
            <form id="showEventKitsUI<c:out value="${eventKit.id}"/>" method="post" action="showEventKitsUI.htm">
                <input type="hidden" name="id" value="<c:out value="${eventKit.id}"/>" />
                <input type="hidden" name="eventId" value="<c:out value="${event.id}"/>" />
                <a href="#" onclick="document.getElementById('showEventKitsUI<c:out value="${eventKit.id}"/>').submit();"><spring:message code="label.update"/></a>
            </form>
        </td>
	</tr>
</table>

<mytags:footer/>
</body>
</html>
