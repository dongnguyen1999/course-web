package com.ndong.courseweb.entity;

import javax.persistence.*;

//Reference composite pk mapping: https://vladmihalcea.com/the-best-way-to-map-a-composite-primary-key-with-jpa-and-hibernate/
@Entity
@Table(name = "media")
public class MediaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "caption", nullable = true, columnDefinition = "varchar(266)")
  private String caption;

  @Column(name = "source", nullable = false, columnDefinition = "varchar(266)")
  private String source;

  @ManyToOne
  @JoinColumn(name = "media_type_id", nullable = false)
  private MediaTypeEntity mediaType;

  @ManyToOne
  @JoinColumns({
          @JoinColumn(name = "course_id", nullable = false, referencedColumnName = "course_id"),
          @JoinColumn(name = "lesson_no", nullable = false, referencedColumnName = "no"),
  })
  private LessonEntity lesson;

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

  public MediaTypeEntity getMediaType() {
    return mediaType;
  }

  public void setMediaType(MediaTypeEntity mediaType) {
    this.mediaType = mediaType;
  }

  public LessonEntity getLesson() {
    return lesson;
  }

  public void setLesson(LessonEntity lesson) {
    this.lesson = lesson;
  }
}
