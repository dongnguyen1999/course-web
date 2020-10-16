package com.ndong.courseweb.dto;

import com.ndong.courseweb.entity.LessonEntity;
import com.ndong.courseweb.entity.MediaTypeEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

public class MediaDTO {
  private Long id;

  private String caption;

  private MultipartFile rawFile;

  private String source;

  private String mediaTypeCode;

  private Long lessonId;

  private Integer lessonNo;

  private String code;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public MultipartFile getRawFile() {
    return rawFile;
  }

  public void setRawFile(MultipartFile rawFile) {
    this.rawFile = rawFile;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCaption() {
    return caption;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getMediaTypeCode() {
    return mediaTypeCode;
  }

  public void setMediaTypeCode(String mediaTypeCode) {
    this.mediaTypeCode = mediaTypeCode;
  }

  public Long getLessonId() {
    return lessonId;
  }

  public void setLessonId(Long lessonId) {
    this.lessonId = lessonId;
  }

  public Integer getLessonNo() {
    return lessonNo;
  }

  public void setLessonNo(Integer lessonNo) {
    this.lessonNo = lessonNo;
  }
}
