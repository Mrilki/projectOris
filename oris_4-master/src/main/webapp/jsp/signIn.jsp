<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Authorization</title>
    <link rel="stylesheet" href="../css/signIn.css">
</head>
<body>
<div class="form-container">
    <h1 class="form-title">Sign In</h1>

    <c:if test="${param.error != null}">
        <div class="error-message">
            <c:choose>
                <c:when test="${param.error == 'emptyFields'}">
                    Please fill in all fields
                </c:when>
                <c:when test="${param.error == 'invalidCredentials'}">
                    Invalid username or password
                </c:when>
            </c:choose>
        </div>
    </c:if>

    <form class="form" action="${pageContext.request.contextPath}/signIn" method="post">
        <div class="form-group">
            <div class="form-field-container">
                <label class="form-label" for="username">Username</label>
                <input class="form-input" type="text" id="username" name="username" required>
            </div>
        </div>

        <div class="form-group">
            <div class="form-field-container">
                <label class="form-label" for="password">Password</label>
                <input class="form-input" type="password" id="password" name="password" required>
            </div>
        </div>

        <div class="form-buttons">
            <button type="submit" class="form-button">Войти</button>
            <a href="${pageContext.request.contextPath}/signUp" class="form-button">Регистрация</a>
        </div>
    </form>
</div>

<script>
    document.getElementById('signInForm').addEventListener('submit', function(event) {
        const password = document.getElementById('password').value;
        if (password.length < 6) {
            alert('The password must be at least 6 characters long.');
            event.preventDefault();
        }
    });
</script>
</body>
</html>