package com.buttercms.example.controllers;

import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.buttercms.IButterCMSClient;
import com.buttercms.model.CategoriesResponse;
import com.buttercms.model.PostResponse;
import com.buttercms.model.PostsResponse;
import com.buttercms.model.TagsResponse;

@Controller
public class BlogController {
  private IButterCMSClient butterCMSClient;

  public BlogController(IButterCMSClient butterCMSClient) {
    this.butterCMSClient = butterCMSClient;
  }

  @RequestMapping("/blog/{slug}")
  public String getBlog(Model model, @PathVariable("slug") String slug) {
    PostResponse post = butterCMSClient.getPost(slug);
    model.addAttribute("blog", post.getData());
    return "blog/blog";
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

  @RequestMapping("/blogs/categories")
  public String getBlogCategories(Model model) {
    CategoriesResponse categoriesResponse = butterCMSClient.getCategories(null);
    model.addAttribute("categories", categoriesResponse.getData());
    return "blog/categories";
  }

  @RequestMapping("/blogs/tags")
  public String getBlogtags(Model model) {
    TagsResponse tagsResponse = butterCMSClient.getTags(null);
    model.addAttribute("tags", tagsResponse.getData());
    return "blog/tags";
  }
}
