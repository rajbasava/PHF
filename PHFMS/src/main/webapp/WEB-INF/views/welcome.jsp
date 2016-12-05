<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>
<html>
<head>
<mytags:style/>
<mytags:menu/>
  <script>
      function overrideLogin(){
          $("#user").get(0).setAttribute('action', 'overrideLogin.htm');
          $("#user").submit();
      }

      function logout(){
          $("#user").get(0).setAttribute('action', 'logout.htm');
          $("#user").submit();
      }

      function showDialog(){
          $( "#dialog-confirm" ).dialog('open');
      }

      $(document).ready(function() {
          $( "#dialog-confirm" ).dialog({
              resizable: false,
              autoOpen: false,
              height: "auto",
              width: 400,
              modal: true,
              buttons: {
                  "Ok": function() {
                      $( this ).dialog( "close" );
                      overrideLogin();
                  },
                  Cancel: function() {
                      $( this ).dialog( "close" );
                      logout();
                  }
              }
          });

          if ($("#status").val() == '4')
          {
              showDialog();
          }
      });
  </script>
</head>
<body>

<form method="post" action="" id="user">
<input type="hidden" id="status" value=<c:out value="${user.status}"/> />


<div id="dialog-confirm" title="Already Logged-in?">
  <p><span class="ui-icon ui-state-error" style="float:left; margin:12px 12px 20px 0;"></span>User is already Logged-in!!! Do you want to continue this session?</p>
</div>

</form>

<h2 align="center">Welcome!!!</h2>
<h3 align="center">Onsite Registration</h3>
<mytags:footer/>
</body>
</html>
