<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<html>
<head>
	<title>Arhatic Yoga Retreat - Registration</title>
    <mytags:style/>
    <mytags:menu/>
    <script type="text/javascript">
        function getEventFees(){
            if ($("select#eventId").val() == ''){
               $("#registeredParticipant input[name='registration.amountPayable']").val('0');
               $("select#eventFeeId").html('<option value=""> --- Select --- </option>');
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
            if ($("select#eventFeeId").val() == '-1'){
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

            $("select#eventId").focus(function()
            {
                getEventFees();
            });

            getEventFees();

            $("select#eventFeeId").change(function()
            {
                populateEventFee();
            });

            $("#registeredParticipant input[name='currentPayment.receiptDate']").datepicker({
                showOn: 'button',
                dateFormat: 'dd/mm/yy',
                buttonImageOnly: true,
                buttonImage: '<c:url value="/resources/img/calendar.gif"/>'
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
<table width="100%" cellpadding="1" cellspacing="1">
    <tr>
        <td align="center" style="font-size:18px">
            User Registration Details
        </td>
    </tr>
    <tr><td>&nbsp;</td></tr>
</table>
<form:form method="post" action="addRegistration.htm" commandName="registeredParticipant">
<form:errors path="*" cssClass="errorblock" element="div" />
<table align="center" cellspacing="2" cellspacing="2" width="80%">
    <form:hidden path="participant.id" />
    <form:hidden path="registration.id" />
    <tr>
        <td>
            <table width="100%" cellpadding="1" cellspacing="1">
                <tr style="background-color:#E8E8E8;">
                    <td align="left"><b>Contact Details:</b></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="1" cellspacing="1">
                <tr>
                    <td width="60%">
                        <table width="100%" cellpadding="1" cellspacing="1">
                            <tr>
                                <td width="30%"><form:label path="participant.name"><spring:message code="label.name"/></form:label></td>
                                <td><form:input path="participant.name" size="50"/></td>
                            </tr>
                            <tr>
                                <td width="30%"><form:label path="participant.email"><spring:message code="label.email"/></form:label></td>
                                <td><form:input path="participant.email" /></td>
                            </tr>
                            <tr>
                                <td width="30%"><form:label path="registration.foundationId"><spring:message code="label.foundation"/></form:label></td>
                                <td>
                                    <form:select path="registration.foundationId" cssClass="drop-select">
                                        <form:option value="" label="--- Select ---"/>
                                        <form:options items="${allFoundations}" />
                                    </form:select>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table width="100%" cellpadding="1" cellspacing="1">
                            <tr>
                                <td width="30%"><form:label path="participant.mobile"><spring:message code="label.mobile"/></form:label></td>
                                <td><form:input path="participant.mobile" /></td>
                            </tr>
                            <tr>
                                <td width="30%"><form:label path="participant.home"><spring:message code="label.home"/></form:label></td>
                                <td><form:input path="participant.home" /></td>
                            </tr>
                            <tr>
                                <td width="30%"><form:label path="participant.vip"><spring:message code="label.vip"/></form:label></td>
                                <td><form:checkbox path="participant.vip"/></td>
                            </tr>
                            <tr>
                                <td width="30%"><form:label path="participant.vipDesc"><spring:message code="label.vipDesc"/></form:label></td>
                                <td><form:input path="participant.vipDesc"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="1" cellspacing="1">
                <tr style="background-color:#E8E8E8;">
                    <td align="left"><b>Event Details:</b></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="1" cellspacing="1">
                <tr>
                    <td width="60%">
                        <table width="100%" cellpadding="1" cellspacing="1">
                            <tr>
                                <td width="30%"><form:label path="eventId"><spring:message code="label.eventId"/></form:label></td>
                                <td>
                                    <form:select path="eventId">
                                        <form:option value="" label="--- Select ---"/>
                                        <form:options items="${allEvents}" />
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <td width="30%"><form:label path="registration.courseType.Id"><spring:message code="label.courseType"/></form:label></td>
                                <td>
                                    <form:select path="registration.courseType.Id">
                                        <form:option value="" label="--- Select ---"/>
                                        <form:options items="${allParticipantCourseTypes}" />
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <td width="30%"><form:label path="registration.review"><spring:message code="label.review"/></form:label></td>
                                <td><form:checkbox path="registration.review"/></td>
                            </tr>
                            <tr>
                                <td width="30%"><form:label path="registration.application"><spring:message code="label.application"/></form:label></td>
                                <td><form:checkbox path="registration.application"/></td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table width="100%" cellpadding="1" cellspacing="1">
                            <tr>
                                <td width="30%"><form:label path="registration.reference"><spring:message code="label.reference"/></form:label></td>
                                <td>
                                    <form:select path="registration.reference">
                                        <form:option value="" label="--- Select ---"/>
                                        <form:options items="${allReferenceGroups}" />
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <td width="30%"><form:label path="eventFeeId"><spring:message code="label.eventFeeId"/></form:label></td>
                                <td>
                                    <form:select path="eventFeeId">
                                        <form:option value="-1" label="--- Select ---"/>
                                        <form:options items="${allEventFees}" itemValue="id" itemLabel="value"/>
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <td width="30%"><form:label path="registration.amountPayable"><spring:message code="label.amountPayable"/></form:label></td>
                                <td><form:input path="registration.amountPayable"/></td>
                            </tr>
                            <tr>
                                <td width="30%"><form:label path="registration.certificates"><spring:message code="label.certificates"/></form:label></td>
                                <td><form:checkbox path="registration.certificates"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="1" cellspacing="1">
                <tr style="background-color:#E8E8E8;">
                    <td align="left"><b>Payment Details:</b></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="1" cellspacing="1">
                <tr>
                    <td width="60%">
                        <table width="100%" cellpadding="1" cellspacing="1">
                            <tr>
                                <td width="30%"><form:label path="currentPayment.amountPaid"><spring:message code="label.amountPaid"/></form:label></td>
                                <td><form:input path="currentPayment.amountPaid"/></td>
                            </tr>
                            <tr>
                                <td width="30%"><form:label path="currentPayment.mode"><spring:message code="label.mode"/></form:label></td>
                                <td>
                                    <form:select path="currentPayment.mode">
                                        <form:option value="" label="--- Select ---"/>
                                        <form:options items="${allPaymentModes}" />
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <td width="30%"><form:label path="currentPayment.receiptInfo"><spring:message code="label.receiptInfo"/></form:label></td>
                                <td><form:input path="currentPayment.receiptInfo"/></td>
                            </tr>
                            <tr>
                                <td width="30%"><form:label path="currentPayment.receiptDate"><spring:message code="label.receiptDate"/></form:label></td>
                                <td><form:input path="currentPayment.receiptDate"/></td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table width="100%" cellpadding="1" cellspacing="1">
                            <tr>
                                <td width="30%"><form:label path="currentPayment.remarks"><spring:message code="label.remarks"/></form:label></td>
                                <td><form:textarea path="currentPayment.remarks" rows="3" cols="30"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="1" cellspacing="1">
                <tr style="background-color:#E8E8E8;">
                    <td align="left"><b>Attendance Details:</b></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellpadding="1" cellspacing="1">
                <tr>
                    <td width="60%">
                        <table width="100%" cellpadding="1" cellspacing="1">
                            <tr>
                                <td width="30%"><form:label path="registration.foodCoupon"><spring:message code="label.foodCoupon"/></form:label></td>
                                <td><form:checkbox path="registration.foodCoupon"/></td>
                            </tr>
                            <tr>
                                <td width="30%"><form:label path="registration.eventKit"><spring:message code="label.eventKit"/></form:label></td>
                                <td><form:checkbox path="registration.eventKit"/></td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table width="100%" cellpadding="1" cellspacing="1">
                            <tr>
                                <td width="30%"><form:label path="currentHistoryRecord.comment"><spring:message code="label.comments"/></form:label></td>
                                <td><form:textarea path="currentHistoryRecord.comment" rows="3" cols="30"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr><td>&nbsp;</td></tr>
	<tr align="center">
	    <td>
	        <table width="100%" cellpadding="2" cellspacing="2">
                <tr style="background-color:#DFDFDF;">
                    <td align="right">
                        <form:hidden path="action"/>
                        <input type="submit" value="<c:out value="${registeredParticipant.action}"/>"/>
                    </td>
                    <td align="left">
                        <input type="reset" value="Cancel"/>
                    </td>
                </tr>
            </table>
        <td>
	</tr>
</table>
</form:form>
<mytags:footer/>
</body>
</html>
