package com.buttercms.example.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.buttercms.ButterCMSClient;
import com.buttercms.IButterCMSClient;

@Configuration
public class AppConfig {

  @Value("${buttercms.key}")
  private String butterCMSKey;

  @Bean
  public IButterCMSClient butterCMSClient() {
    return new ButterCMSClient(butterCMSKey);
  }
}
