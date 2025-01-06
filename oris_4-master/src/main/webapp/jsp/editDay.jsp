<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit Day</title>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="../js/editDay.js"></script>
    <link rel="stylesheet" href="../css/common.css">
    <link rel="stylesheet" href="../css/editDay.css">
</head>

<body>
<div class="form-container">
    <h2>Edit post</h2>

    <div class="edit-content">
        <input type="hidden" id="dayId" value="${day.id}">

        <div class="form-group">
            <label for="date">Date:</label>
            <input type="date" id="date" name="date" value="${day.date}" required>
        </div>

        <div id="imageSection">
            <c:choose>
                <c:when test="${not empty image}">
                    <div class="existing-image" id="existingImageContainer">
                        <p>Current image:</p>
                        <img src="${pageContext.request.contextPath}/uploaded/files?id=${image.id}"
                             alt="Изображение поста" width="300" id="existingImage">
                        <button type="button" class="delete-button" id="deleteImageButton">Delete image</button>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="form-group" id="addImageContainer">
                        <label for="image">Add image:</label>
                        <input type="file" name="image" id="image">
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="form-group">
            <label for="text">Content:</label>
            <textarea id="text" name="text" required>${day.text}</textarea>
        </div>

        <div class="button-group">
            <button type="button" class="submit-button" id="saveButton">Save changes</button>
            <a href="${pageContext.request.contextPath}/profile/${sessionScope.username}"
               class="cancel-button">Back to Profile</a>
        </div>
    </div>
</div>
</body>
</html>