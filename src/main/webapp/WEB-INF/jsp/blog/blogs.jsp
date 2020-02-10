<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello! This is Java ButterCMS example!</title>
</head>
<body>
<h2 class="hello-title">Hello! This is Java ButterCMS example!</h2>
<h3>Here is a list of Posts</h3>
<c:if test="${not empty tag}">
    <h4>Tag: ${tag}</h4>
</c:if>
<c:if test="${not empty category}">
    <h4>Category: ${category}</h4>
</c:if>
<ul>
    <c:forEach items="${blogs}" var="blog">
        <li><a href="/blog/${blog["slug"]}">${blog["title"]}</a></li>
    </c:forEach>
</ul>
</body>
</html>