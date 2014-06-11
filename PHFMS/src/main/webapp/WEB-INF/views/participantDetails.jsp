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
            $("#tabs").tabs();
            $("#tabs").css("min-height", "400px");
            $("#tabs").css("font-size", "11px");
            $('#tabs').tabs({ selected: 0 });

            $("a#addCourseToParticipant").button();
            $("a#addCourseToParticipant").css("font-size", "11px");
            $("a#addCourseToParticipant").click(function() {
                 $("#modifyParticipantDetails").get(0).setAttribute('action', 'showAddParticipantCourse.htm');
                 $("#modifyParticipantDetails").submit();
            });

            $("a#addTrainer").button();
            $("a#addTrainer").css("font-size", "11px");
            $("a#addTrainer").click(function() {
                 $("#modifyParticipantDetails").get(0).setAttribute('action', 'addTrainer.htm');
                 $("#modifyParticipantDetails").submit();
            });

            $("a#editParticipant").button();
            $("a#editParticipant").css("font-size", "11px");
            $("a#editParticipant").click(function() {
                 $("#modifyParticipantDetails").get(0).setAttribute('action', 'showParticipantDetails.htm');
                 $("#modifyParticipantDetails").submit();
            });

            $("#results").flexigrid({
                    colModel : [
                        {display: '<spring:message code="label.name"/>', width : 300, align: 'left'},
                        {display: '<spring:message code="label.course"/>', width : 100, align: 'left'},
                        {display: '<spring:message code="label.trainer"/>', width : 300, align: 'left'},
                        {display: '<spring:message code="label.startDate"/>', width : 100, align: 'left'},
                        {display: '<spring:message code="label.endDate"/>', width : 100, align: 'left'},
                        {display: '<spring:message code="label.foundation"/>', width : 150, align: 'left'},
                        {display: '<spring:message code="label.city"/>', width : 80, align: 'left'}
                    ],
                    useRp: true,
                    rp: 10,
                    showTableToggleBtn: false,
                    resizable: false,
                    height: 175,
                    width: 1290,
                    singleSelect: true
            });

            $("#newEvents").flexigrid({
					colModel : [
                        {display: '<spring:message code="label.name"/>', width : 200, align: 'left'},
                        {display: '<spring:message code="label.venue"/>', width : 75, align: 'left'},
                        {display: '<spring:message code="label.eventType"/>', width : 75, align: 'left'},
                        {display: '<spring:message code="label.primaryTrainer"/>', width : 200, align: 'left'},
                        {display: '<spring:message code="label.city"/>', width : 100, align: 'left'},
                        {display: '<spring:message code="label.primaryEligibility"/>', width : 200, align: 'left'},
                        {display: '<spring:message code="label.startDate"/>', width : 150, align: 'left'},
                        {display: '<spring:message code="label.endDate"/>', width : 100, align: 'left'},
                        {display: 'Action', width : 100, align: 'left'}
					],
					useRp: true,
					rp: 10,
					showTableToggleBtn: false,
					resizable: false,
					height: 150,
					width: 1290,
					singleSelect: true
			});

            $("#reviewEvents").flexigrid({
					colModel : [
                        {display: '<spring:message code="label.name"/>', width : 200, align: 'left'},
                        {display: '<spring:message code="label.venue"/>', width : 75, align: 'left'},
                        {display: '<spring:message code="label.eventType"/>', width : 75, align: 'left'},
                        {display: '<spring:message code="label.primaryTrainer"/>', width : 200, align: 'left'},
                        {display: '<spring:message code="label.city"/>', width : 100, align: 'left'},
                        {display: '<spring:message code="label.primaryEligibility"/>', width : 200, align: 'left'},
                        {display: '<spring:message code="label.startDate"/>', width : 150, align: 'left'},
                        {display: '<spring:message code="label.endDate"/>', width : 100, align: 'left'},
                        {display: 'Action', width : 100, align: 'left'}
					],
					useRp: true,
					rp: 10,
					showTableToggleBtn: false,
					resizable: false,
					height: 150,
					width: 1290,
					singleSelect: true
			});

            $("#registrations").flexigrid({
					colModel : [
                        {display: '<spring:message code="label.name"/>', width : 200, align: 'left'},
                        {display: '<spring:message code="label.venue"/>', width : 75, align: 'left'},
                        {display: '<spring:message code="label.eventType"/>', width : 75, align: 'left'},
                        {display: '<spring:message code="label.primaryTrainer"/>', width : 200, align: 'left'},
                        {display: '<spring:message code="label.city"/>', width : 100, align: 'left'},
                        {display: '<spring:message code="label.primaryEligibility"/>', width : 200, align: 'left'},
                        {display: '<spring:message code="label.startDate"/>', width : 150, align: 'left'},
                        {display: '<spring:message code="label.endDate"/>', width : 100, align: 'left'},
                        {display: 'Action', width : 100, align: 'left'}
					],
					useRp: true,
					rp: 10,
					showTableToggleBtn: false,
					resizable: false,
					height: 150,
					width: 1290,
					singleSelect: true
			});
        });
    </script>
</head>
<body>
<table width="100%" cellpadding="1" cellspacing="1">
    <tr height="20px"><td>&nbsp;</td></tr>
</table>
<div id="tabs">
    <ul>
        <li><a href="#tabs-1">Participant Details</a></li>
        <li><a href="#tabs-2">Available Courses</a></li>
        <li><a href="#tabs-3">Registered Courses</a></li>
    </ul>
    <div id="tabs-1">
        <script type="text/javascript">
            $(document).ready(function(){
                $("#startDate").datepicker({ showOn: 'button', dateFormat: 'dd/mm/yy', buttonImageOnly: true, buttonImage: '<c:url value="/resources/img/calendar.gif"/>' });
                $("#endDate").datepicker({ showOn: 'button', dateFormat: 'dd/mm/yy', buttonImageOnly: true, buttonImage: '<c:url value="/resources/img/calendar.gif"/>' });

                $("a#submit").button();
                $("a#submit").css("font-size", "11px");
                $("a#submit").click(function() {
                     $("#participant").get(0).setAttribute('action', 'updateParticipant.htm');
                     $("#participant").submit();
                });

            });
        </script>
        <c:choose>
            <c:when test="${isEdit}">
                <div class="formdatatab">
                    <div class="formtitle">Participant</div>
                    <div class="formbody">
                        <form:form method="post" action="" commandName="participant">
                            <form:errors path="*" cssClass="errorblock" element="div" />
                            <form:hidden path="id"/>
                            <c:set var="nested" value="false" scope="request"/>
                            <jsp:include page="participant.jsp">
                                <jsp:param name="nested" value="false"/>
                            </jsp:include>
                        </form:form>
                    </div>
                    <div class="formfooter">
                        <table width="100%">
                            <tr>
                                <td align="right">
                                    <div id="button">
                                        <a id="submit" href="#">Submit</a>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <jsp:include page="participantSummary.jsp">
                    <jsp:param name="class" value="formdatatab"/>
                    <jsp:param name="title" value="Participant"/>
                </jsp:include>
            </c:otherwise>
        </c:choose>


        <table width="100%">
            <tr style="background-color:#E8E8E8;">
                <td>Courses</td>
            </tr>
            <tr>
                <td>
                    <table id="results">
                        <tbody>
                            <c:if test="${!empty courses}">
                            <c:forEach items="${courses}" var="participantCourse">
                                <tr>
                                    <td><c:out value="${participantCourse.participant.name}"/> </td>
                                    <td><c:out value="${participantCourse.courseType.shortName}"/></td>
                                    <td><c:out value="${participantCourse.primaryTrainer.participant.name}"/></td>
                                    <td><c:out value="${participantCourse.startDate}"/> </td>
                                    <td><c:out value="${participantCourse.endDate}"/></td>
                                    <td><c:out value="${participantCourse.foundation.shortName}"/></td>
                                    <td><c:out value="${participantCourse.city}"/></td>
                                </tr>
                            </c:forEach>
                            </c:if>
                        <tbody>
                    </table>
                </td>
            </tr>
        </table>


        <div>
            <form id="modifyParticipantDetails" method="post" action="">
                <input type="hidden" name="participantId" value="<c:out value="${participant.id}"/>" />
                <input type="hidden" name="isEdit" value="true" />
                <table width="100%" >
                    <tr height="10px"><td>&nbsp;</td></tr>
                    <tr style="background-color:#E8E8E8;">
                        <td align="center" cellpadding="2px">
                            <a id="addCourseToParticipant" href="#">Add Completed Course</a>
                            <c:choose>
                                <c:when test="${isTrainer}">
                                    <a id="addTrainer" href="#">Add Trainer</a>
                                </c:when>
                            </c:choose>
                            <a id="editParticipant" href="#">Edit</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>

    <div id="tabs-2">
        <table width="100%" cellpadding="1" cellspacing="1">
            <tr height="10px"><td>&nbsp;</td></tr>
        </table>

        <table width="100%">
            <tr style="background-color:#E8E8E8;">
                <td>New Courses For Registration</td>
            </tr>
            <tr>
                <td>
                    <table id="newEvents">
                        <tbody>
                            <c:if  test="${!empty newEvents}">
                            <c:forEach items="${newEvents}" var="newEvent">
                                <tr>
                                    <td><c:out value="${newEvent.name}"/></td>
                                    <td><c:out value="${newEvent.venue}"/></td>
                                    <td><c:out value="${newEvent.eventTypeName}"/></td>
                                    <td><c:out value="${newEvent.primaryTrainer.participant.name}"/></td>
                                    <td><c:out value="${newEvent.city}"/> </td>
                                    <td><c:out value="${newEvent.primaryEligibility.name}"/></td>
                                    <td><c:out value="${newEvent.startDate}"/> </td>
                                    <td><c:out value="${newEvent.endDate}"/></td>
                                    <td class="YLink">
                                        <form id="openReg<c:out value="${participant.id}"/>For<c:out value="${newEvent.id}"/>" method="post" action="register.htm">
                                            <input type="hidden" name="participantId" value="<c:out value="${participant.id}"/>" />
                                            <input type="hidden" name="eventId" value="<c:out value="${newEvent.id}"/>" />
                                            <input type="hidden" name="review" value="false" />
                                            <a href="#" onclick="document.getElementById('openReg<c:out value="${participant.id}"/>For<c:out value="${newEvent.id}"/>').submit();">
                                                Register
                                            </a>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            </c:if>
                        <tbody>
                    </table>
                </td>
            </tr>
        </table>

        <table width="100%" cellpadding="1" cellspacing="1">
            <tr height="10px"><td>&nbsp;</td></tr>
        </table>

        <table width="100%">
            <tr style="background-color:#E8E8E8;">
                <td>Courses For Review</td>
            </tr>
            <tr>
                <td>
                    <table id="reviewEvents">
                        <tbody>
                            <c:if  test="${!empty reviewEvents}">
                            <c:forEach items="${reviewEvents}" var="reviewEvent">
                                <tr>
                                    <td><c:out value="${reviewEvent.name}"/></td>
                                    <td><c:out value="${reviewEvent.venue}"/></td>
                                    <td><c:out value="${reviewEvent.eventTypeName}"/></td>
                                    <td><c:out value="${reviewEvent.primaryTrainer.participant.name}"/></td>
                                    <td><c:out value="${reviewEvent.city}"/> </td>
                                    <td><c:out value="${reviewEvent.primaryEligibility.name}"/></td>
                                    <td><c:out value="${reviewEvent.startDate}"/> </td>
                                    <td><c:out value="${reviewEvent.endDate}"/></td>
                                    <td class="YLink">
                                        <form id="openReg<c:out value="${participant.id}"/>For<c:out value="${reviewEvent.id}"/>" method="post" action="register.htm">
                                            <input type="hidden" name="participantId" value="<c:out value="${participant.id}"/>" />
                                            <input type="hidden" name="eventId" value="<c:out value="${reviewEvent.id}"/>" />
                                            <input type="hidden" name="review" value="true" />
                                            <a href="#" onclick="document.getElementById('openReg<c:out value="${participant.id}"/>For<c:out value="${reviewEvent.id}"/>').submit();">
                                                Review
                                            </a>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            </c:if>
                        <tbody>
                    </table>
                </td>
            </tr>
        </table>
    </div>

    <div id="tabs-3">
        <table width="100%" cellpadding="1" cellspacing="1">
            <tr height="10px"><td>&nbsp;</td></tr>
        </table>

        <table width="100%">
            <tr style="background-color:#E8E8E8;">
                <td>Registered Events</td>
            </tr>
            <tr>
                <td>
                    <table id="registrations">
                        <tbody>
                            <c:if  test="${!empty registrations}">
                            <c:forEach items="${registrations}" var="registration">
                                <tr>
                                    <td><c:out value="${registration.event.name}"/></td>
                                    <td><c:out value="${registration.event.venue}"/></td>
                                    <td><c:out value="${registration.event.eventTypeName}"/></td>
                                    <td><c:out value="${registration.event.primaryTrainer.participant.name}"/></td>
                                    <td><c:out value="${registration.event.city}"/> </td>
                                    <td><c:out value="${registration.event.primaryEligibility.name}"/></td>
                                    <td><c:out value="${registration.event.startDate}"/> </td>
                                    <td><c:out value="${registration.event.endDate}"/></td>
                                    <td class="YLink">
                                        <form id="updatePart<c:out value="${registration.id}"/>" method="post" action="updateRegistration.htm">
                                            <input type="hidden" name="registrationId" value="<c:out value="${registration.id}"/>" />
                                            <a href="#" onclick="document.getElementById('updatePart<c:out value="${registration.id}"/>').submit();">
                                                Open
                                            </a>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            </c:if>
                        <tbody>
                    </table>
                </td>
            </tr>
        </table>
    </div>
</div>

<mytags:footer/>
</body>
</html>
