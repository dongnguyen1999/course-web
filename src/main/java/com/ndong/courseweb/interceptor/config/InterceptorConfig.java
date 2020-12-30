package com.ndong.courseweb.interceptor.config;

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

  @Autowired
  private EditCoursePermissionIntercepter editCoursePermissionIntercepter;

  @Autowired
  private RequireAdminInterceptor requireAdminInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(webInterceptor).
        excludePathPatterns("/admin/*");

    registry.addInterceptor(loginInterceptor).
        addPathPatterns("/login").
        addPathPatterns("/register");

    registry.addInterceptor(requireLoginInterceptor).
        addPathPatterns("/course/new").
        addPathPatterns("/course/*/new-lesson").
        addPathPatterns("/course/*/edit").
        addPathPatterns("/course/*/lesson-*/edit").
        addPathPatterns("/course/*/purchase").
        addPathPatterns("/admin/manage/*").
        addPathPatterns("/admin").
        addPathPatterns("/account").
        addPathPatterns("/account/*");
    
    registry.addInterceptor(requireAdminInterceptor).
        addPathPatterns("/admin").
        addPathPatterns("/admin/manage/*");

    registry.addInterceptor(editCoursePermissionIntercepter).
        addPathPatterns("/course/*/new-lesson").
        addPathPatterns("/course/*/edit").
        addPathPatterns("/course/*/lesson-*/edit");

    registry.addInterceptor(courseInterceptor).
        addPathPatterns("/course").
        addPathPatterns("/course/new").
        addPathPatterns("/course/*/edit").
        addPathPatterns("/category/*").
        addPathPatterns("/account/*-courses");
  }
}
