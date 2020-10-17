package com.ndong.courseweb.service;

import com.ndong.courseweb.dto.CourseDTO;
import com.ndong.courseweb.dto.LessonDTO;

public interface ICourseService {
  boolean tryOpenNewCourse(CourseDTO model);
  boolean tryCreateNewLesson(LessonDTO model);
  CourseDTO findOneCourse(Long courseId);
  CourseDTO findOneCourse(String courseCode);

}
