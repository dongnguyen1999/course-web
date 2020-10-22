package com.ndong.courseweb.dto;

import java.util.Optional;

public class AbstractDTO {
  private Long id;
  private String navigateUrl;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Optional<String> getNavigateUrl() {
    return Optional.ofNullable(navigateUrl);
  }

  public void setNavigateUrl(String navigateUrl) {
    this.navigateUrl = navigateUrl;
  }
}
