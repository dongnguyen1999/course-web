package com.ndong.courseweb.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username", nullable = false, columnDefinition = "varchar(256)")
  private String username;

  @Column(name = "password", nullable = false, columnDefinition = "varchar(256)")
  private String password;

  @Column(name = "full_name", nullable = false, columnDefinition = "varchar(256)")
  private String fullName;

  @Column(name = "avatar", nullable = true, columnDefinition = "varchar(256)")
  private String avatar;

  @Column(name = "birth", nullable = true, columnDefinition = "date")
  private Date birth;

  @Column(name = "email", nullable = true, columnDefinition = "varchar(256)")
  private String email;

  @Column(name = "phone", nullable = true, columnDefinition = "varchar(256)")
  private String phone;

  @Column(name = "coin", nullable = false, columnDefinition = "float(12,1)")
  private Double coin;

  @Column(name = "status_code", nullable = false, columnDefinition = "varchar(256)")
  private String statusCode;

  @Column(name = "work_experience_info", nullable = true, columnDefinition = "text")
  private String workExperienceInfo;

//  Reference cascade usage: https://stackjava.com/hibernate/cascade-trong-jpa-hibernate-la-gi-cac-loai-cascadetype.html
//  Reference fetch type usage: https://stackjava.com/hibernate/hibernate-fetchtype-la-gi-phan-biet-fetchtype-lazy-voi-eager.html
  @OneToMany(mappedBy = "user")
  private final Set<PurchaseDetailEntity> purchaseDetailEntitySet = new HashSet<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public Date getBirth() {
    return birth;
  }

  public void setBirth(Date birth) {
    this.birth = birth;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Double getCoin() {
    return coin;
  }

  public void setCoin(Double coin) {
    this.coin = coin;
  }

  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public String getWorkExperienceInfo() {
    return workExperienceInfo;
  }

  public void setWorkExperienceInfo(String workExperienceInfo) {
    this.workExperienceInfo = workExperienceInfo;
  }

  public Set<PurchaseDetailEntity> getPurchaseDetailSet() {
    return purchaseDetailEntitySet;
  }
}
