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
            $("a#backToKits").button();
            $("a#backToKits").css("font-size", "11px");
            $("a#backToKits").click(function() {
                 $("#modifyEvent").get(0).setAttribute('action', 'backToKitsAction.htm');
                 $("#modifyEvent").submit();
            });
        });
    </script>
</head>
</head>
<body>
<h2 align="center">Kits for Event: <c:out value="${event.name}"/></h2>

<table align="center" cellspacing="2">
    <tr>
        <td>Volunteer Name:  </td>
        <td style="font-weight: bold;padding-left:5px;"><c:out value="${volunteerKit.loggedInVolunteer.volunteer.name}"/> </td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>Counter No:  </td>
        <td style="font-weight: bold;padding-left:5px;"><c:out value="${volunteerKit.loggedInVolunteer.counter}"/> </td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>Total Kits:  </td>
        <td style="font-weight: bold;padding-left:5px;"><c:out value="${volunteerKit.kitCount}"/> </td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>Kits Dispersed:  </td>
        <td style="font-weight: bold;padding-left:5px;"><c:out value="${volunteerKit.kitsGiven}"/> </td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>Kits Left:  </td>
        <td style="font-weight: bold;padding-left:5px;"><c:out value="${volunteerKit.kitCount - volunteerKit.kitsGiven}"/> </td>
        <td>&nbsp;</td>
    </tr>
    <form:form method="post" action="allotVolKitsAction.htm" commandName="volunteerKit">
        <tr>
            <td><form:label path="allotKits">Allot</form:label></td>
            <td style="font-weight: bold;padding-left:5px;"><form:input path="allotKits" /> <b>kits</b></td>
            <td align="center" style="padding-left:10px;">
                <form:hidden path="kit.event.id"/>
                <form:hidden path="kit.id"/>
                <form:hidden path="loggedInVolunteer.id"/>
                <form:hidden path="kitsGiven"/>
                <form:hidden path="kitCount"/>
                <form:hidden path="id"/>
                <input type="submit" value="<spring:message code="label.submit"/>"/>
            </td>
        </tr>
    </form:form>
    <tr>
        <td colspan="100%" style="padding-top:10px;">
            <form id="modifyEvent" method="post" action="">
                <div style="background-color:#E8E8E8;">
                    <table width="100%" cellpadding="2" cellspacing="2">
                        <tr>
                            <td align="left" nowrap>
                                <input type="hidden" name="eventId" value="<c:out value="${event.id}"/>" />
                                <a id="backToKits" href="#">Back</a>
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
