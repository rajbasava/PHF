<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<html>
<head>
    <title>Arhatic Yoga Retreat - Search Registrations</title>
    <mytags:style/>
    <mytags:menu/>
    <script type="text/javascript">
        $(function() {
            var moveLeft = 250;
            var moveDown = 200;
            $("a#submit").button();
            $("a#submit").css("font-size", "11px");
            $("a#submit").click(function() {
                 $("#registrationCriteria").get(0).setAttribute('action', 'list.htm');
                 $("#registrationCriteria").submit();
            });
            $("#results").flexigrid({
					colModel : [
                        {display: '<spring:message code="label.id"/>', width : 18, align: 'left'},
                        {display: '<spring:message code="label.name"/>', width : 200, align: 'left'},
                        {display: '<spring:message code="label.mobile"/>', width : 75, align: 'left'},
                        {display: '<spring:message code="label.foundation"/>', width : 350, align: 'left'},
                        {display: 'Event', width : 150, align: 'left'},
                        {display: '<spring:message code="label.courseType"/>', width : 50, align: 'left'},
                        {display: 'Amount Paid', width : 60, align: 'left'},
                        {display: '<spring:message code="label.amountDue"/>', width : 60, align: 'left'},
                        {display: 'Coupon', width : 50, align: 'left'},
                        {display: 'Kit', width : 40, align: 'left'},
                        {display: '<spring:message code="label.status"/>', width : 50, align: 'left'},
                        {display: '<spring:message code="label.email"/>', width : 100, align: 'left'}
					],
					useRp: true,
					rp: 10,
					showTableToggleBtn: false,
					resizable: false,
					height: 275,
					width: 1325,
					singleSelect: true
			});

            $(document).ready(function() {

                $("#registrationCriteria input[name='fromRegistrationDate']").datepicker({
                    showOn: 'button',
                    dateFormat: 'dd/mm/yy',
                    buttonImageOnly: true,
                    buttonImage: '<c:url value="/resources/img/calendar.gif"/>'
                });

                $("#registrationCriteria input[name='toRegistrationDate']").datepicker({
                    showOn: 'button',
                    dateFormat: 'dd/mm/yy',
                    buttonImageOnly: true,
                    buttonImage: '<c:url value="/resources/img/calendar.gif"/>'
                });

            });
        });
    </script>
</head>
<body>
<div class="formdata">
<div class="formtitle">Search Registrations</div>
<div class="formbody">
<form:form method="post" action="list.htm" commandName="registrationCriteria">
<table align="center" >
	<tr valign="top">
		<td>
			<table>
				<tr>
					<td ><form:label path="name"><spring:message code="label.name"/></form:label></td>
					<td ><form:input path="name" /></td>
				</tr>
				<tr>
					<td ><form:label path="email"><spring:message code="label.email"/></form:label></td>
					<td ><form:input path="email" /></td>
				</tr>
				<tr>
					<td ><form:label path="mobile"><spring:message code="label.mobile"/></form:label></td>
					<td ><form:input path="mobile" /></td>
				</tr>
				<tr>
					<td ><form:label path="eventId"><spring:message code="label.eventId"/></form:label></td>
					<td >
						<form:select path="eventId">
							<form:option value="" label="--- Select ---"/>
							<form:options items="${allEvents}" />
						</form:select>
					</td>
				</tr>
				<tr>
					<td ><form:label path="foodCoupon"><spring:message code="label.foodCoupon"/></form:label></td>
					<td >
						<form:radiobutton path="foodCoupon" value="true"/>True &nbsp;
						<form:radiobutton path="foodCoupon" value="false"/>False &nbsp;
						<form:radiobutton path="foodCoupon" value=""/>Both
					</td>
				</tr>
				<tr>
					<td ><spring:message code="label.registrationDate"/></td>
					<td >
						<table width="100%">
							<tr>
								<td>From: </td><td><form:input path="fromRegistrationDate"/></td>
							</tr>
							<tr>
								<td>To: </td><td><form:input path="toRegistrationDate"/></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td ><form:label path="foundation"><spring:message code="label.foundation"/></form:label></td>
					<td >
						<form:select path="foundation">
						<form:option value="" label="--- Select ---"/>
						<form:options items="${allFoundations}" />
						</form:select>
					</td>
				</tr>
					<td ><form:label path="courseTypeId"><spring:message code="label.courseType"/></form:label></td>
					<td >
						<form:select path="courseTypeId">
							<form:option value="" label="--- Select ---"/>
							<form:options items="${allParticipantCourseTypes}" />
						</form:select>
					</td>
				<tr>
					<td ><form:label path="vip"><spring:message code="label.vip"/></form:label></td>
					<td ><form:checkbox path="vip"/></td>
				</tr>
				<tr>
					<td ><form:label path="eventKit"><spring:message code="label.eventKit"/></form:label></td>
					<td >
						<form:radiobutton path="eventKit" value="true"/>True &nbsp;
						<form:radiobutton path="eventKit" value="false"/>False &nbsp;
						<form:radiobutton path="eventKit" value=""/>Both
					</td>
				</tr>
                <tr>
                    <td ><form:label path="status"><spring:message code="label.status"/></form:label></td>
                    <td >
                        <form:select path="status">
                            <form:option value="" label="--- Select ---"/>
                            <form:options items="${allStatuses}" />
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td ><form:label path="reference"><spring:message code="label.reference"/></form:label></td>
                    <td >
                        <form:select path="reference">
                            <form:option value="" label="--- Select ---"/>
                            <form:options items="${allReferenceGroups}" />
                        </form:select>
                    </td>
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

<c:if  test="${!empty registrationList}">
<c:set var="registrationListSize" value="${fn:length(registrationList)}"/>
<table width="100%" cellpadding="2" cellspacing="2">
    <tr>
        <td>
            <table id="results">
                <tbody>
                <c:forEach items="${registrationList}" var="registration">
                    <tr>
                        <td><c:out value="${registration.id}"/></td>
                        <td class="YLink">
                            <form id="updatePart<c:out value="${registration.id}"/>" method="post" action="updateRegistration.htm">
                                <input type="hidden" name="registrationId" value="<c:out value="${registration.id}"/>" />
                                <a href="#" onclick="document.getElementById('updatePart<c:out value="${registration.id}"/>').submit();">
                                    <c:out value="${registration.participant.name}"/>
                                </a>
                            </form>
                        </td>

                        <td><c:out value="${registration.participant.mobile}"/></td>
                        <td><c:out value="${registration.foundation.shortName}"/></td>
                        <td><c:out value="${registration.event.name}"/></td>
                        <td><c:out value="${registration.courseType.shortName}"/></td>
                        <td>
                            <c:out value="${registration.totalAmountPaid}"/>
                        </td>
                        <td><c:out value="${registration.amountDue}"/></td>
                        <td><c:out value="${registration.foodCoupon}"/></td>
                        <c:if  test="${registration.eventKit}">
                            <td style="font-weight:bold; color:green; font-size:20px;">
                                <c:out value="${registration.eventKit}"/>
                            </td>
                        </c:if>
                        <c:if  test="${!registration.eventKit}">
                            <td width="6%">
                                <c:out value="${registration.foodCoupon}"/>
                            </td>
                        </c:if>
                        <td><c:out value="${registration.status}"/></td>
                        <td><c:out value="${registration.participant.email}"/></td>
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