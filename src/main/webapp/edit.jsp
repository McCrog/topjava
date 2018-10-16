<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <title>${meal.description}</title>
</head>
<body>
<section>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value="${meal.id}">
        <table border="1" cellpadding="8" cellspacing="0">
            <tr>
                <th>Дата</th>
                <th>Время</th>
                <th>Описание</th>
                <th>Калории</th>
            </tr>
            <td><input type="date" name="date" size=50 value="${meal.dateTime.toLocalDate()}"></td>
            <td><input type="time" name="time" size=50 value="${meal.dateTime.toLocalTime()}"></td>
            <td><input type="text" name="description" size=50 value="${meal.description}"></td>
            <td><input type="text" name="calories" size=50 value="${meal.calories}"></td>
            </tr>
        </table>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
</body>
</html>
