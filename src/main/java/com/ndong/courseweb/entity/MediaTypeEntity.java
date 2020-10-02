package com.ndong.courseweb.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "media_type")
public class MediaTypeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, columnDefinition = "varchar(256)")
  private String name;

  @Column(name = "code", nullable = false, columnDefinition = "varchar(256)")
  private String code;

  @Column(name = "extension", nullable = false, columnDefinition = "varchar(256)")
  private String extension;

  @OneToMany(mappedBy = "mediaType")
  private final Set<MediaEntity> mediaSet = new HashSet<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public Set<MediaEntity> getMediaSet() {
    return mediaSet;
  }
}
