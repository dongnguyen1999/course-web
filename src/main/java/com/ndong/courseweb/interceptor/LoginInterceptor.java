package com.ndong.courseweb.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ndong.courseweb.constant.SystemConstant;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

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
