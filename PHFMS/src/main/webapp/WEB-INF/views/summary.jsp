<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<html>
<head>
	<title>Arhatic Yoga Retreat - Registered Participant Summary</title>
    <mytags:style/>
    <mytags:menu/>
</head>
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
            <c:forEach items="${registeredParticipant.registration.seats}" var="seat">
                <c:if  test="${seat.level != null}">
                    <tr style="font-size:20px;color:#ff0000;">
                        <td>
                            <c:if  test="${!empty registeredParticipant.registration.seats}">
                                <c:forEach items="${registeredParticipant.registration.seats}" var="seat">
                                    <c:if  test="${seat.seat != null}">
                                        <c:out value="${seat.levelName}"/>&nbsp;-&nbsp;<c:out value="${seat.alpha}"/>&nbsp;<c:out value="${seat.seat}"/>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
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
            <form method="post" action="register.htm">
			    <input type="submit" value="<spring:message code="label.nextRegistration"/>"/>
            </form>
		</td>
	</tr>
</table>
<mytags:footer/>
</body>
</html>
