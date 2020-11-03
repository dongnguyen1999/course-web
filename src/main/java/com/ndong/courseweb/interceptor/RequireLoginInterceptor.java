package com.ndong.courseweb.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.dto.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequireLoginInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    UserDTO user = (UserDTO) request.getAttribute(SystemConstant.USER_DTO);
    if (user == null) {
      String requestUrl = request.getRequestURL().toString();
      response.sendRedirect("/login?nav=" + requestUrl);
      return false;
    }
    return true;
  }
}
