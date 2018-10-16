<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>Дата/Время</th>
            <th>Описание</th>
            <th>Калории</th>
            <th>Exceed</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"/>
            <c:choose>
                <c:when test="${meal.exceed}">
                    <tr id="exceed">
                </c:when>
                <c:when test="${!meal.exceed}">
                    <tr id="not_exceed">
                </c:when>
            </c:choose>
            <td>${meal.id}</td>
            <td>${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>${meal.exceed}</td>
            <td><a href="meals?id=${meal.id}&action=edit">Edit</a></td>
            <td><a href="meals?id=${meal.id}&action=delete">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <br/>
    <a href="meals?action=add">Добавить</a>
</section>
</body>
</html>
