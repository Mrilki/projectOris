<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:page-template title="Comment" cssFile="profile.css">
    <div class="comment-section">
        <button class="comment-button" data-day-id="${dayId}">comments</button>
        <div class="comments-container" data-day-id="${dayId}"></div>
        <input type="hidden" name="userId" value="${sessionScope.id}">
        <input type="text" class="comment-input" id="text" placeholder="Write a comment..." required>
        <button class="send-comment-button" data-day-id="${dayId}">Send</button>
    </div>
</t:page-template>