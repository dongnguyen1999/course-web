package com.ndong.courseweb.interceptor;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.dto.CategoryDTO;
import com.ndong.courseweb.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class CourseInterceptor extends HandlerInterceptorAdapter {
  @Autowired
  private ICategoryService categoryService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    List<CategoryDTO> categories = categoryService.findAll();
    request.setAttribute(SystemConstant.CATEGORY_DTO_LIST, categories);
    return true;
  }
}
