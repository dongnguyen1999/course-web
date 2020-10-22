package com.ndong.courseweb.interceptor;

import com.ndong.courseweb.utils.SessionUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    SessionUtils sessionUtils = new SessionUtils(request);
    if (sessionUtils.isLoggedIn()) {
      response.sendRedirect("/index");
      return false;
    } else return true;
  }
}
