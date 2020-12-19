package com.ndong.courseweb.entity;

import com.ndong.courseweb.entity.composite_id.PurchaseDetailId;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "purchase_detail")
public class PurchaseDetailEntity {

  @EmbeddedId
  private PurchaseDetailId id;

  @ManyToOne
  @MapsId("userId")
  private UserEntity user;

  @ManyToOne
  @MapsId("courseId")
  private CourseEntity course;

  @Column(name = "purchase_date", nullable = false, columnDefinition = "date")
  private Date purchaseDate;

  @Column(name = "price", nullable = false, columnDefinition = "float(9,1)")
  private Float price;

  public PurchaseDetailId getId() {
    return id;
  }

  public void setId(PurchaseDetailId id) {
    this.id = id;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public CourseEntity getCourse() {
    return course;
  }

  public void setCourse(CourseEntity course) {
    this.course = course;
  }

  public Date getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(Date purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }
}
