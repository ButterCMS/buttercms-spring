<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Author: ${author.firstName} ${author.lastName}</title>
</head>
<body>
<h2 class="hello-title">Hello! This is Java ButterCMS example!</h2>
<h3>Here is a Author's details</h3>
<dl>
    <dt>Picture</dt>
    <dd><img src="${author.profileImage}" height="100px"/></dd>
    <dt>Name</dt>
    <dd>${author.firstName} ${author.lastName}</dd>
    <dt>Email</dt>
    <dd><a href="mailto:${author.email}">${author.email}</a></dd>
    <dt>Bio</dt>
    <dd>${author.bio}</dd>
    <dt>Title</dt>
    <dd>${author.title}</dd>
    <dt>Bio</dt>
    <dd>${author.bio}</dd>
</dl>
<h3>Recent posts</h3>
<ul>
    <c:forEach items="${author.recentPosts}" var="post">
        <li><a href="/blog/${post.slug}">${post.title} </a></li>
    </c:forEach>
</ul>
</body>
</html>