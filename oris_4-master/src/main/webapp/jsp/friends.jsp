<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:page-template title="Friends" cssFile="friends.css">
    <div class="friends-container">
        <div class="friends-list">
            <h1>Friends</h1>
            <div class="friends-grid">
                <c:forEach items="${friends}" var="friend">
                    <div class="friend-card">
                        <a href="/profile/${friend.username}">
                            <p>
                                    ${friend.username}<br>
                                    ${friend.firstName} ${friend.lastName}
                            </p>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
        <c:if test="${isOwner}">
            <div class="add-friend-section">
                <h3>Add friend</h3>
                <form action="${pageContext.request.contextPath}/friends" method="post">
                    <input type="text" name="username" placeholder="Send friend username" required>
                    <button type="submit">Send friend request</button>
                </form>

                <c:if test="${not empty error}">
                    <div class="error-message">${error}</div>
                </c:if>

                <c:if test="${not empty success}">
                    <div class="success-message">${success}</div>
                </c:if>
            </div>
            <div class="friends-requests">
                <h3>Friend requests</h3>
                <div class="friends-grid">
                    <c:forEach items="${friendRequests}" var="friend">
                        <div class="friend-card">
                            <a href="/profile/${friend.username}">
                                <p>${friend.username}<br>
                                        ${friend.firstName} ${friend.lastName}</p>
                            </a>
                            <div class="request-actions">
                                <form action="/friends" method="post">
                                    <input type="hidden" name="action" value="accept">
                                    <input type="hidden" name="requestId" value="${friend.id}">
                                    <button type="submit" class="accept-btn">Accept</button>
                                </form>
                                <form action="/friends" method="post">
                                    <input type="hidden" name="action" value="reject">
                                    <input type="hidden" name="requestId" value="${friend.id}">
                                    <button type="submit" class="reject-btn">Reject</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>
    </div>
</t:page-template>