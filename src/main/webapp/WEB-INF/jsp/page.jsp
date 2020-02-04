<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<meta>
<title>Page Title</title>
<meta property="og:title" content="${page.openGraphTitle}"/>
<meta property="og:image" content="${page.customerLogo}"/>
</head>
<body>
<h1>${page.headline}</h1>
<img src="${page.customerLogo}"/>
<div>
    <c:out value="${page.testimonial}" escapeXml="false"/>
</div>

</body>
</html>