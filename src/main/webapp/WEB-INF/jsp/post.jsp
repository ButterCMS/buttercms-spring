<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<meta>
<title>post Title</title>
<meta property="og:title" content="${post.seoTitle}"/>
<meta property="og:image" content="${post.featuredImage}"/>
<meta property="og:description" content="${post.metaDescription}"/>
</head>
<body>
<h1>${post.title}</h1>
<ul>
    <li>Author: ${post.author.firstName} ${post.author.lastName}</li>
    <li>Published: ${post.published}</li>
    <li>Tags:
        <c:forEach items="${post.tags}" var="tag">
            ${tag.name}
        </c:forEach>
    </li>
</ul>
<img src="${post.featuredImage}"/>
<div>
    <c:out value="${post.body}" escapeXml="false"/>
</div>

</body>
</html>