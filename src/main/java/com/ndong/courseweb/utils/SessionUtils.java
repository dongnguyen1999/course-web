package com.ndong.courseweb.utils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionUtils implements HttpSessionListener {

  @Autowired
  private HttpSession session;

  private Map<String, Date> sessionLog = new HashMap<>();

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
    } else
      latelyCoursesIds = new ArrayList<>();
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

  @Override
  public void sessionCreated(HttpSessionEvent event) {
    HttpSession ss = event.getSession();
    Date createDate = new Date(System.currentTimeMillis());
    sessionLog.put(ss.getId(), createDate);
    System.out.println("Session created " + ss.getId());
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent event) {
    // TODO Auto-generated method stub

  }


  public long countNumberSessionCreated(Date from, Date to) {
    if (from == null && to == null) return sessionLog.size();
    if (from == null) return filterSessionLog(new Date(Long.MIN_VALUE), to);
    if (to == null) return filterSessionLog(from, new Date(System.currentTimeMillis()));
    return filterSessionLog(from, to);
  }

  public long countNumberSessionCreated() {
    return countNumberSessionCreated(null, null);
  }

  public long countNumberSessionCreatedBefore(Date to) {
    return countNumberSessionCreated(null, to);
  }

  public long countNumberSessionCreatedAfter(Date from) {
    return countNumberSessionCreated(from, null);
  }

  private long filterSessionLog(Date from, Date to) {
    return sessionLog.keySet().stream().filter((key) -> 
      (sessionLog.get(key).compareTo(from) >= 0 && sessionLog.get(key).compareTo(to) < 0)).collect(Collectors.toSet()).size();
  }

}
