<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<html>
<head>
    <title>Arhatic Yoga Retreat - Search</title>
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
    <mytags:style />
    <mytags:menu />
    <script type="text/javascript">
        $(document).ready(function() {
            $("#registrationPayments input[name='currentPayment.receiptDate']").datepicker({
                showOn: 'button',
                dateFormat: 'dd/mm/yy',
                buttonImageOnly: true,
                buttonImage: '<c:url value="/resources/img/calendar.gif"/>'
            });

            $("#registrationPayments input[name='currentPayment.pdcDate']").datepicker({
                showOn: 'button',
                dateFormat: 'dd/mm/yy',
                buttonImageOnly: true,
                buttonImage: '<c:url value="/resources/img/calendar.gif"/>'
            });

            $("a#submit").button();
            $("a#submit").css("font-size", "11px");
            $("a#submit").click(function() {
                 $("#registrationPayments").get(0).setAttribute('action', 'processPayments.htm');
                 $("#registrationPayments").submit();
            });

            $("a#back").button();
            $("a#back").css("font-size", "11px");
            $("a#back").click(function() {
                 $("#registrationPayments").get(0).setAttribute('action', 'updateRegistration.htm');
                 $("#registrationPayments").submit();
            });

            $("a#showPayments").button();
            $("a#showPayments").css("font-size", "11px");
            $("a#showPayments").click(function() {
                 $("#showPayments").get(0).setAttribute('action', 'showPayments.htm');
                 $("#showPayments").submit();
            });
        });
    </script>
</head>
</head>
<body>
<form:form method="post" action="processPayments.htm" commandName="registrationPayments">
<form:errors path="*" cssClass="errorblock" element="div" />
<h2 align="center">Payments: <c:out value="${registrationPayments.registration.event.name}"/> - <c:out value="${registrationPayments.registration.participant.name}"/></h2>
    <form:hidden path="action"/>
    <form:hidden path="registrationId"/>
    <form:hidden path="currentPayment.id"/>
    <table align="center">
        <tr>
            <td><form:label path="currentPayment.amountPaid"><spring:message code="label.amountPaid"/></form:label></td>
            <td><form:input path="currentPayment.amountPaid"/></td>
        </tr>
        <tr>
            <td><form:label path="currentPayment.receiptInfo"><spring:message code="label.receiptInfo"/></form:label></td>
            <td><form:input path="currentPayment.receiptInfo"/></td>
        </tr>
        <tr>
            <td><form:label path="currentPayment.receiptDate"><spring:message code="label.receiptDate"/></form:label></td>
            <td><form:input path="currentPayment.receiptDate"/></td>
        </tr>
        <tr>
            <td><form:label path="currentPayment.mode"><spring:message code="label.mode"/></form:label></td>
            <td>
                <form:select path="currentPayment.mode">
                    <form:option value="" label="--- Select ---"/>
                    <form:options items="${allPaymentModes}" />
                </form:select>
            </td>

        </tr>
        <tr>
            <td><form:label path="currentPayment.pdcNotClear"><spring:message code="label.pdcNotClear"/></form:label></td>
            <td><form:checkbox path="currentPayment.pdcNotClear"/></td>
        </tr>
        <tr>
            <td><form:label path="currentPayment.pdc"><spring:message code="label.pdc"/></form:label></td>
            <td><form:input path="currentPayment.pdc"/></td>
        </tr>
        <tr>
            <td><form:label path="currentPayment.pdcDate"><spring:message code="label.pdcDate"/></form:label></td>
            <td><form:input path="currentPayment.pdcDate"/></td>
        </tr>
        <tr>
            <td><form:label path="currentPayment.remarks"><spring:message code="label.remarks"/></form:label></td>
            <td><form:textarea path="currentPayment.remarks" rows="5" cols="30"/></td>
        </tr>
        <tr align="center">
            <table width="100%" cellpadding="2" cellspacing="2" align="center">
                <tr align="center">
                    <td class="YLink">
                        <a id="submit" href="#"><c:out value="${registrationPayments.action}"/> Payment</a>
                        <a id="back" href="#">Back</a>
                    </td>
                </tr>
            </table>
        </tr>
    </table>
</form:form>
<br>
<c:if  test="${!empty registrationPayments.payments}">
    <tr align="left">
    </tr>
    <tr>
        <table class="data" border="1" cellpadding="1" cellspacing="1" width="100%">
        <form id="showPayments" method="post" action="showPayments.htm">
        <input type="hidden" name="registration.id" value="<c:out value="${registrationPayments.registrationId}"/>" />
        <tr>
            <td>&nbsp;</td>
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
        <c:forEach items="${registrationPayments.payments}" var="payment">
            <tr>
                <td><input type="radio" name="paymentId" value="<c:out value="${payment.id}"/>"></td>
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
        </form>
        </table>
    </tr>
    <tr>
    <div id="button">
        <table width="100%" cellpadding="2" cellspacing="2">
            <tr style="background-color:#DFDFDF;">
                <td align="left">
                    <a id="showPayments" href="#">Edit Payment</a>
                </td>
            </tr>
        </table>
    </div>
    </tr>
</c:if>
<mytags:footer/>
</body>
</html>
