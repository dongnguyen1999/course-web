package com.ndong.courseweb.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

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

  @Column(name = "short_description", nullable = true, columnDefinition = "varchar(512)")
  private String shortDescription;

  @Column(name = "price", nullable = false, columnDefinition = "float(9,1)")
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
}
