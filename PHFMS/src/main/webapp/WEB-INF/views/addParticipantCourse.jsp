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
            $("#participantCourseForm input[name='participantCourse.startDate']").datepicker({
                showOn: 'button',
                dateFormat: 'dd/mm/yy',
                buttonImageOnly: true,
                buttonImage: '<c:url value="/resources/img/calendar.gif"/>'
            });
            $("#participantCourseForm input[name='participantCourse.endDate']").datepicker({
                showOn: 'button',
                dateFormat: 'dd/mm/yy',
                buttonImageOnly: true,
                buttonImage: '<c:url value="/resources/img/calendar.gif"/>'
            });

            $("a#addParticipantCourse").button();
            $("a#addParticipantCourse").css("font-size", "11px");
            $("a#addParticipantCourse").click(function() {
                 $("#participantCourseForm").get(0).setAttribute('action', 'addParticipantCourse.htm');
                 $("#participantCourseForm").submit();
            });

            $("a#back").button();
            $("a#back").css("font-size", "11px");
		});
	</script>
</head>
<body>
<form:form method="post" action="" commandName="participantCourseForm">
<form:errors path="*" cssClass="errorblock" element="div" />
<form:hidden path="participantId"/>
<table align="center" cellspacing="2" cellspacing="2" width="80%">
    <tr>
        <td>
            <table width="100%" cellpadding="1" cellspacing="1">
                <tr>
                    <td align="left">&nbsp;</td>
                </tr>
            </table>
        </td>
    </tr>
    <c:choose>
        <c:when test="${showParticipantDetails}">
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
                                        <td width="40%"><form:label path="participant.name"><spring:message code="label.name"/></form:label></td>
                                        <td><form:input path="participant.name" size="50"/></td>
                                    </tr>
                                    <tr>
                                        <td width="40%"><form:label path="participant.mobile"><spring:message code="label.mobile"/></form:label></td>
                                        <td><form:input path="participant.mobile" /></td>
                                    </tr>
                                    <tr>
                                        <td width="40%"><form:label path="participant.home"><spring:message code="label.home"/></form:label></td>
                                        <td><form:input path="participant.home" /></td>
                                    </tr>
                                    <tr>
                                        <td width="40%"><form:label path="participant.email"><spring:message code="label.email"/></form:label></td>
                                        <td><form:input path="participant.email" /></td>
                                    </tr>
                                </table>
                            </td>
                            <td>
                                <table width="100%" cellpadding="1" cellspacing="1">
                                    <tr>
                                        <td width="40%"><form:label path="participant.vip"><spring:message code="label.vip"/></form:label></td>
                                        <td><form:checkbox path="participant.vip"/></td>
                                    </tr>
                                    <tr>
                                        <td width="40%"><form:label path="participant.vipDesc"><spring:message code="label.vipDesc"/></form:label></td>
                                        <td><form:input path="participant.vipDesc"/></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </c:when>
    </c:choose>
    <tr>
        <td>
            <table width="100%" cellpadding="1" cellspacing="1">
                <tr style="background-color:#E8E8E8;">
                    <td align="left"><b>Course Details:</b></td>
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
                                <td width="40%"><form:label path="participantCourse.foundationId"><spring:message code="label.foundation"/></form:label></td>
                                <td>
                                    <form:select path="participantCourse.foundationId">
                                        <form:option value="" label="--- Select ---"/>
                                        <form:options items="${allFoundations}" />
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <td width="40%"><form:label path="participantCourse.courseTypeId">Course</form:label></td>
                                <td>
                                    <form:select path="participantCourse.courseTypeId">
                                        <form:option value="" label="--- Select ---"/>
                                        <form:options items="${allParticipantCourseTypes}" />
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <td width="40%"><form:label path="participantCourse.primaryTrainerId">Primary Trainer</form:label></td>
                                <td><form:input path="participantCourse.primaryTrainerId" size="30"/></td>
                            </tr>
                            <tr>
                                <td width="40%"><form:label path="participantCourse.secondaryTrainerId">Secondary Trainer</form:label></td>
                                <td><form:input path="participantCourse.secondaryTrainerId" size="30"/></td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table width="100%" cellpadding="1" cellspacing="1">
                            <tr>
                                <td width="40%"><form:label path="participantCourse.startDate"><spring:message code="label.startDate"/></form:label></td>
                                <td><form:input path="participantCourse.startDate" /></td>
                            </tr>
                            <tr>
                                <td width="40%"><form:label path="participantCourse.endDate"><spring:message code="label.endDate"/></form:label></td>
                                <td><form:input path="participantCourse.endDate" /></td>
                            </tr>
                            <tr>
                                <td width="40%"><form:label path="participantCourse.city"><spring:message code="label.city"/></form:label></td>
                                <td><form:input path="participantCourse.city" /></td>
                            </tr>
                            <tr>
                                <td width="40%"><form:label path="participantCourse.state"><spring:message code="label.state"/></form:label></td>
                                <td><form:input path="participantCourse.state" /></td>
                            </tr>
                            <tr>
                                <td width="40%"><form:label path="participantCourse.coursecertificate">Certificate Id</form:label></td>
                                <td><form:input path="participantCourse.coursecertificate"/></td>
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
                        <a id="addParticipantCourse">Submit</a>
                    </td>
                    <td align="left">
                        <a id="back">Cancel</a>
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