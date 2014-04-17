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

            $("a#allocateSeats").button();
            $("a#allocateSeats").css("font-size", "11px");
            $("a#allocateSeats").click(function() {
                 $("#modifyEvent").get(0).setAttribute('action', 'allocateSeats.htm');
                 $("#modifyEvent").submit();
            });

            $("a#addEventFee").button();
            $("a#addEventFee").css("font-size", "11px");
            $("a#addEventFee").click(function() {
                 $("#modifyEvent").get(0).setAttribute('action', 'eventFee.htm');
                 $("#modifyEvent").submit();
            });

            $("a#showKits").button();
            $("a#showKits").css("font-size", "11px");
            $("a#showKits").click(function() {
                 $("#modifyEvent").get(0).setAttribute('action', 'showKitsUI.htm');
                 $("#modifyEvent").submit();
            });

            $("a#showEventDetail").button();
            $("a#showEventDetail").css("font-size", "11px");
            $("a#showEventDetail").click(function() {
                 $("#modifyEvent").get(0).setAttribute('action', 'showEventDetailUI.htm');
                 $("#modifyEvent").submit();
            });

            $("a#deleteEvent").button();
            $("a#deleteEvent").css("font-size", "11px");
            $("a#deleteEvent").click(function() {
                 $("#modifyEvent").get(0).setAttribute('action', 'deleteEvent.htm');
                 $("#modifyEvent").submit();
            });

            $("a#exportSeats").button();
            $("a#exportSeats").css("font-size", "11px");
            $("a#exportSeats").click(function() {
                 $("#modifyEvent").get(0).setAttribute('action', 'exportSeats.htm');
                 $("#modifyEvent").submit();
            });

            $("a#editEvent").button();
            $("a#editEvent").css("font-size", "11px");
            $("a#editEvent").click(function() {
                 $("#modifyEvent").get(0).setAttribute('action', 'showEventDetails.htm');
                 $("#modifyEvent").submit();
            });

            $("#results").flexigrid({
                    colModel : [
                        {display: '<spring:message code="label.name"/>', width : 250, align: 'left'},
                        {display: '<spring:message code="label.cutOffDate"/>', width : 250, align: 'left'},
                        {display: '<spring:message code="label.amount"/>', width : 250, align: 'left'},
                        {display: '<spring:message code="label.review"/>', width : 250, align: 'left'},
                        {display: ' ', width : 260, align: 'left'}
                    ],
                    useRp: true,
                    rp: 10,
                    showTableToggleBtn: false,
                    resizable: false,
                    height: 175,
                    width: 1325,
                    singleSelect: true
            });

        });
    </script>
</head>
<body>

<c:choose>
    <c:when test="${isEdit}">
        <jsp:include page="event.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="eventSummary.jsp"/>
    </c:otherwise>
</c:choose>

<c:if  test="${!empty eventFeeList}">
<table width="100%">
    <tr style="background-color:#E8E8E8;">
        <td>Event Fees</td>
    </tr>
    <tr>
        <td>
            <table id="results">
                <tbody>
                    <c:forEach items="${eventFeeList}" var="eventFee">
                        <tr>
                            <td><c:out value="${eventFee.name}"/> </td>
                            <td><c:out value="${eventFee.cutOffDate}"/></td>
                            <td><c:out value="${eventFee.amount}"/> </td>
                            <td><c:out value="${eventFee.review}"/></td>
                            <td class="YLink">
                                <form id="delEventFee<c:out value="${eventFee.id}"/>" method="post" action="deleteEventFee.htm">
                                    <input type="hidden" name="eventFeeId" value="<c:out value="${eventFee.id}"/>" />
                                    <input type="hidden" name="eventId" value="<c:out value="${event.id}"/>" />
                                    <a href="#" onclick="document.getElementById('delEventFee<c:out value="${eventFee.id}"/>').submit();"><spring:message code="label.delete"/></a>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                <tbody>
            </table>
        </td>
    </tr>
</table>
</c:if>

<div>
    <form id="modifyEvent" method="post" action="">
        <input type="hidden" name="eventId" value="<c:out value="${event.id}"/>" />
        <input type="hidden" name="isEdit" value="true" />
        <table width="100%" >
            <tr height="10px"><td>&nbsp;</td></tr>
            <tr style="background-color:#E8E8E8;">
                <td align="center" cellpadding="2px">
                    <a id="addEventFee" href="#">Add Event Fee</a>
                    <a id="showKits" href="#">Volunteer Kits and Status</a>
                    <a id="showEventDetail" href="#">Kits For Event</a>
                    <a id="allocateSeats" href="#">Allocate Seats</a>
                    <a id="exportSeats" href="#">Export Allocated Seats</a>
                    <a id="deleteEvent" href="#">Deactivate</a>
                    <a id="editEvent" href="#">Edit</a>
                </td>
            </tr>
        </table>
    </form>
</div>

<mytags:footer/>
</body>
</html>