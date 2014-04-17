<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags"%>
<html>
<head>
<mytags:style/>
<mytags:menu/>
</head>
<body>
<c:out value="${batchResults}"/>
<mytags:footer/>
</body>
</html>
