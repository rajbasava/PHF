<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<div class="formdata">
<div class="formtitle">Event</div>
<div class="formbody">
<table align="center" >
	<tr valign="top">
		<td>
			<table>
			    <tr>
                    <td><spring:message code="label.name"/></td>
                    <td><c:out value="${event.name}"/></td>
                </tr>
			    <tr>
                    <td>Event Type</td>
                    <td><c:out value="${event.eventType}"/></td>
                </tr>
			    <tr>
                    <td>Course</td>
                    <td><c:out value="${event.courseType.name}"/></td>
                </tr>
			    <tr>
                    <td>Primary Eligibility</td>
                    <td><c:out value="${event.primaryEligibility.name}"/></td>
                </tr>
			    <tr>
                    <td>Secondary Eligibility</td>
                    <td><c:out value="${event.secondaryEligibility.name}"/></td>
                </tr>
			    <tr>
                    <td>Primary Trainer</td>
                    <td><c:out value="${event.primaryTrainer.participant.name}"/></td>
                </tr>
			    <tr>
                    <td>Secondary Trainer</td>
                    <td><c:out value="${event.secondaryTrainer.participant.name}"/></td>
                </tr>
            </table>
        </td>
        <td>
            <table>
                <tr>
                    <td><spring:message code="label.startDate"/></td>
                    <td><c:out value="${event.startDate}"/></td>
                </tr>
                <tr>
                    <td><spring:message code="label.endDate"/></td>
                    <td><c:out value="${event.endDate}"/></td>
                </tr>
                <tr>
                    <td><spring:message code="label.venue"/></td>
                    <td><c:out value="${event.venue}"/></td>
                </tr>
                <tr>
                    <td><spring:message code="label.city"/></td>
                    <td><c:out value="${event.city}"/></td>
                </tr>
                <tr>
                    <td><spring:message code="label.state"/></td>
                    <td><c:out value="${event.state}"/></td>
                </tr>
                <tr>
                    <td><spring:message code="label.seatPerLevel"/></td>
                    <td><c:out value="${event.seatPerLevel}"/></td>
                </tr>
                <tr>
                    <td><spring:message code="label.seatingType"/></td>
                    <td><c:out value="${event.seatingType}"/></td>
                </tr>
                <tr>
                    <td><spring:message code="label.rowMetaName"/></td>
                    <td><c:out value="${event.rowMetaName}"/></td>
                </tr>
			</table>
		</td>
	</tr>
</table>
</div>
</div>
