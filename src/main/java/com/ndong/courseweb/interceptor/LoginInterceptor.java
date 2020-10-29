package com.ndong.courseweb.interceptor;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.utils.SessionUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if (request.getAttribute(SystemConstant.USER_DTO) != null) {
      response.sendRedirect("/index");
      return false;
    }
    return true;
  }
}
