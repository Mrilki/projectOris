<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:page-template title="Reports" cssFile="adminReport.css">
    <div class="reports-container">
        <h1 class="reports-title">Report List</h1>

        <c:if test="${empty reports}">
            <div class="no-reports">
                <p>There are no reports</p>
            </div>
        </c:if>

        <div class="reports-grid">
            <c:forEach var="reportWithImages" items="${reports}" varStatus="status">
                <div class="report-card">
                    <div class="report-content">
                        <div class="report-header">
                            <h2 class="report-number">Report #${reportWithImages.report.id}</h2>
                            <div class="post-info">
                                <span class="post-id">Post ID: ${reportWithImages.report.day.id}</span>
                                <span class="post-date">Date: ${reportWithImages.report.day.date}</span>
                            </div>
                        </div>

                        <div class="post-content">
                            <c:if test="${null != reportWithImages.image}">
                                <div class="post-image">
                                    <img src="${pageContext.request.contextPath}/uploaded/files?id=${reportWithImages.image.id}"
                                         alt="Post Image">
                                </div>
                            </c:if>
                            <p class="post-text">${reportWithImages.report.day.text}</p>
                        </div>

                        <div class="report-details">
                            <div class="report-reason">
                                <h3>Reason for the report</h3>
                                <p>${reportWithImages.report.reason}</p>
                            </div>
                            <div class="report-description">
                                <h3>Description</h3>
                                <p>${reportWithImages.report.text}</p>
                            </div>
                        </div>

                        <form action="${pageContext.request.contextPath}/adminReport" method="post" class="report-actions">
                            <input type="hidden" name="reportId" value="${reportWithImages.report.id}">
                            <div class="form-buttons">
                                <button type="submit" name="action" value="accept" class="form-button accept-button">Accept</button>
                                <button type="submit" name="action" value="reject" class="form-button reject-button">Reject</button>
                            </div>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="form-buttons" style="margin-top: 30px;">
            <a href="${pageContext.request.contextPath}/profile/${sessionScope.username}" class="form-button">
                Back to Profile
            </a>
        </div>
    </div>
</t:page-template>