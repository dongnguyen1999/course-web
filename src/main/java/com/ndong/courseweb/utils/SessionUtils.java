package com.ndong.courseweb.utils;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.dto.CourseDTO;
import com.ndong.courseweb.dto.UserDTO;
import com.ndong.courseweb.entity.CourseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class SessionUtils {

  @Autowired
  private HttpSession session;

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

  public void pushCourse(Long courseId) {
    @SuppressWarnings("unchecked")
    List<Long> latelyCoursesIds = (List<Long>) session.getAttribute(SystemConstant.SESSION_LATELY_COURSE_LIST);
    if (latelyCoursesIds != null) {
      latelyCoursesIds.remove(courseId);
    } else latelyCoursesIds = new ArrayList<>();
    latelyCoursesIds.add(courseId);
    session.setAttribute(SystemConstant.SESSION_LATELY_COURSE_LIST, latelyCoursesIds);
  }

  public List<Long> getLatelyCourseIds() {
    @SuppressWarnings("unchecked")
    List<Long> latelyCourses = (List<Long>) session.getAttribute(SystemConstant.SESSION_LATELY_COURSE_LIST);
    if (latelyCourses != null) {
      List<Long> clone = new ArrayList<>(latelyCourses);
      Collections.reverse(clone);
      return clone;
    }
    return new ArrayList<>();
  }

}
