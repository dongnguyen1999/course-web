package com.ndong.courseweb.controller.web;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.dto.CategoryDTO;
import com.ndong.courseweb.dto.CourseDTO;
import com.ndong.courseweb.service.ICategoryService;
import com.ndong.courseweb.service.ICourseService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
}
