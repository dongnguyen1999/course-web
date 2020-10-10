package com.ndong.courseweb.entity;

import com.ndong.courseweb.entity.composite_id.LessonId;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lesson")
public class LessonEntity {

  @EmbeddedId
  private LessonId id;

  @ManyToOne
  @MapsId("courseId")
  private CourseEntity course;

  @Column(name = "title", nullable = false, columnDefinition = "varchar(256)")
  private String title;

  @Column(name = "short_description", nullable = false, columnDefinition = "varchar(512)")
  private String shortDescription;

  @Column(name = "content", nullable = false, columnDefinition = "text")
  private String content;

  @Column(name = "upload_time", nullable = false, columnDefinition = "timestamp")
  private Timestamp uploadTime;

  @Column(name = "duration", nullable = true, columnDefinition = "int")
  private Integer duration;

  @Column(name = "enable_free_trail", nullable = false, columnDefinition = "bool")
  private Boolean enableFreeTrial;

  @OneToMany(mappedBy = "lesson")
  private final Set<MediaEntity> mediaSet = new HashSet<>();

  public LessonId getId() {
    return id;
  }

  public void setId(LessonId id) {
    this.id = id;
  }

  public CourseEntity getCourse() {
    return course;
  }

  public void setCourse(CourseEntity course) {
    this.course = course;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Timestamp getUploadTime() {
    return uploadTime;
  }

  public void setUploadTime(Timestamp uploadTime) {
    this.uploadTime = uploadTime;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public Set<MediaEntity> getMediaSet() {
    return mediaSet;
  }

  public Boolean getEnableFreeTrial() {
    return enableFreeTrial;
  }

  public void setEnableFreeTrial(Boolean enableFreeTrial) {
    this.enableFreeTrial = enableFreeTrial;
  }
}
