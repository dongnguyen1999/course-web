package com.ndong.courseweb.interceptor.config;

import com.mysql.cj.log.Log;
import com.ndong.courseweb.interceptor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

  @Autowired
  private WebInterceptor webInterceptor;

  @Autowired
  private LoginInterceptor loginInterceptor;

  @Autowired
  private RequireLoginInterceptor requireLoginInterceptor;

  @Autowired
  private CourseInterceptor courseInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(webInterceptor).
        excludePathPatterns("/admin/*");

    registry.addInterceptor(loginInterceptor).
        addPathPatterns("/login").
        addPathPatterns("/register");

    registry.addInterceptor(requireLoginInterceptor).
        addPathPatterns("/course/new");

    registry.addInterceptor(courseInterceptor)
        .addPathPatterns("/course").addPathPatterns("/category/*");
  }
}
