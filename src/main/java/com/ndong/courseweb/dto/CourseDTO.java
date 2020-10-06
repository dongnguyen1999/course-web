package com.ndong.courseweb.dto;

import javax.persistence.Column;
import java.sql.Timestamp;

public class CourseDTO {
  private Long id;

  private String title;

  private String thumbnail;

  private String shortDescription;

  private Float price;

  private Timestamp openTime;

  private String status;

  private String categoryCode;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  public Timestamp getOpenTime() {
    return openTime;
  }

  public void setOpenTime(Timestamp openTime) {
    this.openTime = openTime;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCategoryCode() {
    return categoryCode;
  }

  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }
}
