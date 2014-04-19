<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<html>
<head>
    <title>Arhatic Yoga Retreat - Search Particpants</title>
	<mytags:style/>
	<mytags:menu/>
    <script type="text/javascript">
        $(function() {
            $("#results").flexigrid({
					colModel : [
                        {display: '<spring:message code="label.id"/>', width : 30, align: 'left'},
                        {display: '<spring:message code="label.name"/>', width : 600, align: 'left'},
                        {display: '<spring:message code="label.email"/>', width : 350, align: 'left'},
                        {display: '<spring:message code="label.mobile"/>', width : 250, align: 'left'}
                    ],
					useRp: true,
					rp: 10,
					showTableToggleBtn: false,
					resizable: false,
					height: 275,
					width: 1325,
					singleSelect: true
			});
        });
    </script>
    </head>
</head>
<body>
<jsp:include page="searchParticipantFilters.jsp" />
<div id="container">
<table width="100%">
    <tr style="background-color:#E8E8E8;">
        <td>
            <table width="100%" cellpadding="2" cellspacing="2">
                <tr>
                    <td align="left">Search Participants</td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table id="results">
                <tbody>
                    <c:if  test="${!empty participantList}">
                        <c:forEach items="${participantList}" var="participant">
                            <tr>
                                <td  width="5%"><c:out value="${participant.id}"/></td>
                                <td  width="20%" class="YLink">
                                    <form id="openPart<c:out value="${participant.id}"/>" method="post" action="showParticipantDetails.htm">
                                        <input type="hidden" name="participantId" value="<c:out value="${participant.id}"/>" />
                                        <input type="hidden" name="isEdit" value="false" />
                                        <a href="#" onclick="document.getElementById('openPart<c:out value="${participant.id}"/>').submit();">
                                            <c:out value="${participant.name}"/>
                                        </a>
                                    </form>
                                </td>
                                <td width="19%"><c:out value="${participant.email}"/></td>
                                <td width="12%"><c:out value="${participant.mobile}"/></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                <tbody>
            </table>
        </td>
    </tr>
</table>
</div>

<mytags:footer/>
</body>
</html>