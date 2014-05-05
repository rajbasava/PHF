<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<div class="<%= request.getParameter("class")%>">
<div class="formtitle"><%= request.getParameter("title")%></div>
<div class="formbody">
<table align="center" width="100%">
	<tr valign="top">
		<td width="60%">
			<table>
			    <tr>
                    <td><spring:message code="label.name"/></td>
                    <td><c:out value="${participant.name}"/></td>
                </tr>
			    <tr>
                    <td><spring:message code="label.email"/></td>
                    <td><c:out value="${participant.email}"/></td>
                </tr>
			    <tr>
                    <td><spring:message code="label.mobile"/></td>
                    <td><c:out value="${participant.mobile}"/></td>
                </tr>
			    <tr>
                    <td><spring:message code="label.home"/></td>
                    <td><c:out value="${participant.home}"/></td>
                </tr>
            </table>
        </td>
        <td>
            <table>
                <tr>
                    <td><spring:message code="label.address"/></td>
                    <td><c:out value="${participant.address}"/></td>
                </tr>
                <tr>
                    <td><spring:message code="label.city"/></td>
                    <td><c:out value="${participant.city}"/></td>
                </tr>
                <tr>
                    <td><spring:message code="label.state"/></td>
                    <td><c:out value="${participant.state}"/></td>
                </tr>
                <tr>
                    <td><spring:message code="label.country"/></td>
                    <td><c:out value="${participant.country}"/></td>
                </tr>
			</table>
		</td>
	</tr>
</table>
</div>
</div>
