package com.ndong.courseweb.dto;

import java.util.Optional;

public class AbstractDTO {
  private Long id;
  private String navigateUrl;
  private Integer totalPages;
  private Integer page;
  private Integer limit;
  private String search;
  private String filter;

  public String getSearch() {
    return search;
  }

  public void setSearch(String search) {
    this.search = search;
  }

  public String getFilter() {
    return filter;
  }

  public void setFilter(String filter) {
    this.filter = filter;
  }

  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

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
