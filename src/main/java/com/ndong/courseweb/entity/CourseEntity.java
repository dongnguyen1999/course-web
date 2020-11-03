package com.ndong.courseweb.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "course")
public class CourseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", nullable = false, columnDefinition = "varchar(256)")
  private String title;

  @Column(name = "thumbnail", nullable = false, columnDefinition = "varchar(256)")
  private String thumbnail;

  @Column(name = "code", nullable = false, columnDefinition = "varchar(256)")
  private String code;

  @Column(name = "short_description", nullable = true, columnDefinition = "varchar(512)")
  private String shortDescription;

  @Column(name = "price", nullable = false, columnDefinition = "float(12,1)")
  private Float price;

  @Column(name = "open_time", nullable = false, columnDefinition = "timestamp")
  private Timestamp openTime;

  @Column(name = "status", nullable = false, columnDefinition = "varchar(256)")
  private String status;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  private CategoryEntity category;

  @OneToMany(mappedBy = "course")
  private final Set<PurchaseDetailEntity> purchaseDetailSet = new HashSet<>();

  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
  private final Set<LessonEntity> lessonSet = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  public Set<LessonEntity> getLessonSet() {
    return lessonSet;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

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

  public CategoryEntity getCategory() {
    return category;
  }

  public void setCategory(CategoryEntity category) {
    this.category = category;
  }

  public Set<PurchaseDetailEntity> getPurchaseDetailSet() {
    return purchaseDetailSet;
  }


  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

}
