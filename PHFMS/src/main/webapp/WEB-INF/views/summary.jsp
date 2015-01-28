<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<html>
<head>
	<title>Arhatic Yoga Retreat - Registered Participant Summary</title>
    <mytags:style/>
    <mytags:menu/>
    <script type="text/javascript">
        $(document).ready(function(){
            $(function() {
                $("a#nextRegistration").button();
                $("a#nextRegistration").css("font-size", "11px");
                $("a#nextRegistration").click(function() {
                     $("#registrationSummary").get(0).setAttribute('action', 'register.htm');
                     $("#registrationSummary").submit();
                });

                $("a#searchRegistration").button();
                $("a#searchRegistration").css("font-size", "11px");
                $("a#searchRegistration").click(function() {
                     $("#registrationSummary").get(0).setAttribute('action', 'search.htm');
                     $("#registrationSummary").submit();
                });
            });
	    });
    </script>
</head>
<body>
<h2 align="center">Participant Summary</h2>

<table align="center" cellspacing="2" cellpadding="2" width="50%">
    <tr>
		<td><spring:message code="label.id"/></td>
		<td><b><c:out value="${registeredParticipant.registration.id}"/></b></td>
	</tr>
    <tr>
		<td><spring:message code="label.name"/></td>
		<td><c:out value="${registeredParticipant.registration.participant.name}"/></td>
		<td><spring:message code="label.mobile"/></td>
		<td><c:out value="${registeredParticipant.registration.participant.mobile}"/></td>
	</tr>
	<tr>
		<td><spring:message code="label.email"/></td>
		<td><c:out value="${registeredParticipant.registration.participant.email}"/></td>
		<td><spring:message code="label.home"/></td>
		<td><c:out value="${registeredParticipant.registration.participant.home}"/></td>
	</tr>
	<tr>
		<td><spring:message code="label.foundation"/></td>
		<td><c:out value="${registeredParticipant.registration.foundation.shortName}"/></td>
	</tr>
	<tr>
		<td><spring:message code="label.eventId"/></td>
		<td><c:out value="${registeredParticipant.registration.event.name}"/></td>
        <td><spring:message code="label.review"/></td>
        <td><c:out value="${registeredParticipant.registration.review}"/></td>
	</tr>
	<tr>
		<td><spring:message code="label.courseType"/></td>
		<td><c:out value="${registeredParticipant.registration.courseType.shortName}"/></td>
	</tr>
    <tr>
        <td><spring:message code="label.foodCoupon"/></td>
        <td><c:out value="${registeredParticipant.registration.foodCoupon}"/></td>
        <td><spring:message code="label.eventKit"/></td>
        <td><c:out value="${registeredParticipant.registration.eventKit}"/></td>
    </tr>
    <tr>
        <td><spring:message code="label.application"/></td>
        <td><c:out value="${registeredParticipant.registration.application}"/></td>
        <td><spring:message code="label.certificates"/></td>
        <td><c:out value="${registeredParticipant.registration.certificates}"/></td>
    </tr>
    <tr>
        <td><spring:message code="label.amountPayable"/></td>
        <td><c:out value="${registeredParticipant.registration.amountPayable}"/></td>
        <td><spring:message code="label.amountDue"/></td>
        <td><c:out value="${registeredParticipant.registration.amountDue}"/></td>
    </tr>
    </table>
    <table cellspacing="1" cellpadding="1" width="100%">
    <c:if  test="${registeredParticipant.registration.eventKit}">
    <c:if  test="${!empty registeredParticipant.registration.seats}">
        <tr align="left">
            <td>
                <b>Seats:</b>
            </td>
        </tr>
        <tr> <td>
            <c:if test="${registeredParticipant.displaySeat}" >
            <table class="data" border="1" cellpadding="1" cellspacing="1" width="100%">
            <tr>
                <td>Seat No</td>
            </tr>
            <c:if  test="${!empty registeredParticipant.registration.seats}">
                <c:forEach items="${registeredParticipant.registration.seats}" var="seat">
                    <c:if  test="${seat.seat != null}">
                    <tr style="font-size:24px;color:#ff0000;font-weight:bold;">
                        <td>
                            <c:out value="${seat.levelName}"/>&nbsp;-&nbsp;<c:out value="${seat.alpha}"/>&nbsp;<c:out value="${seat.seat}"/>
                        </td>
                    </tr>
                    </c:if>
                </c:forEach>
            </c:if>
            </table>
            </c:if>
            </td>
        </tr>
    </c:if>
    </c:if>
    <tr><td>&nbsp;<BR></td></tr>
    <c:if  test="${!empty registeredParticipant.registration.historyRecords}">
        <tr align="left">
            <td>
                <b>Comments:</b>
            </td>
        </tr>
        <tr>
            <table class="data" border="1" cellpadding="1" cellspacing="1" width="100%">
            <tr>
                <td>Prepared By</td>
                <td>Time Created</td>
                <td>Comment</td>
            </tr>
            <c:forEach items="${registeredParticipant.registration.historyRecords}" var="historyRecord">
                <tr>
                    <td><c:out value="${historyRecord.preparedBy}"/> </td>
                    <td><c:out value="${historyRecord.timeCreated}"/></td>
                    <td><c:out value="${historyRecord.comment}"/></td>
                </tr>
            </c:forEach>
            </table>
        </tr>
    </c:if>
    <tr><td>&nbsp;<BR></td></tr>
	<tr align="center">
		<td align="center">
            <form id="registrationSummary" method="post" action="">
                <a id="nextRegistration" href="#">Next Registration</a>
                <a id="searchRegistration" href="#">Search Registrations</a>
            </form>
		</td>
	</tr>
</table>
<mytags:footer/>
</body>
</html>
