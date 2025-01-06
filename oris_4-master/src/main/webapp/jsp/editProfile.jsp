<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:page-template title="EditProfile" cssFile="editProfile.css">
    <div class="form-container">
        <h1 class="form-title">Edit Profile</h1>

        <form class="form" method="post" action="${pageContext.request.contextPath}/editProfile">
            <div class="form-group">
                <div class="form-field-container">
                    <label class="form-label" for="firstName">Name</label>
                    <input class="form-input" type="text" id="firstName" name="firstName"
                           value="${profile.firstName}" required>
                </div>
            </div>

            <div class="form-group">
                <div class="form-field-container">
                    <label class="form-label" for="lastName">Surname</label>
                    <input class="form-input" type="text" id="lastName" name="lastName"
                           value="${profile.lastName}" required>
                </div>
            </div>

            <div class="form-group">
                <div class="form-field-container">
                    <label class="form-label" for="age">Age</label>
                    <input class="form-input" type="number" id="age" name="age"
                           value="${profile.age}" required min="1" max="120">
                </div>
            </div>

            <div class="form-buttons">
                <button type="submit" class="form-button">Save</button>
                <a href="${pageContext.request.contextPath}/profile/${profile.username}"
                   class="form-button">Return</a>
            </div>
        </form>
    </div>
</t:page-template>
