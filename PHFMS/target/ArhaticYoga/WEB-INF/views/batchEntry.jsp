<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>

<html>
<head>
	<title>Data entry form</title>
    <mytags:style/>
	<mytags:menu/>
</head>
<body>
	<h3 align="center">Data entry screen</h3>
	<p align="center">

	<form action="batchEntry.html" method="post">
	<table class="blue">
		<tr>
			<th align="right">Please copy/paste the <br>XML content</th>
			<td valign="middle" class="info">
       			<textarea name="content" class="info" rows="30" cols="80"></textarea>
       		</td>
		</tr>
	</table>

	<tr>
		<input type=hidden name="type" value="nyce">
		<input type="submit" value="Submit">
	</tr>

	</form>
	<mytags:footer />
</body>
</html>
