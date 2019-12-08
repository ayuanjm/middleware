<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>天猫首页</title>
</head>
<body>

<h1>天猫首页</h1>

<form action="">
    当前登录的用户为： <span>admin</span> <br>
    <%--本质登出实在服务器实现的--%>
    <a href="${getServerLogOutUrl}">注销</a>
</form>

</body>
</html>

