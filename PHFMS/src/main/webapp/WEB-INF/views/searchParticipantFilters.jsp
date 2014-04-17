<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<html>
<head>
    <title>Arhatic Yoga Retreat - Search Participants</title>
    <script type="text/javascript">
        $(function() {
            var moveLeft = 250;
            var moveDown = 200;
            $("a#submit").button();
            $("a#submit").css("font-size", "11px");
            $( "a#submit" ).click(function() {
                 $("#participantCriteria").get(0).setAttribute('action', 'listParticipants.htm');
                 $("#participantCriteria").submit();
            });
        });
    </script>
</head>
<body>
<div class="formdata">
<div class="formtitle">Search Participants</div>
<div class="formbody">
<form:form method="post" action="listParticipants.htm" commandName="participantCriteria">
<input type="hidden" name="page" value="<c:out value="${page}"/>"/>
<input type="hidden" name="registrationId" value="<c:out value="${registrationId}"/>"/>
<table align="center" cellspacing="2">
    <tr>
        <td><form:label path="participantId">Id</form:label></td>
        <td><form:input path="participantId" /></td>
        <td></td>
        <td></td>
    </tr>
    <tr>
        <td><form:label path="name"><spring:message code="label.name"/></form:label></td>
        <td><form:input path="name" /></td>
        <td><form:label path="foundationId"><spring:message code="label.foundation"/></form:label></td>
        <td>
            <form:select path="foundationId">
                <form:option value="" label="--- Select ---"/>
                <form:options items="${allFoundations}" />
            </form:select>
        </td>
    </tr>
    <tr>
        <td><form:label path="email"><spring:message code="label.email"/></form:label></td>
        <td><form:input path="email" /></td>
        <td width="40%"><form:label path="courseTypeId">Course</form:label></td>
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
        <td><form:label path="vip"><spring:message code="label.vip"/></form:label></td>
        <td><form:checkbox path="vip"/></td>
    </tr>
<table>
</div>
<div class="formfooter">
<table width="100%">
    <tr>
        <td align="right">
            <div id="button">
                <a id="submit" href="#">Search</a>
            </div>
        </td>
    </tr>
</table>
</form:form>
</div>
</div>
