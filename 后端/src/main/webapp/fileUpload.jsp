<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">	
<title>Insert title here</title>
</head>
<body>
<h1>关于文件上传</h1>
	<form action="/day07/user/fileUpload" method="post" enctype="multipart/form-data">
		<input type="text" name="name">
		<input type="file" name="file">
		<input type="submit" value="上传"> 
		
	
	</form>
	
	
	<a href="/day07/user/download?filename=aaa.txt">下载</a>

	
	

</body>
</html>