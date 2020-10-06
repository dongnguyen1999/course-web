package com.ndong.courseweb.service;

import com.ndong.courseweb.dto.CourseDTO;

public interface ICourseService {
  boolean tryOpenNewCourse(CourseDTO model);
}
