package com.ndong.courseweb.controller.web;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.dto.CategoryDTO;
import com.ndong.courseweb.dto.CourseDTO;
import com.ndong.courseweb.dto.LessonDTO;
import com.ndong.courseweb.service.ICategoryService;
import com.ndong.courseweb.service.ICourseService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CourseController {

  @Autowired
  private ICategoryService categoryService;

  @Autowired
  private ICourseService courseService;

  @RequestMapping(path = "/course/new", method = RequestMethod.GET)
  public ModelAndView getNewCourseForm() {
    ModelAndView view = new ModelAndView("/web/course/edit-course");
    List<CategoryDTO> categories = categoryService.findAll();
    view.addObject(SystemConstant.CATEGORY_DTO_LIST, categories);
    return view;
  }

  @RequestMapping(path = "/course/new", method = RequestMethod.POST)
  public ModelAndView openNewCourse(CourseDTO courseDTO) {
    ModelAndView view = new ModelAndView("/web/course/edit-course");
    boolean openCourseStatus = courseService.tryOpenNewCourse(courseDTO);
    view.addObject(SystemConstant.OPEN_COURSE_STATUS, openCourseStatus);
    return view;
  }

  @RequestMapping(path = "/course/{courseId}/new-lesson", method = RequestMethod.GET)
  public ModelAndView getCreateLessonForm(@PathVariable Long courseId) {
    ModelAndView view = new ModelAndView("/web/course/edit-lesson");
    CourseDTO course = courseService.findOneCourse(courseId);
    view.addObject(SystemConstant.COURSE_DTO, course);
    return view;
  }

  @RequestMapping(path = "/course/{courseId}/new-lesson", method = RequestMethod.POST)
  public ModelAndView createNewLesson(@PathVariable Long courseId, LessonDTO lessonDTO) {
    ModelAndView view = new ModelAndView("/web/course/edit-lesson");
    boolean createLessonStatus = courseService.tryCreateNewLesson(lessonDTO);
    CourseDTO course = courseService.findOneCourse(courseId);
    view.addObject(SystemConstant.COURSE_DTO, course);
    view.addObject(SystemConstant.CREATE_LESSON_STATUS, createLessonStatus);
    return view;
  }
}
