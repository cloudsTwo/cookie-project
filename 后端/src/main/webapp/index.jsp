<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/day07/static/css/index.css">
</head>
<body>

<a href="/day07/user/findAllArticle">查找所有文章</a>
<a href="/day07/user/findOneArticleById?article_id=1">查找一篇文章</a>
<a href="/day07/user/deleteArticle?article_id=6">删除一篇文章</a>
<a href="/day07/user/deleteCm?cmId=2">删除一个评论</a>
<a href="/day07/user/findAllCm">查看所有评论</a>
<a href="/day07/user/findAllUser">查看所有用户</a>
<a href="/day07/user/findOneUser?id=1">查看一个用户</a>
<a href="/day07/user/findAllCmByArticle?article_Id=1">查看一个篇文章的所有评论</a>
<br />
<form action="/day07/user/addArticle" method="POST">
文章标题: <input type="text" name="postTitle" value="">
<br />
提交语言地址: <input type="text" name="postAudio" value=""/>
<br />
提交作者: <input type="text" name="postAuthor" value=""/>
<br />
提交内容: <input type="text" name="postAudio" value=""/>
<input type="submit" value="新增文章"  />
</form>
<br />
<br />
<form action="/day07/user/UploadPhoto" method="POST">
照片地址: <input type="text" name="regPhoto" value="">
<br />
用户id: <input type="text" name="id" value=""/>
<input type="submit" value="新增照片"  />
</form>
<br />
<form action="/day07/user/addCm" method="POST">
用户id: <input type="text" name="userId" value="">
<br />
文章id: <input type="text" name="article_Id" value=""/>
<br />
评论内容: <input type="text" name="cmText" value=""/>
<br />
评论语言合成: <input type="text" name="cmAudio" value=""/>
<input type="submit" value="新增评论"  />
</form>

<br />
<form action="/day07/user/changeUser" method="POST">
id: <input type="text" name="id" value="">
<br />
用户名: <input type="text" name="username" value="">
<br />
性别: <input type="text" name="regSex" value=""/>
<br />
年龄: <input type="text" name="regAge" value=""/>
<br />
邮箱: <input type="text" name="regEmial" value=""/>
<br />
权限: <input type="text" name="admin" value=""/>
<input type="submit" value="修改用户信息"  />
</form>
<br />
<form action="/day07/user/UpdateCm" method="POST">
评论id: <input type="text" name="cmId" value="">
<br />
评论内容: <input type="text" name="cmText" value="">
<br />
<input type="submit" value="编辑评论"  />
</form>

<%-- 	<h2>这里是hello world的测试页面</h2>
	测试的数据为 ${user.username }
	<a href="/day07/login.jsp">登录</a>
	<img alt="图片" src="/day07/static/img/52262364_p0.jpg">
	<span id="span">测试css</span> --%>
</body>
</html>