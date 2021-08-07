<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="/day07/user/login" method="POST">
用户名: <input type="text" name="username" value="${param.username}">
<br />
密码: <input type="password" name="password" value="${param.password}"/>
<input type="submit" value="提交"  />
</form>
<h1>分界=============================</h1>
<form action="/day07/user/registered" method="POST">
用户名: <input type="text" name="username" value="">
<br />
密码: <input type="password" name="password" value=""/>
<br />
性别: <input type="text" name="regSex" value=""/>
<br />
年龄: <input type="text" name="regAge" value=""/>
<br />
邮箱: <input type="text" name="regEmial" value=""/>
<input type="submit" value="注册"  />
</form>
</body>
</html>