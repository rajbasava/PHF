<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<html>
<head>
	<title>Reference Groups - Yoga Vidya Pranic Healing Foundation of Karnataka</title>
<mytags:style/>
<mytags:menu/>
</head>
</head>
<body>
<h2 align="center">Reference Groups</h2>

<form:form method="post" action="addReferenceGroup.htm" commandName="referenceGroup">

<table align="center" cellspacing="2">
    <tr>
		<td><form:label path="name"><spring:message code="label.name"/></form:label></td>
		<td><form:input path="name" size="30"/></td>
	</tr>
	<tr>
        <td><form:label path="mobile"><spring:message code="label.mobile"/></form:label></td>
        <td><form:input path="mobile" /></td>
	</tr>
	<tr>
        <td><form:label path="email"><spring:message code="label.email"/></form:label></td>
        <td><form:input path="email" /></td>
	</tr>
    <tr>
        <td><form:label path="remarks"><spring:message code="label.remarks"/></form:label></td>
        <td><form:textarea path="remarks" rows="5" cols="30"/></td>
    </tr>
	<tr>
		<td colspan="2" align="center">
			<input type="submit" value="<spring:message code="label.addReferenceGroup"/>"/>
		</td>
	</tr>
</table>
</form:form>

<c:if  test="${!empty referenceGroupList}">
<h3>Reference Groups</h3>
<table class="data" border="1" cellpadding="1" cellspacing="1" width="100%">
<tr>
    <th><spring:message code="label.name"/></th>
    <th><spring:message code="label.mobile"/></th>
    <th><spring:message code="label.email"/></th>
    <th><spring:message code="label.remarks"/></th>
</tr>
<c:forEach items="${referenceGroupList}" var="referenceGroup">
	<tr>
		<td><c:out value="${referenceGroup.name}"/> </td>
		<td><c:out value="${referenceGroup.mobile}"/></td>
		<td><c:out value="${referenceGroup.email}"/> </td>
		<td><c:out value="${referenceGroup.remarks}"/></td>
	</tr>
</c:forEach>
</table>
</c:if>

<mytags:footer/>
</body>
</html>
