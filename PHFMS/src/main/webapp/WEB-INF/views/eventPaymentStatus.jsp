<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<html>
<head>
	<title>Events - Yoga Vidya Pranic Healing Foundation of Karnataka</title>
    <mytags:style />
    <mytags:menu />
	<script type="text/javascript">
        $(document).ready(function(){
            $("a#refresh").button();
            $("a#refresh").css("font-size", "11px");
            $("a#refresh").click(function() {
                 $("#eventStatus").get(0).setAttribute('action', 'showEventPaymentStatus.htm');
                 $("#eventStatus").submit();
            });

            $("a#backToEvents").button();
            $("a#backToEvents").css("font-size", "11px");
            $("a#backToEvents").click(function() {
                 $("#eventStatus").get(0).setAttribute('action', 'showEventDetails.htm');
                 $("#eventStatus").submit();
            });
        });
    </script>
</head>
<body>

<div align="center" style="padding-bottom:20px;">
<table width="60%" cellpadding="3" cellspacing="3" align="center">
    <c:if  test="${!empty eventPayments}">
        <tr>
            <td>
                <table class="data" border="1" cellpadding="1" cellspacing="1" width="100%" align="center">
                    <tr>
                        <th>Foundation Name</th>
                        <th>Fully Paid Participants</th>
                        <th>Fully Paid Total Amount</th>
                        <th>Partial Paid Participants</th>
                        <th>Partial Paid Total Amount</th>
                    </tr>
                    <c:forEach items="${eventPayments}" var="ep">
                        <tr>
                            <td><c:out value="${ep[0]}"/> </td>
                            <td><c:out value="${ep[1]}"/> </td>
                            <td><c:out value="${ep[2]}"/></td>
                            <td><c:out value="${ep[3]}"/></td>
                            <td><c:out value="${ep[4]}"/></td>
                        </tr>
                    </c:forEach>
                    <tr style="font-weight: bold;">
                        <td>&nbsp;</td>
                        <td>
                            <c:out value="${eventTotalPayments[0]}"/>
                        </td>
                        <td>
                            <c:out value="${eventTotalPayments[1]}"/>
                        </td>
                        <td>
                            <c:out value="${eventTotalPayments[2]}"/>
                        </td>
                        <td>
                            <c:out value="${eventTotalPayments[3]}"/>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </c:if>
</div>

<table width="60%" cellpadding="3" cellspacing="3" align="center">
    <tr>
        <td>
            <form id="eventStatus" method="post" action="">
                <input type="hidden" name="eventId" value="<c:out value="${eventId}"/>" />
                <input type="hidden" name="isEdit" value="false" />
                <div style="background-color:#E8E8E8;">
                    <table width="100%" cellpadding="2" cellspacing="2" >
                        <tr>
                            <td align="right">
                                <a id="refresh" href="#">Refresh</a>
                            </td>
                            <td align="left">
                                <a id="backToEvents" href="#">Back</a>
                            </td>
                        </tr>
                    </table>
                </div>
            </form>
        </td>
    </tr>
</table>

<mytags:footer/>
</body>
</html>
