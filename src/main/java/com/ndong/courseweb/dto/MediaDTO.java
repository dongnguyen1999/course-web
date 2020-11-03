package com.ndong.courseweb.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class MediaDTO extends AbstractDTO implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

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
