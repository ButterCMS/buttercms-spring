package com.buttercms.example.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buttercms.IButterCMSClient;
import com.buttercms.model.PostResponse;
import com.buttercms.model.PostsResponse;

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
    return "blog";
  }

  @RequestMapping("/blog")
  public String getBlogs(Model model) {
    model.addAttribute("blogs", getCMSPostList());
    return "blogs";
  }

  private List<Map<String, String>> getCMSPostList() {
    PostsResponse posts = butterCMSClient.getPosts(null);
    return posts.getData().stream()
        .map(value ->
            new HashMap<String, String>() {{
              put("slug", value.getSlug());
              put("title", value.getTitle());
            }}
        ).collect(Collectors.toList());
  }
}
