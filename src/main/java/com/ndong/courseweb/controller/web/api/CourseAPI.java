package com.ndong.courseweb.controller.web.api;

import com.ndong.courseweb.dto.CourseDTO;
import com.ndong.courseweb.dto.LessonDTO;
import com.ndong.courseweb.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseAPI {

  @Autowired
  private ICourseService courseService;

  @RequestMapping(path = "/web/api/course/complete/{courseId}", method = RequestMethod.PUT)
  public ResponseEntity<CourseDTO> completeCourse(@PathVariable Long courseId) {
    CourseDTO course = courseService.completeCourse(courseId);
    return (course != null)? new ResponseEntity<>(course, HttpStatus.OK):
        new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @RequestMapping(path = "/web/api/course/{courseId}", method = RequestMethod.DELETE)
  public ResponseEntity<CourseDTO> deleteCourse(@PathVariable Long courseId) {
    CourseDTO course = courseService.deleteCourse(courseId);
    return (course != null)? new ResponseEntity<>(course, HttpStatus.OK):
        new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @RequestMapping(path = "/web/api/course/pop-lesson/{courseId}", method = RequestMethod.DELETE)
  public ResponseEntity<LessonDTO> popLesson(@PathVariable Long courseId) {
    LessonDTO lesson = courseService.popLesson(courseId);
    return (lesson != null)? new ResponseEntity<>(lesson, HttpStatus.OK):
        new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }



}
