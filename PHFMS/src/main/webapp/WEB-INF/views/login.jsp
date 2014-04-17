<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<html>
<head>
	<title>Arhatic Yoga Retreat - Volunteer Login</title>
</head>
<mytags:style/>
</head>
<body>
<h2 align="center">Sign In</h2>

<form:form method="post" action="login.htm" commandName="login">
<table align="center">
    <tr>
        <td>
            <img src="<c:url value="/resources/img/mcks_meditation.jpg"/>"/>
        </td>
        <td>
            <table valign="center" align="center" cellspacing="2">
                <tr>
                    <td><form:label path="email"><spring:message code="label.email"/></form:label></td>
                    <td><form:input path="email" /></td>
                </tr>
                <tr>
                    <td><form:label path="password"><spring:message code="label.password"/></form:label></td>
                    <td><form:password path="password"/></td>
                </tr>
                <tr>
                    <td><form:label path="counter"><spring:message code="label.counter"/></form:label></td>
                    <td><form:input path="counter" /></td>
                </tr>
                <tr align="center">
                    <td colspan="2" align="center">
                        <input type="submit" value="<spring:message code="label.signin"/>"/>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</form:form>
<mytags:footer/>
</body>
</html>
