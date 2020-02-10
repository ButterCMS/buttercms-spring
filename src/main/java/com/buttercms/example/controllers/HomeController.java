package com.buttercms.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.buttercms.IButterCMSClient;

@Controller
public class HomeController {
  private IButterCMSClient butterCMSClient;

  public HomeController(IButterCMSClient butterCMSClient) {
    this.butterCMSClient = butterCMSClient;
  }

  @RequestMapping("/")
  public String homeData(Model model) {
    return "index";
  }


}
