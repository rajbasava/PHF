<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<head>
	<title>Events - Yoga Vidya Pranic Healing Foundation of Karnataka</title>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#startDate").datepicker({ showOn: 'button', dateFormat: 'dd/mm/yy', buttonImageOnly: true, buttonImage: '<c:url value="/resources/img/calendar.gif"/>' });
            $("#endDate").datepicker({ showOn: 'button', dateFormat: 'dd/mm/yy', buttonImageOnly: true, buttonImage: '<c:url value="/resources/img/calendar.gif"/>' });

            $("a#submit").button();
            $("a#submit").css("font-size", "11px");
            $("a#submit").click(function() {
                 $("#participant").get(0).setAttribute('action', 'updateParticipant.htm');
                 $("#participant").submit();
            });

        });
    </script>
</head>
<body>
<div class="formdata">
    <div class="formtitle">Participant</div>
        <div class="formbody">
            <form:form method="post" action="" commandName="participant">
                <form:errors path="*" cssClass="errorblock" element="div" />
                <form:hidden path="id"/>
                <table align="center" >
                    <tr valign="top">
                        <td>
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
                        <td width="30px">&nbsp;</td>
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
            </form:form>
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
    </div>
</div>
