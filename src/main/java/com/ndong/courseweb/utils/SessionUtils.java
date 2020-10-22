package com.ndong.courseweb.utils;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

  private final HttpSession session;

  public SessionUtils(HttpSession session) {
    this.session = session;
  }

  public SessionUtils(HttpServletRequest request){
    this.session = request.getSession();
  }

  public void setUser(UserDTO user) {
    session.setAttribute(SystemConstant.USER_DTO, user);
  }

  public UserDTO getUser() {
    return (UserDTO) session.getAttribute(SystemConstant.USER_DTO);
  }

  public void removeUser() {
    session.removeAttribute(SystemConstant.USER_DTO);
  }

  public boolean isLoggedIn() {
    return (getUser() != null);
  }

}
