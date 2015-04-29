<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<html>
<head>
	<title>Arhatic Yoga Retreat - Registered Participant Summary</title>
    <mytags:style/>
    <mytags:menu/>
    <script type="text/javascript">
        $(document).ready(function(){
            $(function() {
                $("a#nextRegistration").button();
                $("a#nextRegistration").css("font-size", "11px");
                $("a#nextRegistration").click(function() {
                     $("#registrationSummary").get(0).setAttribute('action', 'register.htm');
                     $("#registrationSummary").submit();
                });

                $("a#searchRegistration").button();
                $("a#searchRegistration").css("font-size", "11px");
                $("a#searchRegistration").click(function() {
                     $("#registrationSummary").get(0).setAttribute('action', 'search.htm');
                     $("#registrationSummary").submit();
                });
            });
	    });
	</script>	
</head>
<body>
<h2 align="center">Registration Summary</h2>

<table align="center" cellspacing="2" cellpadding="2" width="60%" style="border:2px violet solid; border-radius: 15px; padding: 2px">
    <tr>
		<td style="font-family: verdana; color:red; font-size:30px; text-align:center; border-bottom:1px violet solid; padding:10px">
			<c:out value="${registeredParticipant.registration.event.name}"/>
		</td>
	</tr>
	<tr>
		<td style="display:inline; font-family: verdana; color:green; font-size:20px; text-align:center; padding: 2px">
			<c:out value="${registeredParticipant.registration.participant.name}"/>
			<c:if  test="${registeredParticipant.registration.isVIP()}">
				<p style="display:inline; font-family: verdana; font-weight:bold; color:gold; font-size:26px; border:1px gold solid; border-radius: 5px; padding: 2px">&#9733;</p>
			</c:if>
		</td>
	</tr>
	<c:if  test="${not empty registeredParticipant.registration.participant.email}">
		<tr>  			
			<td style="display:inline; font-family: verdana;  color:green; font-size:20px; text-align:center; padding: 2px">
				&#9993;&nbsp;<c:out value="${registeredParticipant.registration.participant.email}"/>
			</td>
		</tr>	
	</c:if>
	<c:if  test="${not empty registeredParticipant.registration.participant.mobile}">
		<tr>  			
			<td style="display:inline; font-family: verdana; color:green; font-size:20px; text-align:center; padding: 2px">
				&#9742;&nbsp;<c:out value="${registeredParticipant.registration.participant.mobile}"/>
			</td>
		</tr>	
	</c:if>
	<tr>  			
		<td style="font-family: verdana; color:green; font-size:20px; text-align:center; padding: 2px; border-bottom:1px violet solid; ">
			<c:out value="${registeredParticipant.registration.foundation.shortName}"/>
		</td>
	</tr>	
	<tr>
		<td>
			<table width="100%">	
				<tr>
					<td width="50%" style="display:inline; font-family: verdana; color:green; font-size:20px; text-align:center; padding: 2px">
						Reg.Id#<c:out value="${registeredParticipant.registration.id}"/>
					</td>
					<td style="font-weight:bold; font-family: verdana; color:green; font-size:20px; text-align:center; padding: 2px">
						
						<c:out value="${registeredParticipant.registration.getLevelName()}"/>

					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr> 
		<td>
			<table align="center" cellpadding="1" cellspacing="1" width="40%" style="border:3px violet solid; border-radius: 15px; padding: 2px">
				<tr style="font-family: verdana; font-size:24px;color:#ff0000; text-align:center;">
					<td>
						<c:out value="${registeredParticipant.registration.foodType()}"/>
					</td>
				</tr>
				<c:if  test="${!empty registeredParticipant.registration.seats}">
					<c:if test="${registeredParticipant.displaySeat}" >
						<c:forEach items="${registeredParticipant.registration.seats}" var="seat">
							<c:if  test="${seat.seat != null}">
							<tr style="font-family: verdana; font-size:24px;color:#ff0000; text-align:center;">
								<td>
									Seat No#&nbsp;<c:out value="${seat.alpha}"/>&nbsp;<c:out value="${seat.seat}"/>
								</td>
							</tr>
							<tr style="font-family: verdana; font-size:24px;color:#ff0000; text-align:center;">
								<td>
									Gate No#&nbsp;<c:out value="${seat.gate}"/>
								</td>
							</tr>
							<tr style="font-family: verdana; font-size:24px;color:#ff0000; text-align:center;">
								<td>
									<c:out value="${seat.foodCounter}"/>                        
								</td>
							</tr>
							</c:if>
						</c:forEach>
					</c:if>
				</c:if>
			</table>
		</td>
	</tr>
</table>
<table align="center" cellspacing="2" cellpadding="2" width="60%" style="padding: 10px">
	<tr align="center">
		<td align="center">
            <form id="registrationSummary" method="post" action="">
				<c:if test="${user.access.spotRegVolunteer || user.access.admin}">
					<a id="nextRegistration" href="#">Next Registration</a>
				</c:if>

                
                <a id="searchRegistration" href="#">Search Registrations</a>
            </form>
		</td>
	</tr>
</table>

<mytags:footer/>
</body>
</html>
