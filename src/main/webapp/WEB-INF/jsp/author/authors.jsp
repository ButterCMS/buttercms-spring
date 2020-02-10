<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello! This is Java ButterCMS example!</title>
</head>
<body>
<h2 class="hello-title">Hello! This is Java ButterCMS example!</h2>
<h3>Here is a list of Authors</h3>
<ul>
    <c:forEach items="${authors}" var="author">
        <li><a href="/author/${author.slug}">${author.firstName} ${author.lastName}</a></li>
    </c:forEach>
</ul>
</body>
</html>