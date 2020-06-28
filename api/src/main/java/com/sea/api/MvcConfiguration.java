package com.sea.api;

import com.sea.api.mvc.ApiLogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by wanglh on 2020/6/28.
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

  @Bean
  public ApiLogInterceptor apiLogInterceptor() {
    return new ApiLogInterceptor();
  }

}
