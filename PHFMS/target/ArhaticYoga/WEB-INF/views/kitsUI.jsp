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
            $("a#allotKits").button();
            $("a#allotKits").css("font-size", "11px");
            $("a#allotKits").click(function() {
                 $("#modifyEvent").get(0).setAttribute('action', 'showAddVolKitsUI.htm');
                 $("#modifyEvent").submit();
            });

            $("a#refresh").button();
            $("a#refresh").css("font-size", "11px");
            $("a#refresh").click(function() {
                 $("#modifyEvent").get(0).setAttribute('action', 'refreshKitsUI.htm');
                 $("#modifyEvent").submit();
            });

            $("a#backToEvents").button();
            $("a#backToEvents").css("font-size", "11px");
            $("a#backToEvents").click(function() {
                 $("#modifyEvent").get(0).setAttribute('action', 'backToEventsAction.htm');
                 $("#modifyEvent").submit();
            });
        });
    </script>
</head>
<body>
<!--
<h2 align="center">Kits for Event: <c:out value="${event.name}"/></h2>

<table class="data" border="1" cellpadding="1" cellspacing="1" width="50%" align="center">
    <tr>
        <th><spring:message code="label.name"/></th>
        <th><spring:message code="label.stock"/></th>
        <th>&nbsp;</th>
    </tr>
	<tr>
		<td><c:out value="${event.name}"/> </td>
		<td><c:out value="${eventKit.stock}"/></td>
		<td class="YLink">
            <form id="showEventKitsUI<c:out value="${eventKit.id}"/>" method="post" action="showEventKitsUI.htm">
                <input type="hidden" name="id" value="<c:out value="${eventKit.id}"/>" />
                <input type="hidden" name="eventId" value="<c:out value="${event.id}"/>" />
                <a href="#" onclick="document.getElementById('showEventKitsUI<c:out value="${eventKit.id}"/>').submit();"><spring:message code="label.update"/></a>
            </form>
        </td>
	</tr>
</table>
-->

<div align="center" style="padding-bottom:20px;">
    <table border="0" cellpadding="1" cellspacing="1" align="center">
        <tr>
            <th class="Kits">Event:</th>
            <td class="KitsBold"><c:out value="${event.name}"/> </td>
        </tr>
        <tr>
            <th class="Kits">Total Kits:</th>
            <td class="KitsBold"><c:out value="${eventKit.stock}"/></td>
        </tr>
        <tr>
            <th class="Kits">Current Stock with Administrator:</th>
            <td class="KitsBold"><c:out value="${unallotedKitsCount}"/></td>
        </tr>
        <tr>
            <th class="Kits">Total Attended Participants:</th>
            <td class="KitsBold"><c:out value="${kitsGivenCount}"/></td>
        </tr>
    </table>
</div>

<table width="60%" cellpadding="3" cellspacing="3" align="center">
    <c:if  test="${!empty volunteerKits}">
        <tr>
            <td>
                    <table class="data" border="1" cellpadding="1" cellspacing="1" width="100%" align="center">
                        <tr>
                            <th><spring:message code="label.volName"/></th>
                            <th><spring:message code="label.counter"/></th>
                            <th><spring:message code="label.kitCount"/></th>
                            <th><spring:message code="label.kitsGiven"/></th>
                            <th><spring:message code="label.kitsLeft"/></th>
                            <th>&nbsp;</th>
                        </tr>
                        <c:forEach items="${volunteerKits}" var="volKit">
                            <tr>
                                <td><c:out value="${volKit.loggedInVolunteer.volunteer.name}"/> </td>
                                <td><c:out value="${volKit.loggedInVolunteer.counter}"/> </td>
                                <td><c:out value="${volKit.kitCount}"/></td>
                                <td><c:out value="${volKit.kitsGiven}"/></td>
                                <td><c:out value="${volKit.kitCount - volKit.kitsGiven}"/> </td>
                                <td class="YLink">
                                    <form id="showVolKitsUI<c:out value="${volKit.id}"/>" method="post" action="showVolKitsUI.htm">
                                        <input type="hidden" name="id" value="<c:out value="${volKit.id}"/>" />
                                        <input type="hidden" name="eventId" value="<c:out value="${event.id}"/>" />
                                        <a href="#" onclick="document.getElementById('showVolKitsUI<c:out value="${volKit.id}"/>').submit();">
                                            <spring:message code="label.update"/>
                                        </a>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr style="font-weight: bold;">
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>
                                <c:out value="${allotedKitsCount}"/>
                            </td>
                            <td>
                                <c:out value="${kitsGivenCount}"/>
                            </td>
                            <td>
                                <c:out value="${kitsLeftCount}"/>
                            </td>
                            <td>&nbsp;</td>
                        </tr>
                    </table>
            </td>
        </tr>
    </c:if>
    <tr>
        <td>
            <form id="modifyEvent" method="post" action="">
                <div style="background-color:#E8E8E8;">
                    <table width="100%" cellpadding="2" cellspacing="2">
                        <tr>
                            <td align="left" nowrap>
                                <input type="hidden" name="id" value="<c:out value="${eventKit.id}"/>" />
                                <input type="hidden" name="eventId" value="<c:out value="${event.id}"/>" />
                                <input type="hidden" name="unallotedKitsCount" value="<c:out value="${unallotedKitsCount}"/>" />
                                <a id="allotKits" href="#">Allot Kits to Volunteer</a>
                            </td>
                            <td align="left">
                                <a id="refresh" href="#">Refresh</a>
                            </td>
                            <td align="left" width="100%">
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
