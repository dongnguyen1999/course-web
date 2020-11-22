package com.ndong.courseweb.interceptor;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ndong.courseweb.constant.UserConstant;
import com.ndong.courseweb.dto.UserDTO;
import com.ndong.courseweb.service.IUserService;
import com.ndong.courseweb.utils.SessionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class EditCoursePermissionIntercepter implements HandlerInterceptor{

  @Autowired
  private SessionUtils sessionUtils;

  @Autowired
  private IUserService userService;
    
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
    String requestUrl = request.getRequestURL().toString();
    String courseCode = requestUrl.split("/")[4];
    UserDTO user = sessionUtils.getUser();
    if (user != null) user = userService.findPermissionOnCourse(user.getUsername(), courseCode);
    List<String> editors = Arrays.asList(UserConstant.ROLE_ADMIN, UserConstant.ROLE_AUTHOR);
    if (user == null || !editors.contains(user.getRole())) {
      response.sendRedirect("/course/" + courseCode);
      return false;
    }
    return true;
  }
    
}
