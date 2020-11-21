package com.ndong.courseweb.filter.impl;

import com.ndong.courseweb.constant.CourseStatusConstant;
import com.ndong.courseweb.constant.FilterCodeConstant;
import com.ndong.courseweb.dto.query_result.NbUserPerCourseIdDTO;
import com.ndong.courseweb.entity.CategoryEntity;
import com.ndong.courseweb.entity.CourseEntity;
import com.ndong.courseweb.filter.IFilter;
import com.ndong.courseweb.repository.CategoryRepository;
import com.ndong.courseweb.repository.CourseRepository;
import com.ndong.courseweb.repository.PurchaseDetailRepository;
import com.ndong.courseweb.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseFilter implements IFilter<CourseEntity> {

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private PurchaseDetailRepository purchaseDetailRepository;

  @Autowired
  private SessionUtils sessionUtils;

  private Pageable pageable;
  private Long totalElement;
  private List<CourseEntity> courses;


  @Override
  public void clear() {
    pageable = null;
    totalElement = 0L;
    courses = new ArrayList<>();
  }

  @Override
  public List<CourseEntity> filter(List<CourseEntity> courses, Pageable pageable, String categoryCode, String search) {
    clear();
    this.pageable = pageable;
    int offset, limit;
    if (categoryCode != null && !categoryCode.isBlank()) {
      courses = courses.stream().filter(course -> course.getCategory().getCode().equals(categoryCode)).
        collect(Collectors.toList());
    }
    if (search != null) {
      courses = courses.stream().filter(course -> course.getTitle().toLowerCase().contains(search.toLowerCase())).collect(Collectors.toList());
    }
    totalElement = (long) courses.size();
    offset = pageable.getPageNumber() * pageable.getPageSize();
    limit = pageable.getPageSize();
    courses = courses.subList(offset, Math.min(offset + limit, courses.size()));
    return courses;
  }

  @Override
  public List<CourseEntity> filter(String searchText, String filterCode, Pageable pageable, String categoryCode) {
    clear();
    this.pageable = pageable;
    int offset, limit;
    Page<CourseEntity> pages = null;
    if (filterCode == null || filterCode.equals(FilterCodeConstant.ALL)) {
      if (categoryCode != null) {
        CategoryEntity category = categoryRepository.findOneByCode(categoryCode);
        if (searchText != null) pages = courseRepository.
            findByCategoryAndTitleContainingAndStatusNotOrderByOpenTimeDesc
            (category, searchText, CourseStatusConstant.DELETED, pageable);
        else pages = courseRepository.findAllByCategoryAndStatusNotOrderByOpenTimeDesc(category, CourseStatusConstant.DELETED, pageable);
      }
      else {
        if (searchText != null) pages = courseRepository.findByTitleContainingAndStatusNotOrderByOpenTimeDesc(searchText, CourseStatusConstant.DELETED, pageable);
        else pages = courseRepository.findByStatusNotOrderByOpenTimeDesc(CourseStatusConstant.DELETED, pageable);
      }
    }
    else switch (filterCode) {
      case FilterCodeConstant.CHEAP:
        if (categoryCode != null) {
          CategoryEntity category = categoryRepository.findOneByCode(categoryCode);
          if (searchText != null) pages = courseRepository.
              findByCategoryAndTitleContainingAndStatusNot
              (category, searchText, CourseStatusConstant.DELETED,
              PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                  Sort.by("price").ascending()));
          else pages = courseRepository.findAllByCategoryAndStatusNot
              (category, CourseStatusConstant.DELETED,
              PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                  Sort.by("price").ascending()));
        }
        else {
          if (searchText != null) pages = courseRepository.
              findByTitleContainingAndStatusNot
                  (searchText, CourseStatusConstant.DELETED,
              PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                  Sort.by("price").ascending()));
          else pages = courseRepository.findByStatusNot(CourseStatusConstant.DELETED,
              PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                  Sort.by("price").ascending()));
        }
        break;
      case FilterCodeConstant.POPULAR:
        List<NbUserPerCourseIdDTO> nbUserPerCourse = purchaseDetailRepository.countNbUserPerCourseId();
        List<Long> courseIds = nbUserPerCourse.stream().
            map(NbUserPerCourseIdDTO::getCourseId).collect(Collectors.toList());
        courses = courseIds.stream().
            map(courseId -> courseRepository.findById(courseId).orElse(new CourseEntity())).
            filter(course -> !course.getStatus().equals(CourseStatusConstant.DELETED)).
            collect(Collectors.toList());
        if (categoryCode != null) {
          courses = courses.stream().filter(course -> course.getCategory().getCode().equals(categoryCode)).
              collect(Collectors.toList());
        }
        if (searchText != null) {
          courses = courses.stream().
              filter(course -> course.getTitle().toLowerCase().contains(searchText)).
              collect(Collectors.toList());
        }
        totalElement = (long) courses.size();
        offset = pageable.getPageNumber() * pageable.getPageSize();
        limit = pageable.getPageSize();
        courses = courses.subList(offset, Math.min(offset + limit, courses.size()));
        break;
      case FilterCodeConstant.LATELY:
        List<Long> latelyCourseIds = sessionUtils.getLatelyCourseIds();
        if (categoryCode != null) {
          latelyCourseIds = latelyCourseIds.stream().filter(courseId -> {
            CourseEntity course = courseRepository.findById(courseId).orElse(new CourseEntity());
            return course.getCategory().getCode().equals(categoryCode);
          }).collect(Collectors.toList());
        }
        courses = latelyCourseIds.stream().
            map(courseId -> courseRepository.findById(courseId).orElse(new CourseEntity())).
            filter(course -> !course.getStatus().equals(CourseStatusConstant.DELETED)).
            collect(Collectors.toList());
        if (searchText != null) {
          courses = courses.stream().
              filter(course -> course.getTitle().toLowerCase().contains(searchText)).
              collect(Collectors.toList());
        }
        totalElement = (long) courses.size();
        offset = pageable.getPageNumber() * pageable.getPageSize();
        limit = pageable.getPageSize();
        courses = courses.subList(offset, Math.min(offset + limit, courses.size()));
        break;
    }
    if (pages != null) {
      totalElement = pages.getTotalElements();
      courses = pages.get().collect(Collectors.toList());
    }
    return courses;
  }

  @Override
  public Integer getTotalPages() {
    int totalPages = Math.toIntExact(totalElement / pageable.getPageSize());
    if (pageable.getPageSize() * totalPages < totalElement) totalPages = totalPages + 1;
    return  totalPages;
  }

  @Override
  public Integer getPageSize() {
    return pageable.getPageSize();
  }
}
