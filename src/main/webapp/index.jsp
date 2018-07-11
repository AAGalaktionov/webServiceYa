<%@page contentType="text/html" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Поиск по ключевым словам</title>
</head>

<body>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<h1>Веб-сервис поиска по заданным ключевым словам</h1>
<form action = "search" method = "GET">
    <table border = "0">

        <tr>
            <td><tb>Введите запрос</tb>
            <td><input type = "text" name = "query"
                       value = "" size = "70"/></td>
        </tr>

        <tr>
            <td colspan = "5"><input type = "submit" value = "Поиск"/></td>
        </tr>
    </table>
</form>
</body>
</html>