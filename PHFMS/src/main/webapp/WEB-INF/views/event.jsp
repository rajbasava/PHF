<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<head>
	<title>Events - Yoga Vidya Pranic Healing Foundation of Karnataka</title>
    <script type="text/javascript">
        function getTrainers(){
            if ($("select#courseTypeId").val() == ''){
               $("select#eventFeeId").html('<option value=""> --- Select --- </option>');
            }
            else {
                $.getJSON(
                    "getTrainersForCourse.htm",
                    {courseTypeId: $("select#courseTypeId").val()},
                    function(data) {
                        var options = '<option value="-1"> --- Select --- </option>';
                        var len = data.length;
                        for(var i=0; i<len; i++){
                            options +=  '<option value="' + data[i].id + '">' + data[i].value + '</option>';
                        }
						$("select#primaryTrainerId").html(options);
						$("select#secondaryTrainerId").html(options);
                    }
                );
            }
        }

        $(document).ready(function(){
            $("#startDate").datepicker({ showOn: 'button', dateFormat: 'dd/mm/yy', buttonImageOnly: true, buttonImage: '<c:url value="/resources/img/calendar.gif"/>' });
            $("#endDate").datepicker({ showOn: 'button', dateFormat: 'dd/mm/yy', buttonImageOnly: true, buttonImage: '<c:url value="/resources/img/calendar.gif"/>' });

            $("a#submit").button();
            $("a#submit").css("font-size", "11px");
            $("a#submit").click(function() {
                 $("#event").get(0).setAttribute('action', 'saveOrUpdateEvent.htm');
                 $("#event").submit();
            });

             $("select#courseTypeId").change(function()
             {
				 getTrainers();
             });

             $("select#courseTypeId").focus(function()
             {
				 getTrainers();
             });

             getTrainers();

        });
    </script>
</head>

<body>
<div class="formdata">
<div class="formtitle">Event</div>
<div class="formbody">
<form:form method="post" action="saveOrUpdateEvent.htm" commandName="event">
<form:errors path="*" cssClass="errorblock" element="div" />
<form:hidden path="id"/>
<form:hidden path="seatAllocated"/>
<table align="center" >
	<tr valign="top">
		<td>
			<table>
			    <tr>
                    <td><form:label path="name"><spring:message code="label.name"/></form:label></td>
                    <td><form:input path="name" size="30"/></td>
                </tr>
			    <tr>
                    <td><form:label path="eventType">Event Type</form:label></td>
                    <td>
                        <form:select path="eventType">
                            <form:option value="" label="--- Select ---"/>
                            <form:options items="${eventTypeMap}" />
                        </form:select>
                    </td>
                </tr>
			    <tr>
                    <td><form:label path="courseTypeId">Course</form:label></td>
                    <td>
                        <form:select path="courseTypeId">
                            <form:option value="" label="--- Select ---"/>
                            <form:options items="${allParticipantCourseTypes}" />
                        </form:select>
                    </td>
                </tr>
			    <tr>
                    <td><form:label path="primaryEligibilityId">Primary Eligibility</form:label></td>
                    <td>
                        <form:select path="primaryEligibilityId">
                            <form:option value="" label="--- Select ---"/>
                            <form:options items="${allParticipantCourseTypes}" />
                        </form:select>
                    </td>
                </tr>
			    <tr>
                    <td><form:label path="secondaryEligibilityId">Secondary Eligibility</form:label></td>
                    <td>
                        <form:select path="secondaryEligibilityId">
                            <form:option value="" label="--- Select ---"/>
                            <form:options items="${allParticipantCourseTypes}" />
                        </form:select>
                    </td>
                </tr>
			    <tr>
                    <td><form:label path="primaryTrainerId">Primary Trainer</form:label></td>
                    <td>
                        <form:select path="primaryTrainerId">
                            <form:option value="-1" label="--- Select ---"/>
                            <form:options items="${allTrainers}" itemValue="id" itemLabel="value"/>
                        </form:select>
                    </td>
                </tr>
			    <tr>
                    <td><form:label path="secondaryTrainerId">Secondary Trainer</form:label></td>
                    <td>
                        <form:select path="secondaryTrainerId">
                            <form:option value="-1" label="--- Select ---"/>
                            <form:options items="${allTrainers}" itemValue="id" itemLabel="value"/>
                        </form:select>
                    </td>
                </tr>
            </table>
        </td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>
            <table>
                <tr>
                    <td><form:label path="startDate"><spring:message code="label.startDate"/></form:label></td>
                    <td><form:input path="startDate" /></td>
                </tr>
                <tr>
                    <td><form:label path="endDate"><spring:message code="label.endDate"/></form:label></td>
                    <td><form:input path="endDate" /></td>
                </tr>
                <tr>
                    <td><form:label path="venue"><spring:message code="label.venue"/></form:label></td>
                    <td><form:input path="venue"/></td>
                </tr>
                <tr>
                    <td><form:label path="city"><spring:message code="label.city"/></form:label></td>
                    <td><form:input path="city" /></td>
                </tr>
                <tr>
                    <td><form:label path="state"><spring:message code="label.state"/></form:label></td>
                    <td><form:input path="state" /></td>
                </tr>
                <tr>
                    <td><form:label path="seatPerLevel"><spring:message code="label.seatPerLevel"/></form:label></td>
                    <td><form:checkbox path="seatPerLevel" /></td>
                </tr>
                <tr>
                    <td><form:label path="seatingType"><spring:message code="label.seatingType"/></form:label></td>
                    <td>
                        <form:select path="seatingType">
                            <form:option value="NONE" label="--- Select ---"/>
                            <form:options items="${allSeatingTypes}" />
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td><form:label path="rowMetaName"><spring:message code="label.rowMetaName"/></form:label></td>
                    <td>
                        <form:select path="rowMetaName">
                            <form:option value="NONE" label="--- Select ---"/>
                            <form:options items="${allRowMetaNames}" />
                        </form:select>
                    </td>
                </tr>
			</table>
		</td>
	</tr>
</table>
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
</form:form>
</div>
</div>
