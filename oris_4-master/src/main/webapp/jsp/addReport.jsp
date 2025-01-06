<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:page-template title="Add new report" cssFile="addReport.css">
    <div class="form-container">
        <h1 class="form-title">Send Report</h1>

        <c:if test="${not empty param.error}">
            <div class="error-message">
                <c:choose>
                    <c:when test="${param.error == 'emptyFields'}">
                        Please fill in all required fields.
                    </c:when>
                    <c:otherwise>
                        An error occurred while sending the report. Please try again later.
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>

        <div class="report-content">
            <form action="${pageContext.request.contextPath}/report" method="post" class="form" id="reportForm">
                <input type="hidden" name="dayId" value="${dayId}">
                <input type="hidden" name="pathInfo" value="${pathInfo}">

                <div class="form-group">
                    <div class="form-field-container">
                        <label class="form-label" for="reason">Reason for the report</label>
                        <input class="form-input" type="text" id="reason" name="reason" required>
                    </div>
                </div>

                <div class="form-group">
                    <div class="form-field-container">
                        <label class="form-label" for="text">Report description</label>
                        <textarea class="form-input form-textarea" id="text" name="text" required></textarea>
                    </div>
                </div>

                <div class="form-buttons">
                    <button type="submit" class="form-button submit-button">Send Report</button>
                    <a href="javascript:history.back()" class="form-button cancel-button">Cancel</a>
                </div>
            </form>
        </div>
    </div>


    <script>
        document.getElementById('reportForm').addEventListener('submit', function (event) {
            const reason = document.getElementById('reason').value.trim();
            const text = document.getElementById('text').value.trim();

            if (reason === '' || text === '') {
                alert('Please fill in all required fields.');
                event.preventDefault();
            }
        });
    </script>
</t:page-template>