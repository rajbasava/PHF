<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" src="<c:url value="/resources/script/jquery-1.9.1.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/script/jquery-ui-1.10.4.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/script/jquery-migrate-1.1.0.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/script/jquery.ui.menubar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/script/flexigrid.pack.js"/>"></script>
<style>
	TABLE.Table
	{
		 Border:1px Groove;
	}
	TR.Header
	{
		Font-Size:24pt;
		background-color:MidnightBlue;
		Color: LightSteelBlue;
		text-shadow: 2px 2px SlateBlue;
	}
	TR.Caption
	{
		backGround-Color:SlateGray;
		Color:Ivory;
	}
	TR.AlternateRow
	{
		background-color:#E1E1E1;
	}
	TH.ManuPad
	{
		Color:Black;
		BackGround-Color:#E9DCEC;
		font:normal 9px;
	}
	a:link    
	{
		color:Blue;
	}
	a:visited 
	{
		color:Blue;
	}
	a:hover   
	{
		color:red;
	}
	a:active  
	{
		color:yellow;
	}
	#addParticipant
	{
		border:1px Solid Black;
		background-color:black;
		color:Black;
	}
	.Select
	{
		width:98%;
	}
	.Input
	{
		width:98%;
		border:1ps Solid Black;
	}
</style>
 <script type="text/javascript">
    $(function() {
        $("#menu").menubar();
		$("#menu").css("font-size", "11px");
    });
 </script>

<div class="Link">
    <table align="center" border="0" class="YLink" cellPadding="0" cellSpacing="0" width="100%">
        <tr>
           <th width="10%"><img src="<c:url value="/resources/img/gmcks.png"/>" height="97" width="114"/></th>
		   <th width="80%"><img src="<c:url value="/resources/img/New-Pranic-Logo.png"/>" height="97" width="165"/></th>
		   <th width="10%"> Welcome <c:out value="${user.name}"/></th>
        </tr>   
		<tr height="4px"><td></td></tr>
		<tr>
		 <th colspan="3">
		     <ul id="menu" class="menubar">
                <li><a href="welcome.htm">Home</a></li>
                <li><a href="#">Search</a>
                     <ul>
                        <c:if test="${user.access.admin}" >
                            <li><a href="searchParticipants.htm">Participants</a></li>
                        </c:if>
                        <li><a href="search.htm">Registrations</a></li>
                     </ul>
                </li>
                <c:if test="${user.access.admin}" >
                    <li><a href="#">Add</a>
                         <ul>
                           <li><a href="showAddEvent.htm">Event</a></li>
                           <li><a href="showAddParticipantCourse.htm">Participant Course</a></li>
                         </ul>
                    </li>
                </c:if>
                <c:if test="${user.access.spotRegVolunteer || user.access.admin}" >
                   <li><a href="register.htm">Spot Registration</a></li>
                </c:if>
                <c:if test="${user.access.admin}" >
                    <li><a href="#">Manage</a>
                         <ul>
                           <li><a href="volunteer.htm">Volunteers</a></li>
                           <li><a href="showManageEvents.htm">Events</a></li>
                           <li><a href="showManageTrainers.htm">Trainers</a></li>
                           <li><a href="referenceGroup.htm">Reference Groups</a></li>
                         </ul>
                    </li>
                </c:if>
                <c:if test="${user.access.admin}" >
                    <li><a href="#">Import/Export</a>
                         <ul>
                           <li><a href="import.htm">Import</a></li>
                           <li><a href="rptRegistrations.htm">Export Registrations</a></li>
                           <li><a href="rptPayments.htm">Export Payments</a></li>
                         </ul>
                    </li>
                </c:if>
                <li><a href="logout.htm">Sign Out</a></li>
             </ul>
            </th>
	    </tr>
    </table>
</div>