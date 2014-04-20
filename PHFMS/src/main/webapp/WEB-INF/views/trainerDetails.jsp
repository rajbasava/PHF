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

            $("a#addTrainerCourse").button();
            $("a#addTrainerCourse").css("font-size", "11px");
            $("a#addTrainerCourse").click(function() {
                 $("#modifyTrainerDetails").get(0).setAttribute('action', 'showAddTrainerCourse.htm');
                 $("#modifyTrainerDetails").submit();
            });

            $("#results").flexigrid({
                    colModel : [
                        {display: '<spring:message code="label.name"/>', width : 300, align: 'left'},
                        {display: '<spring:message code="label.course"/>', width : 100, align: 'left'},
                        {display: '<spring:message code="label.foundation"/>', width : 300, align: 'left'},
                        {display: 'Is Apprentice', width : 50, align: 'left'},
                        {display: 'Contract Start date', width : 200, align: 'left'},
                        {display: 'Contract End date', width : 200, align: 'left'}
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

<jsp:include page="participantSummary.jsp">
    <jsp:param name="class" value="formdata"/>
    <jsp:param name="title" value="Trainer"/>
</jsp:include>

<c:if  test="${!empty courses}">
<table width="100%">
    <tr style="background-color:#E8E8E8;">
        <td>Courses</td>
    </tr>
    <tr>
        <td>
            <table id="results">
                <tbody>
                    <c:forEach items="${courses}" var="trainerCourse">
                        <tr>
                            <td><c:out value="${trainerCourse.trainer.participant.name}"/> </td>
                            <td><c:out value="${trainerCourse.courseType.shortName}"/></td>
                            <td><c:out value="${trainerCourse.foundation.shortName}"/></td>
                            <td><c:out value="${trainerCourse.apprentice}"/></td>
                            <td><c:out value="${trainerCourse.contractStartDate}"/></td>
                            <td><c:out value="${trainerCourse.contractEndDate}"/></td>
                        </tr>
                    </c:forEach>
                <tbody>
            </table>
        </td>
    </tr>
</table>
</c:if>

<div>
    <form id="modifyTrainerDetails" method="post" action="">
        <input type="hidden" name="participantId" value="<c:out value="${trainer.participant.id}"/>" />
        <input type="hidden" name="trainerId" value="<c:out value="${trainer.id}"/>" />
        <table width="100%" >
            <tr height="10px"><td>&nbsp;</td></tr>
            <tr style="background-color:#E8E8E8;">
                <td align="center" cellpadding="2px">
                    <a id="addTrainerCourse" href="#">Add Trainer Course</a>
                </td>
            </tr>
        </table>
    </form>
</div>

<mytags:footer/>
</body>
</html>
