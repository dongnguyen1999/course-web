package com.ndong.courseweb.dto;

import java.io.Serializable;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.entity.composite_id.LessonId;

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

  private String mediaTypeName;

  private String filename;

  private LessonId lessonId;

  private Long courseId;

  private Integer lessonNo;

  private String code;

  public String getCode() {
    return code;
  }

  public Integer getLessonNo() {
    return lessonNo;
  }

  public void setLessonNo(Integer lessonNo) {
    this.lessonNo = lessonNo;
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public LessonId getLessonId() {
    return lessonId;
  }

  public void setLessonId(LessonId lessonId) {
    this.lessonId = lessonId;
  }

  public String getMediaTypeName() {
    return mediaTypeName;
  }

  public void setMediaTypeName(String mediaTypeName) {
    this.mediaTypeName = mediaTypeName;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
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

  public boolean isImage() {
    return getMediaTypeCode().contains(SystemConstant.IMAGE_MEDIA_CODE);
  }

  public boolean isVideo() {
    return getMediaTypeCode().contains(SystemConstant.VIDEO_MEDIA_CODE);
  }

}
