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

            $("#registrationPayments input[name='currentPayment.receiptDate']").datepicker( "setDate", new Date());

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
            <td><form:label path="currentPayment.amountPaid"><spring:message code="label.amountPaid"/>&nbsp;*&nbsp;</form:label></td>
            <td><form:input path="currentPayment.amountPaid"/></td>
        </tr>
        <tr>
            <td><form:label path="currentPayment.receiptInfo"><spring:message code="label.receiptInfo"/>&nbsp;*&nbsp;</form:label></td>
            <td><form:input path="currentPayment.receiptInfo"/></td>
        </tr>
        <tr>
            <td><form:label path="currentPayment.receiptDate"><spring:message code="label.receiptDate"/>&nbsp;*&nbsp;</form:label></td>
            <td><form:input path="currentPayment.receiptDate"/></td>
        </tr>
        <tr>
            <td><form:label path="currentPayment.mode"><spring:message code="label.mode"/>&nbsp;*&nbsp;</form:label></td>
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
<mytags:footer/>
</body>
</html>
