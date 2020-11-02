package com.ndong.courseweb.service;

import com.ndong.courseweb.dto.CourseDTO;
import com.ndong.courseweb.dto.LessonDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICourseService {
  CourseDTO tryOpenNewCourse(CourseDTO model);
  LessonDTO tryCreateNewLesson(LessonDTO model);
  CourseDTO findOneCourse(Long courseId);
  CourseDTO findOneCourse(String courseCode);
  LessonDTO findOneLesson(String courseCode, Integer lessonNo);
  LessonDTO findOneLesson(CourseDTO course, Integer lessonNo);
  LessonDTO findOneLesson(Long courseId, Integer lessonNo);
  List<CourseDTO> listTopPopularCourses(Integer limit);
  List<CourseDTO> listCourses(String searchText, String filterCode, Pageable pageable, String categoryCode);
  List<CourseDTO> listRelatedCourses(CourseDTO course);
  CourseDTO completeCourse(Long courseId);
  LessonDTO popLesson(Long courseId);
  List<LessonDTO> listLessons(Long courseId);
  CourseDTO updateCourse(CourseDTO model);
  LessonDTO updateLesson(LessonDTO model);
  CourseDTO deleteCourse(Long courseId);
}
