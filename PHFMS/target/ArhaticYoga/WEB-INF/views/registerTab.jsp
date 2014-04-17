<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<html>
<head>
	<title>Arhatic Yoga Retreat - Search</title>
    <mytags:style/>
    <mytags:menu/>
    <script type="text/javascript">
        $(document).ready(function(){
            $(function() {
                $("#tabs").tabs();
                $("#tabs").css("min-height", "400px");
                $("#tabs").css("resize", "none");
                $("#tabs").css("font-size", "11px");

                $("a#submit").button();
                $("a#submit").css("font-size", "11px");
                $("a#submit").click(function() {
                     $("#registeredParticipant").get(0).setAttribute('action', 'addRegistration.htm');
                     $("#registeredParticipant").submit();
                });

                $("a#showPayments").button();
                $("a#showPayments").css("font-size", "11px");
                $("a#showPayments").click(function() {
                     $("#registeredParticipant").get(0).setAttribute('action', 'showPayments.htm');
                     $("#registeredParticipant").submit();
                });

                $("a#cancelRegistration").button();
                $("a#cancelRegistration").css("font-size", "11px");
                $("a#cancelRegistration").click(function() {
                     $("#registeredParticipant").get(0).setAttribute('action', 'cancelRegistration.htm');
                     $("#registeredParticipant").submit();
                });

                $("a#onHoldRegistration").button();
                $("a#onHoldRegistration").css("font-size", "11px");
                $("a#onHoldRegistration").click(function() {
                     $("#registeredParticipant").get(0).setAttribute('action', 'onHoldRegistration.htm');
                     $("#registeredParticipant").submit();
                });

                $("a#changeToRegistered").button();
                $("a#changeToRegistered").css("font-size", "11px");
                $("a#changeToRegistered").click(function() {
                     $("#registeredParticipant").get(0).setAttribute('action', 'changeToRegistered.htm');
                     $("#registeredParticipant").submit();
                });

                $("a#replaceRegistration").button();
                $("a#replaceRegistration").css("font-size", "11px");
                $("a#replaceRegistration").click(function() {
                     $("#registeredParticipant").get(0).setAttribute('action', 'showReplaceRegistration.htm');
                     $("#registeredParticipant").submit();
                });

                $("a#back").button();
                $("a#back").css("font-size", "11px");
                $("a#back").click(function() {
                     $("#registeredParticipant").get(0).setAttribute('action', 'search.htm');
                     $("#registeredParticipant").submit();
                });

            });

            function getEventFees(){
                if ($("select#eventId").val() == 'NONE'){
                   $("#registeredParticipant input[name='registration.amountPayable']").val('0');
                   $("select#eventFeeId").html('<option value="NONE"> --- Select --- </option>');
                }
                else {
                    $.getJSON(
                        "getAllEventFees.htm",
                        {eventId: $("select#eventId").val()},
                        function(data) {
                            var options = '<option value="-1"> --- Select --- </option>';
                            var len = data.length;
                            for(var i=0; i<len; i++){
                                options +=  '<option value="' + data[i].id + '">' + data[i].value + '</option>';
                            }
                        $("select#eventFeeId").html(options);
                        $("#registeredParticipant input[name='registration.amountPayable']").val('0');
                        }
                    );
                }
            }

            function populateEventFee(){
				if ($("#registeredParticipant input[name='access']").val() == 'true'){

				}
                else if ($("select#eventFeeId").val() == '-1'){
                   $("#registeredParticipant input[name='registration.amountPayable']").val('0');
                }
                else {
                    $.getJSON(
                        "fetchEventFee.htm",
                        {eventFeeId: $("select#eventFeeId").val()},
                        function(data) {
                            $("#registeredParticipant input[name='registration.amountPayable']").val(data.value);
                        }
                    );
                }
            }

            $(document).ready(function() {
                $("select#eventId").change(function()
                {
                    getEventFees();
                });

                $("select#eventFeeId").change(function()
                {
                    populateEventFee();
                });

                $('#tabs').tabs({ selected: 1 });

                $("#registeredParticipant input[name='registration.foodCoupon']").click(function () {
                     if ($("#registeredParticipant input[name='registration.foodCoupon']").prop('checked') &&
                        $("#registeredParticipant input[name='registration.eventKit']").prop('checked')) {
                            $("div#seatsDisplay").show();
                    }
                });

                $("#registeredParticipant input[name='registration.eventKit']").click(function () {
                     if ($("#registeredParticipant input[name='registration.foodCoupon']").prop('checked') &&
                        $("#registeredParticipant input[name='registration.eventKit']").prop('checked')) {
                            $("div#seatsDisplay").show();
                    }
                });

                if ($("#registeredParticipant input[name='registration.foodCoupon']").prop('checked') &&
                        $("#registeredParticipant input[name='registration.eventKit']").prop('checked')) {
                    $("div#seatsDisplay").show();
                }
            });
	    });
    </script>
    <style>
	.error {
		color: #ff0000;
	}

	.errorblock {
		color: #000;
		background-color: #ffEEEE;
		border: 3px solid #ff0000;
		padding: 8px;
		margin: 16px;
	}
	</style>
</head>
<body>
<form:form method="post" action="addRegistration.htm" commandName="registeredParticipant">
<form:errors path="*" cssClass="errorblock" element="div"/>

	<%-- Start : common variables that can be used to control access at field level.  Can be considered to be moved a level up. --%>
	<c:if test="${user.access.regVolunteer}" var="isRegVolunteer"/>
	<c:if test="${user.access.infoVolunteer}" var="isInfoVolunteer"/>
	<c:if test="${user.access.spotRegVolunteer}" var="isSpotRegVolunteer"/>
	<c:if test="${user.access.admin}" var="isAdmin"/>
	<%-- End --%>

    <form:hidden path="action"/>
    <form:hidden path="participant.id"/>
    <form:hidden path="registration.id"/>
    <form:hidden path="registration.status"/>
    <form:hidden path="registration.localEventKitStatus"/>
    <form:hidden path="registration.refOrder"/>
    <form:hidden path="registration.totalAmountPaid"/>
    <input id="access" name="access" type="hidden" value="<c:out value="${isInfoVolunteer || isRegVolunteer}"/>"/>
    <table width="100%" cellpadding="1" cellspacing="1">
        <tr>
            <td align="left" style="font-size:18px">
                <c:out value="${registeredParticipant.participant.name}"/> - <c:out value="${registeredParticipant.registration.event.name}"/>
            </td>
            <td align="right" style="font-size:18px">
                 <c:out value="${registeredParticipant.registration.status}"/>
            </td>
        </tr>
    </table>
    <div id="tabs">
        <ul>
            <li><a href="#tabs-1">Participant</a></li>
            <li><a href="#tabs-2">Registration</a></li>
            <li><a href="#tabs-3">Payments</a></li>
            <li><a href="#tabs-4">History</a></li>
        </ul>
        <div id="tabs-1">
            <table>
                <tr>
                    <td><form:label path="participant.name"><spring:message code="label.name"/></form:label></td>
                    <td><form:input path="participant.name" size="50" readonly="${isRegVolunteer || isInfoVolunteer}"/></td>
                </tr>
                <tr>
                    <td><form:label path="participant.mobile"><spring:message code="label.mobile"/></form:label></td>
                    <td><form:input path="participant.mobile" readonly="${isRegVolunteer || isInfoVolunteer}"/></td>
                </tr>
                <tr>
                    <td><form:label path="participant.home"><spring:message code="label.home"/></form:label></td>
                    <td><form:input path="participant.home" readonly="${isRegVolunteer || isInfoVolunteer}"/></td>
                </tr>
                <tr>
                    <td><form:label path="participant.email"><spring:message code="label.email"/></form:label></td>
                    <td><form:input path="participant.email" readonly="${isRegVolunteer || isInfoVolunteer}"/></td>
                </tr>
                <tr>
                    <td><form:label path="registration.foundation"><spring:message code="label.foundation"/></form:label></td>
                    <td>
                    	<c:choose>
	   						<c:when test="${isRegVolunteer || isInfoVolunteer}">
		   						<form:select path="registration.foundation" onfocus="this.defaultIndex=this.selectedIndex;" onchange="this.selectedIndex=this.defaultIndex;">
		                            <form:option value="" label="--- Select ---"/>
		                            <form:options items="${allFoundations}"/>
		                        </form:select>
	                        </c:when>
	   						<c:otherwise>
	   							<form:select path="registration.foundation">
	                            	<form:option value="" label="--- Select ---"/>
	                            	<form:options items="${allFoundations}"/>
	                        	</form:select>
	   						</c:otherwise>
   						</c:choose>
                    </td>
                </tr>
                <tr>
                    <td><form:label path="participant.vip"><spring:message code="label.vip"/></form:label></td>
                    <td><form:checkbox path="participant.vip" readonly="${isRegVolunteer || isInfoVolunteer}" onclick="return ${isSpotRegVolunteer || isAdmin}"/></td>
                </tr>
                <tr>
                    <td><form:label path="participant.vipDesc"><spring:message code="label.vipDesc"/></form:label></td>
                    <td><form:input path="participant.vipDesc" readonly="${isRegVolunteer || isInfoVolunteer}"/></td>
                </tr>
            </table>
        </div>
        <div id="tabs-2">
			<table width="80%" cellspacing="1" cellpadding="1">
				<tr>
					<td>
                        <table>
                            <tr>
                                <td><form:label path="eventId"><spring:message code="label.eventId"/></form:label></td>
                                <td>
                                	<c:choose>
										<c:when test="${isInfoVolunteer || isRegVolunteer}">
											<form:select path="eventId" onfocus="this.defaultIndex=this.selectedIndex;" onchange="this.selectedIndex=this.defaultIndex;">
		                                        <form:option value="NONE" label="--- Select ---"/>
		                                        <form:options items="${allEvents}" />
		                                    </form:select>
										</c:when>
										<c:otherwise>
											<form:select path="eventId">
		                                        <form:option value="NONE" label="--- Select ---"/>
		                                        <form:options items="${allEvents}" />
		                                    </form:select>										
										</c:otherwise>
									</c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td><form:label path="registration.review"><spring:message code="label.review"/></form:label></td>
                                <td><form:checkbox path="registration.review" onclick="return ${isSpotRegVolunteer || isAdmin}"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="registration.courseType.shortName"><spring:message code="label.courseType"/></form:label></td>
                                <td>
                                	<c:choose>
										<c:when test="${isInfoVolunteer || isRegVolunteer}">
		                                    <form:select path="registration.courseType.shortName" onfocus="this.defaultIndex=this.selectedIndex;" onchange="this.selectedIndex=this.defaultIndex;">
		                                        <form:option value="NONE" label="--- Select ---"/>
		                                        <form:options items="${allParticipantLevels}" />
		                                    </form:select>										
										</c:when>
										<c:otherwise>
		                                    <form:select path="registration.courseType.shortName">
		                                        <form:option value="NONE" label="--- Select ---"/>
		                                        <form:options items="${allParticipantLevels}" />
		                                    </form:select>										
										</c:otherwise>
									</c:choose>

                                </td>
                            </tr>
                            <tr>
                                <td><form:label path="eventFeeId"><spring:message code="label.eventFeeId"/></form:label></td>
                                <td>
									<c:choose>
										<c:when test="${isInfoVolunteer || isRegVolunteer}">
		                                    <form:select path="eventFeeId" onfocus="this.defaultIndex=this.selectedIndex;" onchange="this.selectedIndex=this.defaultIndex;">
		                                        <form:option value="-1" label="--- Select ---"/>
		                                        <form:options items="${allEventFees}" itemValue="id" itemLabel="value"/>
		                                    </form:select>										
										</c:when>
										<c:otherwise>
		                                    <form:select path="eventFeeId">
		                                        <form:option value="-1" label="--- Select ---"/>
		                                        <form:options items="${allEventFees}" itemValue="id" itemLabel="value"/>
		                                    </form:select>										
										</c:otherwise>
									</c:choose>                                
                                </td>
                            </tr>
                            <tr>
                                <td><form:label path="registration.amountPayable"><spring:message code="label.amountPayable"/></form:label></td>
                                <td><form:input path="registration.amountPayable" readonly="${isInfoVolunteer || isRegVolunteer}"/></td>
                            </tr>
                            <tr>
                                <td><spring:message code="label.totalAmountPaid"/></td>
                                <td><c:out value="${registeredParticipant.registration.totalAmountPaid}"/></td>
                            </tr>
                            <tr>
                                <td><spring:message code="label.amountDue"/></td>
                                <td><c:out value="${registeredParticipant.registration.amountDue}"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="registration.reference"><spring:message code="label.reference"/></form:label></td>
                                <td>
									<c:choose>
										<c:when test="${isInfoVolunteer || isRegVolunteer}">
		                                    <form:select path="registration.reference" onfocus="this.defaultIndex=this.selectedIndex;" onchange="this.selectedIndex=this.defaultIndex;">
		                                        <form:option value="" label="--- Select ---"/>
		                                        <form:options items="${allReferenceGroups}" />
		                                    </form:select>
										</c:when>
										<c:otherwise>
		                                    <form:select path="registration.reference">
		                                        <form:option value="" label="--- Select ---"/>
		                                        <form:options items="${allReferenceGroups}" />
		                                    </form:select>										
										</c:otherwise>
									</c:choose>                                

                                </td>
                            </tr>
						</table>	
					</td>
					<td>	
						<table>
                            <tr>
                                <td><form:label path="registration.foodCoupon"><spring:message code="label.foodCoupon"/></form:label></td>
                                <td><form:checkbox path="registration.foodCoupon" onclick="return ${!isInfoVolunteer}"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="registration.eventKit"><spring:message code="label.eventKit"/></form:label></td>
                                <td><form:checkbox path="registration.eventKit" onclick="return ${!isInfoVolunteer}"/></td>
                            </tr>
                            <c:if test="${registeredParticipant.displaySeat}" >
							<tr style="font-size:18px;color:#ff0000;">
							    <td>
                                    <div id="seatsDisplay" style="display:none;">
                                        <c:if  test="${!empty registeredParticipant.registration.seats}">
                                            <c:forEach items="${registeredParticipant.registration.seats}" var="seat">
                                                <c:if  test="${seat.seat != null}">
                                                    <ul class="tooltipBullet">
                                                         <li><c:out value="${seat.levelName}"/>&nbsp;-&nbsp;<c:out value="${seat.alpha}"/>&nbsp;<c:out value="${seat.seat}"/></li>
                                                    </ul>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </div>
							    </td>
							</tr>
							</c:if>
                            <tr>
                                <td><form:label path="registration.application"><spring:message code="label.application"/></form:label></td>
                                <td><form:checkbox path="registration.application" onclick="return ${isSpotRegVolunteer || isAdmin }"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="registration.certificates"><spring:message code="label.certificates"/></form:label></td>
                                <td><form:checkbox path="registration.certificates" onclick="return ${isSpotRegVolunteer || isAdmin }"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="currentHistoryRecord.comment"><spring:message code="label.comments"/></form:label></td>
                                <td><form:textarea path="currentHistoryRecord.comment" rows="5" cols="30"/></td>
                            </tr>
						</table>
					</td>		
				</tr>
			</table>
        </div>
        <div id="tabs-3">
			<table>
				<tr>
					<c:if  test="${!empty registeredParticipant.registration.payments}">
						<table class="data" border="1" cellpadding="1" cellspacing="1" width="100%">
							<tr>
								<td>Prepared By</td>
								<td>Time Created</td>
								<td><spring:message code="label.amountPaid"/></td>
								<td><spring:message code="label.receiptInfo"/></td>
								<td><spring:message code="label.receiptDate"/></td>
								<td><spring:message code="label.mode"/></td>
								<td><spring:message code="label.pdcNotClear"/></td>
								<td><spring:message code="label.pdc"/></td>
								<td><spring:message code="label.pdcDate"/></td>
								<td><spring:message code="label.remarks"/></td>
							</tr>
							<c:forEach items="${registeredParticipant.registration.payments}" var="payment">
								<tr>
									<td><c:out value="${payment.preparedBy}"/> </td>
									<td><c:out value="${payment.timeCreated}"/></td>
									<td><c:out value="${payment.amountPaid}"/></td>
									<td><c:out value="${payment.receiptInfo}"/></td>
									<td><c:out value="${payment.receiptDate}"/></td>
									<td><c:out value="${payment.mode}"/></td>
									<td><c:out value="${payment.pdcNotClear}"/></td>
									<td><c:out value="${payment.pdc}"/></td>
									<td><c:out value="${payment.pdcDate}"/></td>
									<td><c:out value="${payment.remarks}"/></td>
								</tr>
							</c:forEach>
						</table>
					</c:if>
				</tr>
			</table>
        </div>
        <div id="tabs-4">
            <c:if  test="${!empty registeredParticipant.registration.historyRecords}">
				<table class="data" border="1" cellpadding="1" cellspacing="1" width="100%">
					<tr>
						<td>Prepared By</td>
						<td>Time Created</td>
						<td>Comment</td>
					</tr>
					<c:forEach items="${registeredParticipant.registration.historyRecords}" var="historyRecord">
						<c:if  test="${!empty historyRecord.comment}">
							<tr>
								<td><c:out value="${historyRecord.preparedBy}"/> </td>
								<td><c:out value="${historyRecord.timeCreated}"/></td>
								<td><c:out value="${historyRecord.comment}"/></td>
							</tr>
						</c:if>
					</c:forEach>
				</table>
            </c:if>
        </div>
    </div>
    <table width="100%" cellpadding="2" cellspacing="2" align="center">
        <tr>
            <td>&nbsp;</td>
        </tr>
    </table>
    <div id="button">
        <table width="100%" cellpadding="2" cellspacing="2">
            <tr style="background-color:#DFDFDF;">
                <td align="center">
                    <c:choose>
                        <c:when test="${user.access.admin}" >
                            <a id="submit" href="#"><c:out value="${registeredParticipant.action}"/></a>
                        </c:when>
                        <c:when test="${!registeredParticipant.registration.eventKit}" >
                            <a id="submit" href="#"><c:out value="${registeredParticipant.action}"/></a>
                        </c:when>
                    </c:choose>
		            <c:if test="${isAdmin || isSpotRegVolunteer}" >
                        <a id="showPayments" href="#">Payments</a>
                    </c:if>
                    <c:if test="${isAdmin}" >
	                    <a id="cancelRegistration" href="#">Cancel Registration</a>
	                    <a id="onHoldRegistration" href="#">On Hold</a>
	                    <a id="changeToRegistered" href="#">Change To Registered</a>
	                    <a id="replaceRegistration" href="#">Replace</a>
                    </c:if>
                    <a id="back" href="#">Back</a>
                </td>
            </tr>
        </table>
    </div>
</form:form>
<mytags:footer/>
</body>
</html>
