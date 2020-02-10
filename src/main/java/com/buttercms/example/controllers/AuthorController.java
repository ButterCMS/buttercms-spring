package com.buttercms.example.controllers;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buttercms.IButterCMSClient;
import com.buttercms.model.AuthorResponse;
import com.buttercms.model.AuthorsResponse;

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
