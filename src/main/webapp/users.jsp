<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.javawebinar.topjava.web.SecurityUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Users</h2>
<hr>
<form method="post" action="users">
    <b>Meals of&nbsp;</b>
    <select name="userId">
        <option value="1">User</option>
        <option value="2">Admin</option>
    </select>
    <button type="submit">Select</button>
</form>
</body>
</html>