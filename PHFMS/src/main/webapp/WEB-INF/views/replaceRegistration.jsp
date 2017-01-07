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
                $("a#replaceRegistration").button();
                $("a#replaceRegistration").css("font-size", "11px");
                $("a#replaceRegistration").click(function() {
                     $("#replaceRegistration").get(0).setAttribute('action', 'replaceRegistration.htm');
                     $("#replaceRegistration").submit();
                });

                $("a#addParticipantForReplacement").button();
                $("a#addParticipantForReplacement").css("font-size", "11px");
                $("a#addParticipantForReplacement").click(function() {
                     $("#addParticipantForReplacement").get(0).setAttribute('action', 'showAddParticipantForReplacement.htm');
                     $("#addParticipantForReplacement").submit();
                });

                $("a#back").button();
                $("a#back").css("font-size", "11px");
                $("a#back").click(function() {
                     $("#replaceRegistration").get(0).setAttribute('action', 'updateRegistration.htm');
                     $("#replaceRegistration").submit();
                });
            });
        });
    </script>
</head>
</head>
<body>
<table width="100%" cellpadding="1" cellspacing="1">
    <tr>
        <td align="center" style="font-size:18px">
            Search Participant to Replace <c:out value="${participantName}"/> - <c:out value="${eventName}"/>
        </td>
    </tr>
    <tr><td>&nbsp;</td></tr>
</table>
<jsp:include page="searchParticipantFilters.jsp" />
<c:if  test="${!empty participantList}">
<table width="100%" cellpadding="2" cellspacing="2">
    <form id="replaceRegistration" method="post" action="replaceRegistration.htm">
    <tr style="background-color:#E8E8E8;">
        <td><b>Select Participant to Replace</b></td>
    </tr>
    <tr>
        <td>
            <table style="width:100%; table-layout:fixed;">
                <tr>
                    <td>
                        <table border="1" width="100%">
                            <tr>
                                <td width="3%">&nbsp;</td>
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
                            <table border="1" width="100%">
                                <input type="hidden" name="registrationId" value="<c:out value="${registrationId}"/>" />
                                <c:forEach items="${participantList}" var="participant">
                                    <tr>
                                        <td width="3%"><span><input type="radio" name="participantId" value="<c:out value="${participant.id}"/>"></span></td>
                                        <td width="5%"><span><c:out value="${participant.id}"/></span></td>
                                        <td width="20%"><span><c:out value="${participant.name}"/></span></td>
                                        <td width="20%"><span><c:out value="${participant.email}"/></span></td>
                                        <td width="12%"><span><c:out value="${participant.mobile}"/></span></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr style="background-color:#E8E8E8;">
        <td><b>Comments: </b></td>
    </tr>
    <tr>
        <td>
            <table width="100%">
                <tr>
                    <td width="25%" align="right">Enter the reason for Participant Replacement:</td>
                    <td align="left"><textarea name="comments" rows="5" cols="60"></textarea></td>
                </tr>
            </table>
        </td>
    </tr>
    </form>
</table>
</c:if>
<table>
    <tr style="background-color:#E8E8E8;">
        <td>
            <table width="100%" cellpadding="2" cellspacing="2">
                <tr>
                    <td align="left">
                        <c:if  test="${!empty participantList}">
                            <a id="replaceRegistration" href="#">Replace Registration</a>
                        </c:if>
                        <!-- <a id="addParticipantForReplacement" href="#">Add Replaceable Participant</a> -->
                        <a id="back" href="#">Back</a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<mytags:footer/>
</body>
</html>
