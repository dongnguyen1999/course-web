package com.ndong.courseweb.dto.query_result;

import java.util.List;

public class NbUserPerCourseIdDTO {
  private Long courseId;
  private Long numberUser;

  public NbUserPerCourseIdDTO(Long courseId, Long numberUser) {
    this.courseId = courseId;
    this.numberUser = numberUser;
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public Long getNumberUser() {
    return numberUser;
  }

  public void setNumberUser(Long numberUser) {
    this.numberUser = numberUser;
  }
}
