package com.ndong.courseweb.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Timestamp;

public class CourseDTO extends AbstractDTO implements Serializable {
  private String title;

  private String thumbnail;

  private MultipartFile thumbnailFile;

  private String shortDescription;

  private Float price;

  private Timestamp openTime;

  private String status;

  private String categoryCode;

  private Integer nextAvailableLessonNo;

  private String code;

  private Long nbMembers;

  public Long getNbMembers() {
    return nbMembers;
  }

  public void setNbMembers(Long nbMembers) {
    this.nbMembers = nbMembers;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
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

  public Integer getNextAvailableLessonNo() {
    return nextAvailableLessonNo;
  }

  public void setNextAvailableLessonNo(Integer nextAvailableLessonNo) {
    this.nextAvailableLessonNo = nextAvailableLessonNo;
  }


  public MultipartFile getThumbnailFile() {
    return thumbnailFile;
  }

  public void setThumbnailFile(MultipartFile thumbnailFile) {
    this.thumbnailFile = thumbnailFile;
  }
}
