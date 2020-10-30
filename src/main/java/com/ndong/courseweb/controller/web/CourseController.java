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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

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

  @Autowired
  private SessionUtils sessionUtils;

  @RequestMapping(path = "/course", method = RequestMethod.GET)
  public ModelAndView getCourseList(AbstractDTO getParams){
    ModelAndView view = new ModelAndView("/web/course/course-list");
    int page = (getParams.getPage() != null)? getParams.getPage() - 1: 0;
    int limit = (getParams.getLimit() != null)? getParams.getLimit(): SystemConstant.COURSE_LIMIT_ITEM;
    List<CourseDTO> courses = courseService.listCourses(
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
    List<CourseDTO> courses = courseService.listCourses(
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
    return new ModelAndView("/web/course/edit-course");
  }

  @RequestMapping(path = "/course/new", method = RequestMethod.POST)
  public ModelAndView openNewCourse(CourseDTO courseDTO) {
    ModelAndView view = new ModelAndView("/web/course/edit-course");
    CourseDTO course = courseService.tryOpenNewCourse(courseDTO);
    if (course != null) {
      String redirectView = "redirect:/course/" + course.getCode();
      return new ModelAndView(redirectView);
    } else view.addObject(SystemConstant.OPEN_COURSE_FAILED, true);
    return view;
  }

  @RequestMapping(path = "/course/{courseCode}/edit", method = RequestMethod.GET)
  public ModelAndView openNewCourse(@PathVariable String courseCode) {
    ModelAndView view = new ModelAndView("/web/course/edit-course");
    CourseDTO course = courseService.findOneCourse(courseCode);
    view.addObject(SystemConstant.COURSE_DTO, course);
    return view;
  }

  @RequestMapping(path = "/course/{courseCode}/edit", method = RequestMethod.POST)
  public ModelAndView openNewCourse(@PathVariable String courseCode, CourseDTO courseDTO) {
    CourseDTO course = courseService.updateCourse(courseDTO);
    ModelAndView view = new ModelAndView("redirect:/course/" + course.getCode());
    view.addObject(SystemConstant.COURSE_DTO, course);
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
    CourseDTO course = courseService.findOneCourse(courseCode);
    view.addObject(SystemConstant.COURSE_DTO, course);
    LessonDTO lesson = courseService.tryCreateNewLesson(lessonDTO);
    if (lesson != null) {
      String redirectView = "redirect:/course/" + course.getCode();
      return new ModelAndView(redirectView);
    } else view.addObject(SystemConstant.CREATE_LESSON_FAILED, true);
    return view;
  }

  @RequestMapping(path = "/course/{courseCode}", method = RequestMethod.GET)
  public ModelAndView getCourseDetail(@PathVariable String courseCode, HttpSession session) {
    ModelAndView view = new ModelAndView("/web/course/course-info");
    CourseDTO course = courseService.findOneCourse(courseCode);
    List<LessonDTO> lessons = courseService.listLessons(course.getId());
    CategoryDTO category = categoryService.findByCode(course.getCategoryCode());
    UserDTO author = userService.findOneUser(course.getUserId());
    List<CourseDTO> relatedCourses = courseService.listRelatedCourses(category.getCode());
    sessionUtils.pushCourse(course.getId());
    UserDTO user = sessionUtils.getUser();
    if (user != null) user = userService.
        findPermissionOnCourse(user.getUsername(), courseCode);
    view.addObject(SystemConstant.USER_DTO, user);
    view.addObject(SystemConstant.COURSE_DTO, course);
    view.addObject(SystemConstant.CATEGORY_DTO, category);
    view.addObject(SystemConstant.COURSE_AUTHOR_DTO, author);
    view.addObject(SystemConstant.LESSON_DTO_LIST, lessons);
    view.addObject(SystemConstant.RELATED_COURSE_DTO_LIST, relatedCourses);
    return view;
  }

}
