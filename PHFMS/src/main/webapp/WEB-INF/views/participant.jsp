<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>


    <c:choose>
        <c:when test="${nested}">
            <table width="100%" align="center" >
                <tr valign="top">
                    <td width="60%">
                        <table>
                            <tr>
                                <td><form:label path="participant.name"><spring:message code="label.name"/></form:label></td>
                                <td><form:input path="participant.name" size="50" /></td>
                            </tr>
                            <tr>
                                <td><form:label path="participant.mobile"><spring:message code="label.mobile"/></form:label></td>
                                <td><form:input path="participant.mobile"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="participant.home"><spring:message code="label.home"/></form:label></td>
                                <td><form:input path="participant.home"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="participant.email"><spring:message code="label.email"/></form:label></td>
                                <td><form:input path="participant.email"/></td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table>
                            <tr>
                                <td><form:label path="participant.vip"><spring:message code="label.vip"/></form:label></td>
                                <td><form:checkbox path="participant.vip"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="participant.vipDesc"><spring:message code="label.vipDesc"/></form:label></td>
                                <td><form:input path="participant.vipDesc"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="participant.address"><spring:message code="label.address"/></form:label></td>
                                <td><form:input path="participant.address"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="participant.city"><spring:message code="label.city"/></form:label></td>
                                <td><form:input path="participant.city"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="participant.state"><spring:message code="label.state"/></form:label></td>
                                <td><form:input path="participant.state"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="participant.country"><spring:message code="label.country"/></form:label></td>
                                <td><form:input path="participant.country"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </c:when>
        <c:otherwise>
            <table width="100%" align="center">
                <tr valign="top">
                    <td width="60%">
                        <table>
                            <tr>
                                <td><form:label path="name"><spring:message code="label.name"/></form:label></td>
                                <td><form:input path="name" size="50" /></td>
                            </tr>
                            <tr>
                                <td><form:label path="mobile"><spring:message code="label.mobile"/></form:label></td>
                                <td><form:input path="mobile"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="home"><spring:message code="label.home"/></form:label></td>
                                <td><form:input path="home"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="email"><spring:message code="label.email"/></form:label></td>
                                <td><form:input path="email"/></td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table>
                            <tr>
                                <td><form:label path="vip"><spring:message code="label.vip"/></form:label></td>
                                <td><form:checkbox path="vip"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="vipDesc"><spring:message code="label.vipDesc"/></form:label></td>
                                <td><form:input path="vipDesc"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="address"><spring:message code="label.address"/></form:label></td>
                                <td><form:input path="address"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="city"><spring:message code="label.city"/></form:label></td>
                                <td><form:input path="city"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="state"><spring:message code="label.state"/></form:label></td>
                                <td><form:input path="state"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="country"><spring:message code="label.country"/></form:label></td>
                                <td><form:input path="country"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </c:otherwise>
    </c:choose>

