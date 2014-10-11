<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<html>
<head>
	<title>Trainers - Yoga Vidya Pranic Healing Foundation of Karnataka</title>
	<mytags:style/>
    <mytags:menu/>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#contractStartDate").datepicker({
                        showOn: 'button',
                        dateFormat: 'dd/mm/yy',
                        buttonImageOnly: true,
                        buttonImage: '<c:url value="/resources/img/calendar.gif"/>'
            });
            $("#contractEndDate").datepicker({
                        showOn: 'button',
                        dateFormat: 'dd/mm/yy',
                        buttonImageOnly: true,
                        buttonImage: '<c:url value="/resources/img/calendar.gif"/>'
            });

            $("a#submit").button();
            $("a#submit").css("font-size", "11px");
            $("a#submit").click(function() {
                 $("#trainerCriteria").get(0).setAttribute('action', 'listTrainers.htm');
                 $("#trainerCriteria").submit();
            });

            $("#results").flexigrid({
					colModel : [
                        {display: '<spring:message code="label.id"/>', width : 30, align: 'left'},
                        {display: '<spring:message code="label.name"/>', width : 600, align: 'left'},
                        {display: '<spring:message code="label.email"/>', width : 350, align: 'left'},
                        {display: '<spring:message code="label.mobile"/>', width : 250, align: 'left'}
					],
					useRp: true,
					rp: 10,
					showTableToggleBtn: false,
					resizable: false,
					height: 275,
					width: 1325,
					singleSelect: true
			});

        });
    </script>
</head>
<body>
<div class="formdata">
<div class="formtitle">Search Participants</div>
<div class="formbody">
<form:form method="post" action="" commandName="trainerCriteria">
<table align="center" cellspacing="2">
    <tr>
        <td><form:label path="participantId">Id</form:label></td>
        <td><form:input path="participantId" /></td>
        <td><form:label path="mobile"><spring:message code="label.mobile"/></form:label></td>
        <td><form:input path="mobile" /></td>
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

<c:if  test="${!empty trainers}">
<table width="100%" cellpadding="3" cellspacing="3">
    <tr style="background-color:#E8E8E8;">
        <td>Events</td>
    </tr>
    <tr>
        <td>
            <table id="results">
                <tbody>
                    <c:forEach items="${trainers}" var="trainer">
                        <tr>
                            <td  width="5%"><c:out value="${participant.id}"/></td>
                            <td class="YLink">
                                <form id="openPart<c:out value="${trainer.id}"/>" method="post" action="showTrainerDetails.htm">
                                    <input type="hidden" name="trainerId" value="<c:out value="${trainer.id}"/>" />
                                    <a href="#" onclick="document.getElementById('openPart<c:out value="${trainer.id}"/>').submit();">
                                        <c:out value="${trainer.participant.name}"/>
                                    </a>
                                </form>
                            </td>
                            <td width="19%"><c:out value="${trainer.participant.email}"/></td>
                            <td width="12%"><c:out value="${trainer.participant.mobile}"/></td>
                        </tr>
                    </c:forEach>
                <tbody>
            </table>
        </td>
    </tr>
</table>
</c:if>

<mytags:footer/>
</body>
</html>
