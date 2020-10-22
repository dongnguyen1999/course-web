package com.ndong.courseweb.interceptor.config;

import com.mysql.cj.log.Log;
import com.ndong.courseweb.interceptor.LoginInterceptor;
import com.ndong.courseweb.interceptor.RequireLoginInterceptor;
import com.ndong.courseweb.interceptor.WebInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new WebInterceptor()).
        addPathPatterns("/*").
        excludePathPatterns("/admin/*");

    registry.addInterceptor(new LoginInterceptor()).
        addPathPatterns("/login").
        addPathPatterns("/register");

    registry.addInterceptor(new RequireLoginInterceptor()).
        addPathPatterns("/course/new");
  }
}
