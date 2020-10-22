package com.ndong.courseweb.interceptor;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.dto.UserDTO;
import com.ndong.courseweb.utils.SessionUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequireLoginInterceptor extends HandlerInterceptorAdapter {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    SessionUtils sessionUtils = new SessionUtils(request);
    UserDTO user = sessionUtils.getUser();
    if (user == null) {
      String requestUrl = request.getRequestURL().toString();
      response.sendRedirect("/login?nav=" + requestUrl);
      return false;
    } else {
      request.setAttribute(SystemConstant.USER_DTO, user);
      return true;
    }
  }
}
