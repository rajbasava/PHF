<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<html>
<head>
	<title>Participant Courses - Yoga Vidya Pranic Healing Foundation of Karnataka</title>
	<mytags:style/>
	<mytags:menu/>
	<script type="text/javascript">
		$(document).ready(function(){
            $("a#addParticipant").button();
            $("a#addParticipant").css("font-size", "11px");
            $("a#addParticipant").click(function() {
                 $("#participant").get(0).setAttribute('action', 'addParticipantForReplacement.htm');
                 $("#participant").submit();
            });

            $("a#back").button();
            $("a#back").css("font-size", "11px");
		});
	</script>
</head>
<body>
<form:form method="post" action="" commandName="participant">
<form:errors path="*" cssClass="errorblock" element="div" />
<input type="hidden" name="registrationId" value="<c:out value="${registrationId}"/>" />
<input type="hidden" name="participantName" value="<c:out value="${participantName}"/>" />
<input type="hidden" name="eventName" value="<c:out value="${eventName}"/>" />
<table align="center" cellspacing="2" cellspacing="2" width="80%">
<tr>
        <td align="center" style="font-size:18px">
            Adding Participant to Replace <c:out value="${participantName}"/> For Event <c:out value="${eventName}"/>
        </td>
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr>
        <td>
            <table width="100%" cellpadding="1" cellspacing="1">
                <tr>
                    <td align="left">&nbsp;</td>
                </tr>
            </table>
        </td>
    </tr>
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
                                <td width="40%"><form:label path="name"><spring:message code="label.name"/></form:label></td>
                                <td><form:input path="name" size="50"/></td>
                            </tr>
                            <tr>
                                <td width="40%"><form:label path="mobile"><spring:message code="label.mobile"/></form:label></td>
                                <td><form:input path="mobile" /></td>
                            </tr>
                            <tr>
                                <td width="40%"><form:label path="home"><spring:message code="label.home"/></form:label></td>
                                <td><form:input path="home" /></td>
                            </tr>
                            <tr>
                                <td width="40%"><form:label path="email"><spring:message code="label.email"/></form:label></td>
                                <td><form:input path="email" /></td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table width="100%" cellpadding="1" cellspacing="1">
                            <tr>
                                <td width="40%"><form:label path="vip"><spring:message code="label.vip"/></form:label></td>
                                <td><form:checkbox path="vip"/></td>
                            </tr>
                            <tr>
                                <td width="40%"><form:label path="vipDesc"><spring:message code="label.vipDesc"/></form:label></td>
                                <td><form:input path="vipDesc"/></td>
                            </tr>
                            <tr>
                                <td width="40%">Comments:</td>
                                <td align="left"><textarea name="comments" rows="5" cols="30"></textarea></td>
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
	        <table width="100%" cellpadding="2" cellspacing="2" style="background-color:#DFDFDF;">
                <tr>
                    <td align="right">
                        <a id="addParticipant">Replace Participant</a>
                    </td>
                </tr>
            </table>
        </td>
	</tr>
</table>
</form:form>
<mytags:footer/>
</body>
</html>