package com.ndong.courseweb.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class UserDTO extends AbstractDTO implements Serializable {
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private String username;
  private String password;
  private String fullName;
  private String email;
  private String phone;
  private String statusCode;
  private String workExperienceInfo;
  private String birth;
  private String avatar;
  private Double coin;
  private MultipartFile avatarFile;
  private String role;
  private Long nbMembers;
  private Long nbCourses;

  public String getRole() {
    return role;
  }

  public Long getNbMembers() {
    return nbMembers;
  }

  public void setNbMembers(Long nbMembers) {
    this.nbMembers = nbMembers;
  }

  public Long getNbCourses() {
    return nbCourses;
  }

  public void setNbCourses(Long nbCourses) {
    this.nbCourses = nbCourses;
  }

  public void setRole(String role) {
    this.role = role;
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

  public String getBirth() {
    return birth;
  }

  public void setBirth(String birth) {
    this.birth = birth;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public Double getCoin() {
    return coin;
  }

  public void setCoin(Double coin) {
    this.coin = coin;
  }

  public MultipartFile getAvatarFile() {
    return avatarFile;
  }

  public void setAvatarFile(MultipartFile avatarFile) {
    this.avatarFile = avatarFile;
  }
}
