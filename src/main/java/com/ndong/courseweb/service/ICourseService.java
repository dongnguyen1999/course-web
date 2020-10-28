package com.ndong.courseweb.service;

import com.ndong.courseweb.dto.CourseDTO;
import com.ndong.courseweb.dto.LessonDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICourseService {
  boolean tryOpenNewCourse(CourseDTO model);
  boolean tryCreateNewLesson(LessonDTO model);
  CourseDTO findOneCourse(Long courseId);
  CourseDTO findOneCourse(String courseCode);
  List<CourseDTO> listTopPopularCourses(Integer limit);
  List<CourseDTO> listCourse(String searchText, String filterCode, Pageable pageable, String categoryCode);
  List<CourseDTO> listRelatedCourses(String categoryCode);
}
