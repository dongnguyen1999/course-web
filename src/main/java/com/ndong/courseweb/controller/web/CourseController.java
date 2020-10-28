package com.ndong.courseweb.controller.web;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.dto.*;
import com.ndong.courseweb.entity.CourseEntity;
import com.ndong.courseweb.filter.IFilter;
import com.ndong.courseweb.service.ICategoryService;
import com.ndong.courseweb.service.ICourseService;
import com.ndong.courseweb.service.IUserService;
import com.ndong.courseweb.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CourseController {

  @Autowired
  private ICategoryService categoryService;

  @Autowired
  private ICourseService courseService;

  @Autowired
  private IFilter<CourseEntity> filter;

  @Autowired
  private IUserService userService;

  @RequestMapping(path = "/course", method = RequestMethod.GET)
  public ModelAndView getCourseList(AbstractDTO getParams){
    ModelAndView view = new ModelAndView("/web/course/course-list");
    int page = (getParams.getPage() != null)? getParams.getPage() - 1: 0;
    int limit = (getParams.getLimit() != null)? getParams.getLimit(): SystemConstant.COURSE_LIMIT_ITEM;
    List<CourseDTO> courses = courseService.listCourse(
        getParams.getSearch(), getParams.getFilter(),
        PageRequest.of(page, limit), null);
    getParams.setPage(page);
    getParams.setTotalPages(filter.getTotalPages());
    view.addObject(SystemConstant.PAGING_INFO, getParams);
    view.addObject(SystemConstant.COURSE_DTO_LIST, courses);
    return view;
  }

  @RequestMapping(path = "/category/{categoryCode}", method = RequestMethod.GET)
  public ModelAndView getCourseListWithCategory(AbstractDTO getParams, @PathVariable String categoryCode){
    ModelAndView view = new ModelAndView("/web/course/course-list");
    int page = (getParams.getPage() != null)? getParams.getPage() - 1: 0;
    int limit = (getParams.getLimit() != null)? getParams.getLimit(): SystemConstant.COURSE_LIMIT_ITEM;
    List<CourseDTO> courses = courseService.listCourse(
        getParams.getSearch(), getParams.getFilter(),
        PageRequest.of(page, limit), categoryCode);
    getParams.setPage(page);
    getParams.setTotalPages(filter.getTotalPages());
    CategoryDTO category = categoryService.findByCode(categoryCode);
    view.addObject(SystemConstant.PAGING_INFO, getParams);
    view.addObject(SystemConstant.CATEGORY_DTO, category);
    view.addObject(SystemConstant.COURSE_DTO_LIST, courses);
    return view;
  }

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

  @RequestMapping(path = "/course/{courseCode}/new-lesson", method = RequestMethod.GET)
  public ModelAndView getCreateLessonForm(@PathVariable String courseCode) {
    ModelAndView view = new ModelAndView("/web/course/edit-lesson");
    CourseDTO course = courseService.findOneCourse(courseCode);
    view.addObject(SystemConstant.COURSE_DTO, course);
    return view;
  }

  @RequestMapping(path = "/course/{courseCode}/new-lesson", method = RequestMethod.POST)
  public ModelAndView createNewLesson(@PathVariable String courseCode, LessonDTO lessonDTO) {
    ModelAndView view = new ModelAndView("/web/course/edit-lesson");
    boolean createLessonStatus = courseService.tryCreateNewLesson(lessonDTO);
    CourseDTO course = courseService.findOneCourse(courseCode);
    view.addObject(SystemConstant.COURSE_DTO, course);
    view.addObject(SystemConstant.CREATE_LESSON_STATUS, createLessonStatus);
    return view;
  }

  @RequestMapping(path = "/course/{courseCode}", method = RequestMethod.GET)
  public ModelAndView getCourseDetail(@PathVariable String courseCode, HttpSession session) {
    ModelAndView view = new ModelAndView("/web/course/course-info");
    CourseDTO course = courseService.findOneCourse(courseCode);
    CategoryDTO category = categoryService.findByCode(course.getCategoryCode());
    UserDTO author = userService.findOneUser(course.getUserId());
    List<CourseDTO> relatedCourses = courseService.listRelatedCourses(category.getCode());
    SessionUtils sessionUtils = new SessionUtils(session);
    sessionUtils.pushCourse(course.getId());
    view.addObject(SystemConstant.COURSE_DTO, course);
    view.addObject(SystemConstant.CATEGORY_DTO, category);
    view.addObject(SystemConstant.COURSE_AUTHOR_DTO, author);
    view.addObject(SystemConstant.RELATED_COURSE_DTO_LIST, relatedCourses);
    return view;
  }


}
