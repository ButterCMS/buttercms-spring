package com.buttercms.example.controllers;

import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buttercms.IButterCMSClient;
import com.buttercms.example.cms.model.CaseStudyPage;
import com.buttercms.model.PageResponse;
import com.buttercms.model.PagesResponse;

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
