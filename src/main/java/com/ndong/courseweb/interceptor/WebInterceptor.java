package com.ndong.courseweb.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.dto.UserDTO;
import com.ndong.courseweb.utils.SessionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class WebInterceptor implements HandlerInterceptor {
  @Autowired
  private SessionUtils sessionUtils;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    UserDTO user = sessionUtils.getUser();
    if (user != null)
      request.setAttribute(SystemConstant.USER_DTO, user);
    return true;
  }
}
