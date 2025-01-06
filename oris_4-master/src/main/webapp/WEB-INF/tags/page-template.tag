<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ tag language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ attribute name="title" required="true" %>
<%@ attribute name="cssFile" required="true" %>



<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">
    <title>${title}</title>
    <link rel="stylesheet" href="../../css/common.css">
    <link rel="stylesheet" href="../../css/${cssFile}">
</head>
<body>
<jsp:include page="/jsp/parts/_nav.jsp"/>
<main class="main-content">
    <jsp:doBody/>
</main>
<jsp:include page="/jsp/parts/_footer.jsp"/>
</body>
</html>