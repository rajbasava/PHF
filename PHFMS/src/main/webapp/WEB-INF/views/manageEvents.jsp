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
            $("#startDate").datepicker({ showOn: 'button', dateFormat: 'dd/mm/yy', buttonImageOnly: true, buttonImage: '<c:url value="/resources/img/calendar.gif"/>' });
            $("#endDate").datepicker({ showOn: 'button', dateFormat: 'dd/mm/yy', buttonImageOnly: true, buttonImage: '<c:url value="/resources/img/calendar.gif"/>' });

            $("a#submit").button();
            $("a#submit").css("font-size", "11px");
            $("a#submit").click(function() {
                 $("#eventCriteria").get(0).setAttribute('action', 'searchEvents.htm');
                 $("#eventCriteria").submit();
            });

            $("#results").flexigrid({
					colModel : [
                        {display: '<spring:message code="label.name"/>', width : 200, align: 'left'},
                        {display: '<spring:message code="label.eventType"/>', width : 75, align: 'left'},
                        {display: '<spring:message code="label.primaryTrainer"/>', width : 200, align: 'left'},
                        {display: '<spring:message code="label.venue"/>', width : 75, align: 'left'},
                        {display: '<spring:message code="label.city"/>', width : 75, align: 'left'},
                        {display: '<spring:message code="label.primaryEligibility"/>', width : 250, align: 'left'},
                        {display: '<spring:message code="label.startDate"/>', width : 100, align: 'left'},
                        {display: '<spring:message code="label.endDate"/>', width : 100, align: 'left'}
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
<div class="formtitle">Search Events</div>
<div class="formbody">
<form:form method="post" action="searchEvents.htm" commandName="eventCriteria">
<form:errors path="*" cssClass="errorblock" element="div" />
<table align="center">
	<tr valign="top">
		<td>
			<table>
			    <tr>
                    <td><form:label path="name"><spring:message code="label.name"/></form:label></td>
                    <td><form:input path="name" size="30"/></td>
                </tr>
			    <tr>
                    <td><form:label path="courseTypeId">Course</form:label></td>
                    <td>
                        <form:select path="courseTypeId">
                            <form:option value="0" label="--- Select ---"/>
                            <form:options items="${allParticipantCourseTypes}" />
                        </form:select>
                    </td>
                </tr>
			    <tr>
                    <td><form:label path="primaryTrainerId">Primary Trainer</form:label></td>
                    <td><form:input path="primaryTrainerId" size="30"/></td>
                </tr>
			    <tr>
                    <td><form:label path="eventType">Event Type</form:label></td>
                    <td>
                        <form:select path="eventType">
                            <form:option value="0" label="--- Select ---"/>
                            <form:options items="${eventTypeMap}" />
                        </form:select>
                    </td>
                </tr>
            </table>
        </td>
        <td>
            <table>
                <tr>
                    <td><form:label path="startDate"><spring:message code="label.startDate"/></form:label></td>
                    <td><form:input path="startDate" /></td>
                </tr>
                <tr>
                    <td><form:label path="endDate"><spring:message code="label.endDate"/></form:label></td>
                    <td><form:input path="endDate" /></td>
                </tr>
                <tr>
                    <td><form:label path="venue"><spring:message code="label.venue"/></form:label></td>
                    <td><form:input path="venue"/></td>
                </tr>
			</table>
		</td>
	</tr>
</table>
</div>
<div class="formfooter">
<table width="100%">
    <tr>
        <td align="right">
            <div id="button">
                <a id="submit" href="#"><spring:message code="label.search"/></a>
            </div>
        </td>
    </tr>
</table>
</form:form>
</div>
</div>

<c:if  test="${!empty eventList}">
<table width="100%" cellpadding="3" cellspacing="3">
    <tr style="background-color:#E8E8E8;">
        <td>Events</td>
    </tr>
    <tr>
        <td>
            <table id="results">
                <tbody>
                    <c:forEach items="${eventList}" var="event">
                        <tr>
                            <td class="YLink">
                                <form id="updatePart<c:out value="${event.id}"/>" method="post" action="showEventDetails.htm">
                                    <input type="hidden" name="eventId" value="<c:out value="${event.id}"/>" />
                                    <input type="hidden" name="isEdit" value="false" />
                                    <a href="#" onclick="document.getElementById('updatePart<c:out value="${event.id}"/>').submit();">
                                        <c:out value="${event.name}"/>
                                    </a>
                                </form>
                            </td>
                            <td><c:out value="${event.eventTypeName}"/></td>
                            <td><c:out value="${event.primaryTrainer.participant.name}"/></td>
                            <td><c:out value="${event.venue}"/></td>
                            <td><c:out value="${event.city}"/> </td>
                            <td><c:out value="${event.primaryEligibility.name}"/></td>
                            <td><c:out value="${event.startDate}"/> </td>
                            <td><c:out value="${event.endDate}"/></td>
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
