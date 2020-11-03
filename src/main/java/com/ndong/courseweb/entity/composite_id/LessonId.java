package com.ndong.courseweb.entity.composite_id;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LessonId implements Serializable {
  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private Long courseId;

  private Integer no;

  public LessonId() {
  }

  public LessonId(Long courseId, Integer no) {
    this.courseId = courseId;
    this.no = no;
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public Integer getNo() {
    return no;
  }

  public void setNo(Integer no) {
    this.no = no;
  }

  @Override
  public boolean equals(Object ob) {
    if (this == ob) return true;
    if (ob == null || getClass() != ob.getClass()) return false;
    LessonId that = (LessonId) ob;
    return getCourseId().equals(that.getCourseId()) &&
            getNo().equals(that.getNo());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCourseId(), getNo());
  }

}
