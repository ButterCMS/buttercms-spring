<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<meta>
<title>${blog.seoTitle}</title>
<meta property="og:title" content="${blog.seoTitle}"/>
<meta property="og:image" content="${blog.featuredImage}"/>
<meta property="og:description" content="${blog.metaDescription}"/>
</head>
<body>
<h1>${blog.title}</h1>
<ul>
    <li>Author: ${blog.author.firstName} ${blog.author.lastName}</li>
    <li>Published: ${blog.published}</li>
    <li>Tags:
        <c:forEach items="${post.tags}" var="tag">
            ${tag.name}
        </c:forEach>
    </li>
</ul>
<img src="${blog.featuredImage}"/>
<div>
    <c:out value="${blog.body}" escapeXml="false"/>
</div>

</body>
</html>