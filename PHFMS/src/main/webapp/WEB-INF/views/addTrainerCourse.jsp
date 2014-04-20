<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<head>
	<title>Events - Yoga Vidya Pranic Healing Foundation of Karnataka</title>
    <mytags:style/>
    <mytags:menu/>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#contractStartDate").datepicker({
                        showOn: 'button',
                        dateFormat: 'dd/mm/yy',
                        buttonImageOnly: true,
                        buttonImage: '<c:url value="/resources/img/calendar.gif"/>'
            });
            $("#contractEndDate").datepicker({
                        showOn: 'button',
                        dateFormat: 'dd/mm/yy',
                        buttonImageOnly: true,
                        buttonImage: '<c:url value="/resources/img/calendar.gif"/>'
            });

            $("a#submit").button();
            $("a#submit").css("font-size", "11px");
            $("a#submit").click(function() {
                 $("#trainerCourse").get(0).setAttribute('action', 'addTrainerCourse.htm');
                 $("#trainerCourse").submit();
            });

        });
    </script>
</head>

<body>
<div class="formdata">
<form:form method="post" action="" commandName="trainerCourse">
<div class="formtitle">Trainer Course - <c:out value="${trainerCourse.trainer.participant.name}"/></div>
<div class="formbody">
<form:errors path="*" cssClass="errorblock" element="div" />
<form:hidden path="trainerId"/>
<table align="center" >
	<tr valign="top">
		<td>
			<table>
			    <tr>
                    <td><form:label path="courseTypeId">Course</form:label></td>
                    <td>
                        <form:select path="courseTypeId">
                            <form:option value="" label="--- Select ---"/>
                            <form:options items="${allParticipantCourseTypes}" />
                        </form:select>
                    </td>
                </tr>
			    <tr>
                    <td><form:label path="foundationId">Foundation</form:label></td>
                    <td>
                        <form:select path="foundationId" cssClass="drop-select">
                            <form:option value="" label="--- Select ---"/>
                            <form:options items="${allFoundations}" />
                        </form:select>
                    </td>
                </tr>
			    <tr>
                    <td><form:label path="apprentice">Apprentice</form:label></td>
                    <td><form:checkbox path="apprentice"/></td>
                </tr>
            </table>
        </td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>
            <table>
                <tr>
                    <td><form:label path="contractStartDate"><spring:message code="label.startDate"/></form:label></td>
                    <td><form:input path="contractStartDate" /></td>
                </tr>
                <tr>
                    <td><form:label path="contractEndDate"><spring:message code="label.endDate"/></form:label></td>
                    <td><form:input path="contractEndDate" /></td>
                </tr>
			</table>
		</td>
	</tr>
</table>
</div>
<div class="formfooter">
<table width="100%">
    <tr>
        <td align="right">
            <div id="button">
                <a id="submit" href="#">Submit</a>
            </div>
        </td>
    </tr>
</table>
</div>
</form:form>
</div>
