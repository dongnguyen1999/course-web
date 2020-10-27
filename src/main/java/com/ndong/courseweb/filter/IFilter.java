package com.ndong.courseweb.filter;

import com.ndong.courseweb.entity.CourseEntity;
import com.ndong.courseweb.service.ICourseService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFilter<T> {
  void clear();
  List<T> filter(String searchText, String filterCode, Pageable pageable, String categoryCode);
  Integer getTotalPages();
  Integer getPageSize();
}
