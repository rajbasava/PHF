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

            $("a#addCourseToParticipant").button();
            $("a#addCourseToParticipant").css("font-size", "11px");
            $("a#addCourseToParticipant").click(function() {
                 $("#modifyEvent").get(0).setAttribute('action', 'showAddParticipantCourse.htm');
                 $("#modifyEvent").submit();
            });

            $("a#registerForCourse").button();
            $("a#registerForCourse").css("font-size", "11px");
            $("a#registerForCourse").click(function() {
                 $("#modifyEvent").get(0).setAttribute('action', 'registerForCourse.htm');
                 $("#modifyEvent").submit();
            });

            $("a#editParticipant").button();
            $("a#editParticipant").css("font-size", "11px");
            $("a#editParticipant").click(function() {
                 $("#modifyEvent").get(0).setAttribute('action', 'showParticipantDetails.htm');
                 $("#modifyEvent").submit();
            });

            $("#results").flexigrid({
                    colModel : [
                        {display: '<spring:message code="label.name"/>', width : 300, align: 'left'},
                        {display: '<spring:message code="label.course"/>', width : 100, align: 'left'},
                        {display: '<spring:message code="label.trainer"/>', width : 300, align: 'left'},
                        {display: '<spring:message code="label.startDate"/>', width : 100, align: 'left'},
                        {display: '<spring:message code="label.endDate"/>', width : 100, align: 'left'},
                        {display: '<spring:message code="label.foundation"/>', width : 250, align: 'left'},
                        {display: '<spring:message code="label.city"/>', width : 80, align: 'left'}
                    ],
                    useRp: true,
                    rp: 10,
                    showTableToggleBtn: false,
                    resizable: false,
                    height: 175,
                    width: 1325,
                    singleSelect: true
            });
        });
    </script>
</head>
<body>

<c:choose>
    <c:when test="${isEdit}">
        <jsp:include page="participant.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="participantSummary.jsp">
            <jsp:param name="class" value="formdata"/>
        </jsp:include>
    </c:otherwise>
</c:choose>

<c:if  test="${!empty courses}">
<table width="100%">
    <tr style="background-color:#E8E8E8;">
        <td>Courses</td>
    </tr>
    <tr>
        <td>
            <table id="results">
                <tbody>
                    <c:forEach items="${courses}" var="participantCourse">
                        <tr>
                            <td><c:out value="${participantCourse.participant.name}"/> </td>
                            <td><c:out value="${participantCourse.courseType.shortName}"/></td>
                            <td><c:out value="${participantCourse.primaryTrainer.participant.Name}"/></td>
                            <td><c:out value="${participantCourse.startDate}"/> </td>
                            <td><c:out value="${participantCourse.endDate}"/></td>
                            <td><c:out value="${participantCourse.foundation.shortName}"/></td>
                            <td><c:out value="${participantCourse.city}"/></td>
                        </tr>
                    </c:forEach>
                <tbody>
            </table>
        </td>
    </tr>
</table>
</c:if>

<div>
    <form id="modifyEvent" method="post" action="">
        <input type="hidden" name="participantId" value="<c:out value="${participant.id}"/>" />
        <input type="hidden" name="isEdit" value="true" />
        <table width="100%" >
            <tr height="10px"><td>&nbsp;</td></tr>
            <tr style="background-color:#E8E8E8;">
                <td align="center" cellpadding="2px">
                    <a id="addCourseToParticipant" href="#">Add Course</a>
                    <a id="registerForCourse" href="#">Register</a>
                    <a id="editParticipant" href="#">Edit</a>
                </td>
            </tr>
        </table>
    </form>
</div>

<mytags:footer/>
</body>
</html>
