package com.buttercms.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buttercms.IButterCMSClient;
import com.buttercms.model.PostResponse;

@Controller
public class PostController {
  private IButterCMSClient butterCMSClient;

  public PostController(IButterCMSClient butterCMSClient) {
    this.butterCMSClient = butterCMSClient;
  }

  @RequestMapping("/post/{slug}")
  public String getPost(Model model, @PathVariable("slug") String slug) {
    PostResponse post = butterCMSClient.getPost(slug);
    model.addAttribute("post", post.getData());
    return "post";
  }
}
