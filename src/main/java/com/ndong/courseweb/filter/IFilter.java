package com.ndong.courseweb.filter;

import java.util.List;
import org.springframework.data.domain.Pageable;

public interface IFilter<T> {
  void clear();
  List<T> filter(List<T> courses, Pageable pageable, String categoryCode, String search);
  List<T> filter(String searchText, String filterCode, Pageable pageable, String categoryCode);
  Integer getTotalPages();
  Integer getPageSize();
}
