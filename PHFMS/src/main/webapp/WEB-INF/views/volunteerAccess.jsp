<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<html>
<head>
	<title>Arhatic Yoga Retreat - Volunteer</title>
    <mytags:style/>
    <mytags:menu/>
        <script type="text/javascript">
            $(document).ready(function(){

                $("a#addAccessControl").button();
                $("a#addAccessControl").css("font-size", "11px");
                $("a#addAccessControl").click(function() {
                     $("#modifyAccess").get(0).setAttribute('action', 'showAccessControl.htm');
                     $("#modifyAccess").submit();
                });

                $("a#addAccessFilter").button();
                $("a#addAccessFilter").css("font-size", "11px");
                $("a#addAccessFilter").click(function() {
                     $("#modifyAccess").get(0).setAttribute('action', 'showAccessFilter.htm');
                     $("#modifyAccess").submit();
                });

                $("a#clearCache").button();
                $("a#clearCache").css("font-size", "11px");
                $("a#clearCache").click(function() {
                     $("#modifyAccess").get(0).setAttribute('action', 'clearCache.htm');
                     $("#modifyAccess").submit();
                });

                $("#accessControlList").flexigrid({
                        colModel : [
                            {display: '<spring:message code="label.id"/>', width : 50, align: 'left'},
                            {display: '<spring:message code="label.permission"/>', width : 350, align: 'left'},
                            {display: ' ', width : 250, align: 'left'}
                        ],
                        useRp: true,
                        rp: 10,
                        showTableToggleBtn: false,
                        resizable: false,
                        height: 175,
                        width: 1325,
                        singleSelect: true
                });

                $("#accessFilterList").flexigrid({
                        colModel : [
                            {display: '<spring:message code="label.id"/>', width : 50, align: 'left'},
                            {display: '<spring:message code="label.event"/>', width : 250, align: 'left'},
                            {display: '<spring:message code="label.foundation"/>', width : 250, align: 'left'},
                            {display: ' ', width : 250, align: 'left'}
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
<h2 align="center">Access List for <c:out value="${volunteerName}"/></h2>

<c:if  test="${!empty accessControlList}">
<table width="100%">
    <tr style="background-color:#E8E8E8;">
        <td>Workshop Levels</td>
    </tr>
    <tr>
        <td>
            <table id="accessControlList">
                <tbody>
                    <c:forEach items="${accessControlList}" var="accessControl">
                        <tr>
                            <td><c:out value="${accessControl.id}"/> </td>
                            <td><c:out value="${accessControl.permissionName}"/> </td>
                            <td class="YLink">
                                <form id="delAccessControl<c:out value="${accessControl.id}"/>" method="post" action="deleteAccessControl.htm">
                                    <input type="hidden" name="accessControlId" value="<c:out value="${accessControl.id}"/>" />
                                    <input type="hidden" name="volunteerId" value="<c:out value="${volunteerId}"/>" />
                                    <a href="#" onclick="document.getElementById('delAccessControl<c:out value="${accessControl.id}"/>').submit();"><spring:message code="label.delete"/></a>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                <tbody>
            </table>
        </td>
    </tr>
</table>
</c:if>

<c:if  test="${!empty accessFilterList}">
<table width="100%">
    <tr style="background-color:#E8E8E8;">
        <td>Workshop Levels</td>
    </tr>
    <tr>
        <td>
            <table id="accessFilterList">
                <tbody>
                    <c:forEach items="${accessFilterList}" var="accessFilter">
                        <tr>
                            <td><c:out value="${accessFilter.id}"/> </td>
                            <td><c:out value="${accessFilter.event.name}"/> </td>
                            <td><c:out value="${accessFilter.foundation.shortName}"/> </td>
                            <td class="YLink">
                                <form id="delAccessFilter<c:out value="${accessFilter.id}"/>" method="post" action="deleteAccessFilter.htm">
                                <input type="hidden" name="accessFilterId" value="<c:out value="${accessFilter.id}"/>" />
                                <input type="hidden" name="volunteerId" value="<c:out value="${volunteerId}"/>" />
                                <a href="#" onclick="document.getElementById('delAccessFilter<c:out value="${accessFilter.id}"/>').submit();"><spring:message code="label.delete"/></a>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                <tbody>
            </table>
        </td>
    </tr>
</table>
</c:if>

<div>
    <form id="modifyAccess" method="post" action="">
        <input type="hidden" name="volunteerId" value="<c:out value="${volunteerId}"/>" />
        <table width="100%" >
            <tr height="10px"><td>&nbsp;</td></tr>
            <tr style="background-color:#E8E8E8;">
                <td align="center" cellpadding="2px">
                    <a id="addAccessControl" href="#">Add Access Control</a>
                    <a id="addAccessFilter" href="#">Add Access Filter</a>
                    <a id="clearCache" href="#">Clear Cache</a>
                </td>
            </tr>
        </table>
    </form>
</div>

<mytags:footer/>
</body>
</html>
