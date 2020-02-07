package com.buttercms.example.cms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(
    ignoreUnknown = true
)
public class CaseStudyPage {

  private String openGraphTitle;
  private String seoTitle;
  private String headline;
  private String testimonial;
  private String customerLogo;

  public String getOpenGraphTitle() {
    return openGraphTitle;
  }

  @JsonProperty("facebook_open_graph_title")
  public void setOpenGraphTitle(String openGraphTitle) {
    this.openGraphTitle = openGraphTitle;
  }

  public String getSeoTitle() {
    return seoTitle;
  }

  public void setSeoTitle(String seoTitle) {
    this.seoTitle = seoTitle;
  }

  public String getHeadline() {
    return headline;
  }

  public void setHeadline(String headline) {
    this.headline = headline;
  }

  public String getTestimonial() {
    return testimonial;
  }

  public void setTestimonial(String testimonial) {
    this.testimonial = testimonial;
  }

  public String getCustomerLogo() {
    return customerLogo;
  }

  public void setCustomerLogo(String customerLogo) {
    this.customerLogo = customerLogo;
  }
}
