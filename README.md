# buttercms-spring
Java Spring starter project integrated with ButterCMS

## Install

Add [com.buttercms:buttercmsclient:1.6](https://search.maven.org/artifact/com.buttercms/buttercmsclient/1.6/jar) dependence to you existing java project project.

### Maven

**pom.xml**
```
<dependencies>
...
    <dependency>
      <groupId>com.buttercms</groupId>
      <artifactId>buttercmsclient</artifactId>
      <version>1.6</version>
    </dependency>
...
</dependencies>    
```

### Gradle

**build.gradle**
```
dependencies {
    implementation 'com.buttercms:buttercmsclient:1.6'
}
```

In case you are starting new project from scratch we recommend to use [Spring Initializr](https://start.spring.io/)
or [Spring Boot CLI](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-cli.html) to generate your new project.
## Quickstart

Add you API token to `application.properties`:
```properties
# ...
buttercms.key=you_api_key
# ...
```

Define ButterCMS Bean in you AppConfiguration Class:

```java
@Configuration
public class AppConfig {

  @Value("${buttercms.key}")
  private String butterCMSKey;

  @Bean
  public IButterCMSClient butterCMSClient() {
    return new ButterCMSClient(butterCMSKey);
  }
}
```

You can than inject given ButterCMS client in to any of you other Beans/Controllers:

```java
@Controller
public class BlogController {
  private IButterCMSClient butterCMSClient;

  public BlogController(IButterCMSClient butterCMSClient) {
    this.butterCMSClient = butterCMSClient;
  }
}
```

You can then test ButterCMS client by, for example, fetching all of your posts:
```java
 butterCMSClient.getPosts(null);
```

## Pages

### Get single page

With your homepage defined, the ButterCMS Pages API will return it in JSON format like this:
```json
{
  "data":{
    "slug": "acme-co",
    "page_type": "customer_case_study",
    "fields": {
      "facebook_open_graph_title": "Acme Co loves ButterCMS",
      "seo_title": "Acme Co Customer Case Study",
      "headline": "Acme Co saved 200% on Anvil costs with ButterCMS",
      "testimonial": "<p>We've been able to make anvils faster than ever before! - <em>Chief Anvil Maker</em></p>\r\n<p><img src=\"https://cdn.buttercms.com/NiA3IIP3Ssurz5eNJ15a\" alt=\"\" caption=\"false\" width=\"310\" height=\"310\" /></p>",
      "customer_logo": "https://cdn.buttercms.com/c8oSTGcwQDC5I58km5WV"
    }
  }
}
```

To integrate this into your app, create a Controller that fetches content for the page, that injects previosly defined ButterCMS client:

`com.yourorganization.app.controllers`

```java

@Controller
public class PageController {
  private IButterCMSClient butterCMSClient;

  public PageController(IButterCMSClient butterCMSClient) {
    this.butterCMSClient = butterCMSClient;
  }

  @RequestMapping("/page/{pageType}/{slug}")
  public String getPage(Model model, @PathVariable("pageType") String pageType, @PathVariable("slug") String slug) {
    PageResponse<CaseStudyPage> page = butterCMSClient.getPage(pageType, slug, null, CaseStudyPage.class);
    model.addAttribute("page", page.getData().getFields());
    return "page/page";
  }
}

```

`src/main/webapp/WEB-INF/jsp/page/page.jsp`

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<meta>
<title>${page.seoTitle}</title>
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
```

### Get pages by type

Process of fetching pages by page type is very similar to process of fetching a single one:

`com.yourorganization.app.controllers`

```java
@Controller
public class PageController {
  private IButterCMSClient butterCMSClient;

  public PageController(IButterCMSClient butterCMSClient) {
    this.butterCMSClient = butterCMSClient;
  }
  @RequestMapping("/pages")
  public String homeData(Model model) {
    PagesResponse<CaseStudyPage> pages = butterCMSClient.getPages("customer_case_study", null, CaseStudyPage.class);
    model.addAttribute("pages", pages.getData().stream()
        .map(value ->
            new HashMap<String, String>() {{
              put("pageType", value.getPageType());
              put("slug", value.getSlug());
              put("headline", value.getFields().getHeadline());
            }}
        ).collect(Collectors.toList()));
    return "page/pages";
  }

}
```

`src/main/webapp/WEB-INF/jsp/page/pages.jsp`

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello! This is Java ButterCMS example!</title>
</head>
<body>
<h2 class="hello-title">Hello! This is Java ButterCMS example!</h2>
<h3>Here is a list of Pages</h3>
<ul>
    <c:forEach items="${pages}" var="page">
        <li><a href="/page/${page["pageType"]}/${page["slug"]}">${page["headline"]}</a></li>
    </c:forEach>
</ul>
</body>
</html>
``` 

## Blog Engine

### Display posts

To display posts we create a simple /blog route in our app and fetch blog posts from the Butter API. See our [API reference](https://buttercms.com/docs/api/) for additional options such as filtering by category or author. The response also includes some metadata we'll use for pagination.

`com.yourorganization.app.controllers`

```java
@Controller
public class BlogController {
  private IButterCMSClient butterCMSClient;

  public BlogController(IButterCMSClient butterCMSClient) {
    this.butterCMSClient = butterCMSClient;
  }

  @RequestMapping("/blogs")
  public String getBlogs(Model model, @RequestParam(value = "category", required = false) String category, @RequestParam(value = "tag", required = false) String tag) {
    PostsResponse posts = butterCMSClient.getPosts(new HashMap<String, String>() {{
      put("category_slug", category);
      put("tag_slug", tag);
    }});
    if (posts.getData() == null || posts.getData().isEmpty()) {
      return "404";
    }
    model.addAttribute("blogs", posts.getData().stream()
        .map(value ->
            new HashMap<String, String>() {{
              put("slug", value.getSlug());
              put("title", value.getTitle());
            }}
        ).collect(Collectors.toList()));
    model.addAttribute("category", category);
    model.addAttribute("tag", tag);
    return "blog/blogs";
  }
```

Next we'll create a simple view at `src/main/webapp/WEB-INF/jsp/blog/blogs.jsp` that displays our posts and pagination links:

```jsp
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
``` 
We'll also create an additional route and controller method for displaying individual posts:

```java
@RequestMapping("/blog/{slug}")
  public String getBlog(Model model, @PathVariable("slug") String slug) {
    PostResponse post = butterCMSClient.getPost(slug);
    model.addAttribute("blog", post.getData());
    return "blog/blog";
  }
```

The view for displaying a full post includes information such as author, publish date, and categories. See a full list of available post properties in our [API reference](https://buttercms.com/docs/api/). We use the Page object for setting the HTML title and meta description in the <head> tag of the page.

`src/main/webapp/WEB-INF/jsp/blog/blogs.jsp`

```jsp
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
</ul>a
<img src="${blog.featuredImage}"/>
<div>
    <c:out value="${blog.body}" escapeXml="false"/>
</div>

</body>
</html>
```

### Categories, Tags, and Authors

Use Butter's APIs for categories, tags, and authors to feature and filter content on your blog:

### Authors

`com.yourorganization.app.controllers`

```java
@Controller
public class AuthorController {
  private IButterCMSClient butterCMSClient;

  public AuthorController(IButterCMSClient butterCMSClient) {
    this.butterCMSClient = butterCMSClient;
  }

  @RequestMapping("/authors")
  public String getAuthors(Model model) {
    AuthorsResponse authorsResponse = butterCMSClient.getAuthors(null);
    model.addAttribute("authors", authorsResponse.getData());
    return "author/authors";
  }

  @RequestMapping("/author/{slug}")
  public String getAuthor(Model model, @PathVariable("slug") String slug) {
    AuthorResponse authorResponse = butterCMSClient.getAuthor(slug, new HashMap<String, String>() {{
      put("include", "recent_posts");
    }});
    model.addAttribute("author", authorResponse.getData());
    return "author/author";
  }
}

```

### Categories

`com.yourorganization.app.controllers`

```java
@RequestMapping("/blogs/categories")
  public String getBlogCategories(Model model) {
    CategoriesResponse categoriesResponse = butterCMSClient.getCategories(null);
    model.addAttribute("categories", categoriesResponse.getData());
    return "blog/categories";
  }
```

### Tags

`com.yourorganization.app.controllers`

```java
@RequestMapping("/blogs/tags")
  public String getBlogtags(Model model) {
    TagsResponse tagsResponse = butterCMSClient.getTags(null);
    model.addAttribute("tags", tagsResponse.getData());
    return "blog/tags";
  }
```

### RSS, Atom, and Sitemap

Butter generates RSS, Atom, and sitemap XML markup. To use these on your blog, return the generated XML from the Butter API with the proper content type headers.

`com.yourorganization.app.controllers`

```java
@Controller
@RequestMapping("/feed")
public class FeedController {
  private IButterCMSClient butterCMSClient;

  public FeedController(IButterCMSClient butterCMSClient) {
    this.butterCMSClient = butterCMSClient;
  }

  @RequestMapping("/rss")
  public void getRSS(HttpServletResponse response) throws IOException, TransformerException {
    response.setContentType("application/xml");
    ServletOutputStream outStream = response.getOutputStream();
    outStream.println(xmlToString(butterCMSClient.getRSS()));
    outStream.flush();
    outStream.close();
  }

  @RequestMapping("/atom")
  public void getAtom(HttpServletResponse response) throws IOException, TransformerException {
    response.setContentType("application/xml");
    ServletOutputStream outStream = response.getOutputStream();
    outStream.println(xmlToString(butterCMSClient.getAtom()));
    outStream.flush();
    outStream.close();
  }

  @RequestMapping("/sitemap")
  public void getSiteMap(HttpServletResponse response) throws IOException, TransformerException {
    response.setContentType("application/xml");
    ServletOutputStream outStream = response.getOutputStream();
    outStream.println(xmlToString(butterCMSClient.getSiteMap()));
    outStream.flush();
    outStream.close();
  }

  private String xmlToString(Node node) throws TransformerException {

    DOMSource domSource = new DOMSource(node);
    StringWriter writer = new StringWriter();
    StreamResult result = new StreamResult(writer);
    TransformerFactory tf = TransformerFactory.newInstance();
    Transformer transformer = tf.newTransformer();
    transformer.transform(domSource, result);
    return writer.toString();
  }
}

```