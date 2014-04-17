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

<table align="center" cellspacing="2">
    <form:form method="post" action="addVolKitsAction.htm" commandName="volunteerKit">
        <tr>
            <td>Event:</td>
            <td style="font-weight: bold;padding-left:5px;"><c:out value="${volunteerKit.kit.event.name}"/> </td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>Current Stock:</td>
            <td style="font-weight: bold;padding-left:5px;"><c:out value="${unallotedKitsCount}"/></td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td><form:label path="volunteerId">Volunteer Name</form:label></td>
            <td style="font-weight: bold;padding-left:5px;">
                <form:select path="volunteerId">
                    <form:option value="NONE" label="--- Select ---"/>
                    <form:options items="${volunteerList}" />
                </form:select>
            </td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td><form:label path="kitCount">kit Count</form:label></td>
            <td style="font-weight: bold;padding-left:5px;"><form:input path="kitCount"/></td>
            <td align="center">
                <form:hidden path="kit.id"/>
                <form:hidden path="kit.event.id"/>
                <input type="submit" value="Allocate Kits"/>
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
                                <input type="hidden" name="eventId" value="<c:out value="${volunteerKit.kit.event.id}"/>" />
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
