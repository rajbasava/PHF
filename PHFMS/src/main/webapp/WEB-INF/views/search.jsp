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
                        {display: 'Reg.Id', width : 40, align: 'left'},
                        {display: '<spring:message code="label.name"/>', width : 250, align: 'left'},
                        {display: '<spring:message code="label.mobile"/>', width : 100, align: 'left'},
                        {display: '<spring:message code="label.email"/>', width : 200, align: 'left'},
                        {display: 'Country/State', width : 190, align: 'left'},
                        {display: '<spring:message code="label.event"/>', width : 150, align: 'left'},
                        {display: 'Details', width : 200, align: 'left'},
                        {display: 'Action', width : 75, align: 'left'}
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
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td><form:label path="foundationId">Country/State</form:label></td>
					<td>
						<form:select path="foundationId">
                            <form:option value="" label="--- Select ---"/>
                            <form:options items="${allFoundations}" />
						</form:select>
					</td>
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
					<td ><form:label path="id">Reg. Id</form:label></td>
					<td ><form:input path="id" /></td>
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
                        <td><c:out value="${registration.participant.email}"/></td>
                        <td><c:out value="${registration.foundation.shortName}"/></td>
                        <td><c:out value="${registration.event.name}"/></td>
                        <td>
							<div>
								<p style="display:inline;font-weight:bold; color:lightblue; font-size:16px; border:1px blue solid; border-radius: 5px; padding: 2px">
								    <c:out value="${registration.getLevelName()}"/>
                                </p>
							    <c:if  test="${registration.isRegistered()}">
								    <p style="display:inline;font-weight:bold; color:green; font-size:16px; border:1px green solid; border-radius: 5px; padding:2px; margin-left:4px;">R</p>
								</c:if>
                                <c:if  test="${registration.isAttend()}">
                                    <p style="display:inline;font-weight:bold; color:lightgreen; font-size:16px; border:1px green solid; border-radius: 5px; padding: 2px; background-color: lightgreen; margin-right:4px;">
                                        &nbsp;
                                    </p>
								</c:if>
							    <c:if  test="${registration.isAmountDue()}">
								    <p style="display:inline;font-weight:bold; color:red; font-size:16px; border:1px red solid; border-radius: 5px; padding: 2px">$</p>
								</c:if>
								<c:if  test="${registration.isJainFood()}">
								    <p style="display:inline;font-weight:bold; color:violet; font-size:16px; border:1px violet solid; border-radius: 5px; padding: 2px">J</p>
								</c:if>
								<c:if  test="${registration.isVIP()}">
								    <p style="display:inline;font-weight:bold; color:gold; font-size:16px; border:1px gold solid; border-radius: 5px; padding: 2px">&#9733;</p>
								</c:if>
							</div>
                        </td>
                        <td>
                            <div>
                                <form id="attendPart<c:out value="${registration.id}"/>" method="post" action="attendRegistration.htm">
                                    <input type="hidden" name="registrationId" value="<c:out value="${registration.id}"/>" />
                                        <c:choose>
                                            <c:when test="${user.access.infoVolunteer || registration.isAmountDue() || registration.isAttend()}">
                                                <input type="button" value="Attend" onclick="document.getElementById('attendPart<c:out value="${registration.id}"/>').submit();" disabled/>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="button" value="Attend" onclick="document.getElementById('attendPart<c:out value="${registration.id}"/>').submit();" />
                                            </c:otherwise>
                                        </c:choose>
                                </form>
                            </div>
                        </td>
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