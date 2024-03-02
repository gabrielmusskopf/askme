package br.com.gabrielgmusskopf.myquestion.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
class RequestLoggingFilterConfig {

  @Bean
  public CommonsRequestLoggingFilter logFilter() {
    final var filter = new CommonsRequestLoggingFilter();
    filter.setIncludeQueryString(true);
    filter.setMaxPayloadLength(10000);
    return filter;
  }
}