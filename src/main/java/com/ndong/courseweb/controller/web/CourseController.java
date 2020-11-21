package com.ndong.courseweb.controller.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.constant.UserConstant;
import com.ndong.courseweb.dto.AbstractDTO;
import com.ndong.courseweb.dto.CategoryDTO;
import com.ndong.courseweb.dto.CourseDTO;
import com.ndong.courseweb.dto.LessonDTO;
import com.ndong.courseweb.dto.UserDTO;
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
  public ModelAndView getCourseList(AbstractDTO getParams) {
    ModelAndView view = new ModelAndView("/web/course/course-list");
    int page = (getParams.getPage() != null) ? getParams.getPage() - 1 : 0;
    int limit = (getParams.getLimit() != null) ? getParams.getLimit() : SystemConstant.COURSE_LIMIT_ITEM;
    List<CourseDTO> courses = courseService.listCourses(getParams.getSearch(), getParams.getFilter(),
        PageRequest.of(page, limit), null);
    getParams.setPage(page);
    getParams.setTotalPages(filter.getTotalPages());
    view.addObject(SystemConstant.PAGING_INFO, getParams);
    view.addObject(SystemConstant.COURSE_DTO_LIST, courses);
    return view;
  }

  @RequestMapping(path = "/category/{categoryCode}", method = RequestMethod.GET)
  public ModelAndView getCourseListWithCategory(AbstractDTO getParams, @PathVariable String categoryCode) {
    ModelAndView view = new ModelAndView("/web/course/course-list");
    int page = (getParams.getPage() != null) ? getParams.getPage() - 1 : 0;
    int limit = (getParams.getLimit() != null) ? getParams.getLimit() : SystemConstant.COURSE_LIMIT_ITEM;
    List<CourseDTO> courses = courseService.listCourses(getParams.getSearch(), getParams.getFilter(),
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
    } else
      view.addObject(SystemConstant.OPEN_COURSE_FAILED, true);
    return view;
  }

  @RequestMapping(path = "/course/{courseCode}/edit", method = RequestMethod.GET)
  public ModelAndView getEditCourseView(@PathVariable String courseCode) {
    ModelAndView view = new ModelAndView("/web/course/edit-course");
    CourseDTO course = courseService.findOneCourse(courseCode);
    view.addObject(SystemConstant.COURSE_DTO, course);
    return view;
  }

  @RequestMapping(path = "/course/{courseCode}/edit", method = RequestMethod.POST)
  public ModelAndView editCourse(@PathVariable String courseCode, CourseDTO courseDTO) {
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
    } else
      view.addObject(SystemConstant.CREATE_LESSON_FAILED, true);
    return view;
  }

  @RequestMapping(path = "/course/{courseCode}/lesson-{lessonNo}", method = RequestMethod.GET)
  public ModelAndView getLessonDetail(@PathVariable String courseCode, @PathVariable Integer lessonNo) {
    ModelAndView view = new ModelAndView("/web/course/lesson-info");
    LessonDTO lesson = courseService.findOneLesson(courseCode, lessonNo);
    view.addObject(SystemConstant.LESSON_DTO, lesson);
    return view;
  }

  @RequestMapping(path = "/course/{courseCode}/lesson-{lessonNo}/edit", method = RequestMethod.GET)
  public ModelAndView getEditLessonView(@PathVariable String courseCode, @PathVariable Integer lessonNo) {
    ModelAndView view = new ModelAndView("/web/course/edit-lesson");
    CourseDTO course = courseService.findOneCourse(courseCode);
    LessonDTO lesson = courseService.findOneLesson(course, lessonNo);
    view.addObject(SystemConstant.COURSE_DTO, course);
    view.addObject(SystemConstant.LESSON_DTO, lesson);
    return view;
  }

  @RequestMapping(path = "/course/{courseCode}/lesson-{lessonNo}/edit", method = RequestMethod.POST)
  public ModelAndView editLesson(@PathVariable String courseCode, @PathVariable Integer lessonNo, LessonDTO lessonDTO) {
    LessonDTO lesson = courseService.updateLesson(lessonDTO);
    ModelAndView view = new ModelAndView("redirect:/course/" + courseCode);
    view.addObject(SystemConstant.COURSE_DTO, lesson);
    return view;
  }

  @RequestMapping(path = "/course/{courseCode}", method = RequestMethod.GET)
  public ModelAndView getCourseDetail(@PathVariable String courseCode) {
    ModelAndView view = new ModelAndView("/web/course/course-info");
    CourseDTO course = courseService.findOneCourse(courseCode);
    List<LessonDTO> lessons = courseService.listLessons(course.getId());
    CategoryDTO category = categoryService.findByCode(course.getCategoryCode());
    UserDTO author = userService.findOneUser(course.getUserId());
    List<CourseDTO> relatedCourses = courseService.listRelatedCourses(course);
    sessionUtils.pushCourse(course.getId());
    UserDTO user = sessionUtils.getUser();
    if (user != null)
      user = userService.findPermissionOnCourse(user.getUsername(), courseCode);
    view.addObject(SystemConstant.USER_DTO, user);
    view.addObject(SystemConstant.COURSE_DTO, course);
    view.addObject(SystemConstant.CATEGORY_DTO, category);
    view.addObject(SystemConstant.COURSE_AUTHOR_DTO, author);
    view.addObject(SystemConstant.LESSON_DTO_LIST, lessons);
    view.addObject(SystemConstant.RELATED_COURSE_DTO_LIST, relatedCourses);
    return view;
  }

  @RequestMapping(path = "/course/{courseCode}/purchase", method = RequestMethod.GET)
  public ModelAndView confirmPurchase(@PathVariable String courseCode) {
    UserDTO currentUser = sessionUtils.getUser();
    UserDTO user = userService.findPermissionOnCourse(currentUser.getUsername(), courseCode);
    List<String> excludes = Arrays.asList(UserConstant.ROLE_ADMIN, UserConstant.ROLE_AUTHOR, UserConstant.ROLE_OWNER);
    if (excludes.contains(user.getRole())) return new ModelAndView("redirect:/course/" + courseCode);
    ModelAndView view = new ModelAndView("web/wallet/purchase");
    view.addObject(SystemConstant.COURSE_DTO, courseService.findOneCourse(courseCode));
    view.addObject(SystemConstant.BALANCE_STATUS, userService.checkBalanceForPurchase(courseCode));
    return view;
  }

  @RequestMapping(path = "/course/{courseCode}/purchase", method = RequestMethod.POST)
  public ModelAndView purchase(@PathVariable String courseCode) {
    UserDTO currentUser = sessionUtils.getUser();
    UserDTO user = userService.findPermissionOnCourse(currentUser.getUsername(), courseCode);
    List<String> excludes = Arrays.asList(UserConstant.ROLE_ADMIN, UserConstant.ROLE_AUTHOR, UserConstant.ROLE_OWNER);
    if (excludes.contains(user.getRole())) return new ModelAndView("redirect:/course/" + courseCode);
    ModelAndView view = new ModelAndView("web/wallet/purchase");
    Boolean balanceStatus = userService.checkBalanceForPurchase(courseCode);
    if (balanceStatus) {
      userService.purchaseCourse(user.getUsername(), courseCode);
      return new ModelAndView("redirect:/course/" + courseCode);
    } else {
      view.addObject(SystemConstant.COURSE_DTO, courseService.findOneCourse(courseCode));
      view.addObject(SystemConstant.BALANCE_STATUS, balanceStatus);
      return view;
    }
  }

  private ModelAndView getCourseLib(String userRole, CategoryDTO categoryDTO) {
    ModelAndView view = new ModelAndView("/web/course/course-lib");
    UserDTO user = sessionUtils.getUser();
    user.setRole(userRole);
    view.addObject(SystemConstant.USER_DTO, user);
    int page = (categoryDTO.getPage() != null) ? categoryDTO.getPage() - 1 : 0;
    int limit = (categoryDTO.getLimit() != null) ? categoryDTO.getLimit() : SystemConstant.COURSE_LIMIT_ITEM;
    List<CourseDTO> courses = new ArrayList<>();
    if (userRole.equals(UserConstant.ROLE_OWNER)) courses = courseService.listPurchasedCourses(user.getUsername(), PageRequest.of(page, limit), categoryDTO.getCode(), categoryDTO.getSearch());
    else if (userRole.equals(UserConstant.ROLE_AUTHOR)) courses = courseService.listManagedCourses(user.getUsername(), PageRequest.of(page, limit), categoryDTO.getCode(), categoryDTO.getSearch());
    categoryDTO.setPage(page);
    categoryDTO.setTotalPages(filter.getTotalPages());
    view.addObject(SystemConstant.PAGING_INFO, categoryDTO);
    view.addObject(SystemConstant.COURSE_DTO_LIST, courses);
    return view;
  }

  @RequestMapping(path = "/account/purchased-courses", method = RequestMethod.GET)
  public ModelAndView getPurchasedCourses(CategoryDTO categoryDTO) {
    return getCourseLib(UserConstant.ROLE_OWNER, categoryDTO);
  }

  @RequestMapping(path = "/account/manage-courses", method = RequestMethod.GET)
  public ModelAndView getManagedCourses(CategoryDTO categoryDTO) {
    return getCourseLib(UserConstant.ROLE_AUTHOR, categoryDTO);
  }

}
