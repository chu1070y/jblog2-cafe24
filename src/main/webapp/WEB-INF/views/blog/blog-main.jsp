<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header_blog.jsp" />
		<div id="wrapper">
			<div id="content">
			<c:forEach items="${posts }" var="post" varStatus="status">
			
				<c:if test="${status.count == 1 }">
				<div class="blog-content">
					<h4>${post.title }</h4>
					<p>
						${post.content }
					<p>
				</div>
				<hr/>
				<ul class="blog-list">
				</c:if>
				
					<li><a href="">${post.title }</a> <span>${post.regDate }</span>	</li>
					
			</c:forEach>
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}/${blogInfo.logo }">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
			<c:forEach items="${categorys }" var="category" varStatus="status">
				<li><a href="">${category.title }(${category.count })</a></li>
			</c:forEach>
			</ul>
		</div>
		
		<c:import url="/WEB-INF/views/includes/footer_blog.jsp" />
	</div>
</body>
</html>