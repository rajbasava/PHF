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
        $(document).ready(function(){
            $(function() {
                $("a#addParticipant").button();
                $("a#addParticipant").css("font-size", "11px");
            });
        });
    </script>
    </head>
</head>
<body>
<h2 align="center">Search Participants</h2>
<jsp:include page="searchParticipantsFilters.jsp" />
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
            <table style="width:100%; table-layout:fixed;">
                <tr>
                    <td>
                        <table border="1" width="100%">
                            <tr>
                                <td width="5%"><spring:message code="label.participantId"/></td>
                                <td width="20%"><spring:message code="label.name"/></td>
                                <td width="19%"><spring:message code="label.email"/></td>
                                <td width="12%"><spring:message code="label.mobile"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div style="width:100%; height:200px; overflow-y:scroll; overflow-x:hidden; empty-cells:show; font-size:11px">
                            <c:if  test="${!empty participantList}">
                                <table border="1" width="100%">
                                    <c:forEach items="${participantList}" var="participant">
                                        <tr>
                                            <td  width="5%"><c:out value="${participant.id}"/></td>
                                            <td  width="20%" class="YLink">
                                                <form id="updatePart<c:out value="${participant.id}"/>" method="post" action="updateParticipant.htm">
                                                    <input type="hidden" name="participantId" value="<c:out value="${participant.id}"/>" />
                                                    <a href="#" onclick="document.getElementById('updatePart<c:out value="${participant.id}"/>').submit();">
                                                        <c:out value="${participant.name}"/>
                                                    </a>
                                                </form>
                                            </td>
                                            <td width="19%"><c:out value="${participant.email}"/></td>
                                            <td width="12%"><c:out value="${participant.mobile}"/></td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:if>
                        </div>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</div>

<mytags:footer/>
</body>
</html>