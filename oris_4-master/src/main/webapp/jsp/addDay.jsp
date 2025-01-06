<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<t:page-template title="Add new post" cssFile="addDay.css">
<div class="form-container">
    <h2>Add new post</h2>
    <form action="${pageContext.request.contextPath}/addDay" method="post" enctype="multipart/form-data">
        <input type="hidden" name="userId" value="${sessionScope.id}">


        <div class="form-group">
            <label for="date">Date:</label>
            <input type="date" id="date" name="date" required
                   min="1950-01-01"
                   max="2050-12-31">

        </div>

        <div class="form-group">
            <label for="text">Content:</label>
            <textarea id="text" name="text" required
                      minlength="1"
                      maxlength="1000"></textarea>
        </div>>


        <input type="file" name="file">


        <div class="button-group">
            <button type="submit" class="submit-button">Save</button>
            <a href="${pageContext.request.contextPath}/profile/${sessionScope.username}"
               class="cancel-button">Cancel</a>
        </div>
    </form>
</div>
</t:page-template>