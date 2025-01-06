<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Profile</title>
    <meta name="context-path" content="${pageContext.request.contextPath}">
    <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
    <script src="/js/profile.js"></script>
    <link rel="stylesheet" href="../css/common.css">
    <link rel="stylesheet" href="../css/profile.css">
</head>
<body>
<jsp:include page="/jsp/parts/_nav.jsp"/>
<div class="profile-container" data-user-id="${sessionScope.id}">
    <div class="profile-header">
        <h1 class="profile-username">@ ${profile.username}</h1>

        <div class="profile-info">
            <p class="profile-name">Name: <span>${profile.firstName}</span></p>
            <p class="profile-surname">Surname: <span>${profile.lastName}</span></p>
            <p class="profile-age">Age: <span>${profile.age}</span></p>
        </div>
    </div>

    <div class="actions-container">
        <c:if test="${isOwner}">
            <div class="button-container">
                <a href="${pageContext.request.contextPath}/editProfile" class="edit-profile-link">
                    Edit profile
                </a>
            </div>
        </c:if>

        <div class="button-container">
            <a href="${pageContext.request.contextPath}/friends?id=${profile.id}" class="friends-link">
                <h3>Friend</h3>
            </a>
        </div>

        <c:if test="${role == 'ADMIN'}">
            <div class="button-container">
                <a href="${pageContext.request.contextPath}/adminReport" class="admin-button">
                    View reports
                </a>
            </div>
        </c:if>
        <c:if test="${isOwner}">
            <div class="button-container">
                <a href="${pageContext.request.contextPath}/addDay" class="add-post-button">
                    Add new post
                </a>
            </div>
        </c:if>
    </div>

    <div class="posts-section">
        <div class="posts-header">
            <h2>Posts</h2>
        </div>

        <div class="posts-list">
            <c:forEach items="${days}" var="dayWithImage">
                <article class="post" data-post-id="${dayWithImage.day.id}">
                    <div class="post-header">
                        <time class="post-date" datetime="${dayWithImage.day.date}">
                                ${dayWithImage.day.date}
                        </time>

                        <c:if test="${null != dayWithImage.image }">
                            <figure class="post-image">
                                <img src="${pageContext.request.contextPath}/uploaded/files?id=${dayWithImage.image.id}"
                                     alt="image"
                                     loading="lazy">
                            </figure>
                        </c:if>

                        <div class="post-content">
                            <p>${dayWithImage.day.text}</p>
                        </div>
                    </div>

                    <div class="post-footer">

                        <div class="button-container">
                            <c:if test="${isOwner}">
                                <a href="${pageContext.request.contextPath}/editDay/${dayWithImage.day.id}"
                                   class="edit-button">
                                    Edit post
                                </a>
                            </c:if>
                        </div>

                        <div class="button-container">
                            <c:if test="${!isOwner}">
                                <a href="${pageContext.request.contextPath}/report/${dayWithImage.day.id}"
                                   class="report-button">
                                    Send report
                                </a>
                            </c:if>
                        </div>

                        <div class="button-container">
                            <button class="show-comments-button"
                                    data-day-id="${dayWithImage.day.id}">
                                Comments
                            </button>
                        </div>

                        <div class="comments-section"
                             id="comments-${dayWithImage.day.id}"
                             style="display: none;">
                            <div class="comments-list"
                                 id="comments-list-${dayWithImage.day.id}">

                            </div>

                            <form class="add-comment-form"
                                  id="add-comment-form-${dayWithImage.day.id}">
                                <div class="comment-input-group">
                                    <input type="text"
                                           name="commentText"
                                           placeholder="Write a comment..."
                                           required
                                           maxlength="500">
                                    <button type="submit">Отправить</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </article>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>