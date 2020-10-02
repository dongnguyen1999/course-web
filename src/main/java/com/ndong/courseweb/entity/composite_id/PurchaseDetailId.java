package com.ndong.courseweb.entity.composite_id;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

//Reference Embeddale Id and MapsId: https://stackoverflow.com/questions/9923643/can-someone-please-explain-me-mapsid-in-hibernate
@Embeddable
public class PurchaseDetailId implements Serializable {

  private Long userId;

  private Long courseId;

  public PurchaseDetailId() {
  }

  public PurchaseDetailId(Long userId, Long courseId) {
    this.userId = userId;
    this.courseId = courseId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }


  @Override
  public boolean equals(Object ob) {
    if (this == ob) return true;
    if (ob == null || getClass() != ob.getClass()) return false;
    PurchaseDetailId that = (PurchaseDetailId) ob;
    return getCourseId().equals(that.getCourseId()) &&
            getUserId().equals(that.getUserId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCourseId(), getUserId());
  }

}
