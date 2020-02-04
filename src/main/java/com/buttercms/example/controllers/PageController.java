package com.buttercms.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buttercms.IButterCMSClient;
import com.buttercms.example.cms.model.CaseStudyPage;
import com.buttercms.model.PageResponse;

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
    return "page";
  }
}
