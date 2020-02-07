package com.buttercms.example.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buttercms.IButterCMSClient;
import com.buttercms.example.cms.model.CaseStudyPage;
import com.buttercms.model.PagesResponse;

@Controller
public class HomeController {
  private IButterCMSClient butterCMSClient;

  public HomeController(IButterCMSClient butterCMSClient) {
    this.butterCMSClient = butterCMSClient;
  }

  @RequestMapping("/")
  public String homeData(Model model) {
    model.addAttribute("pages", getCMSPagesList());
    return "home";
  }

  private List<Map<String, String>> getCMSPagesList() {
    PagesResponse<CaseStudyPage> pages = butterCMSClient.getPages("customer_case_study", null, CaseStudyPage.class);
    return pages.getData().stream()
        .map(value ->
            new HashMap<String, String>() {{
              put("pageType", value.getPageType());
              put("slug", value.getSlug());
              put("headline", value.getFields().getHeadline());
            }}
        ).collect(Collectors.toList());
  }



}
