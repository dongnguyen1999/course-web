package com.ndong.courseweb.controller.web;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.dto.CourseDTO;
import com.ndong.courseweb.dto.UserDTO;
import com.ndong.courseweb.service.ICourseService;
import com.ndong.courseweb.service.IUserService;
import com.ndong.courseweb.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

  @Autowired
  private IUserService userService;

  @Autowired
  private ICourseService courseService;

  @Autowired
  private SessionUtils sessionUtils;

  @RequestMapping(path = "/index", method = RequestMethod.GET)
  public ModelAndView indexPage() {
    ModelAndView view = new ModelAndView("web/home");
    List<CourseDTO> courses = courseService.listTopPopularCourses(SystemConstant.LANDING_LIMIT_ITEM);
    view.addObject(SystemConstant.COURSE_DTO_LIST, courses);
    return view;
  }

  @RequestMapping(path = "/login", method = RequestMethod.GET)
  public ModelAndView loginPage(@RequestParam(required = false) String nav) {
    ModelAndView view = new ModelAndView("/web/auth/login");
    if (nav != null)
      view.addObject(SystemConstant.NAVIGATE_URL, nav);
    return view;
  }

  @RequestMapping(path = "/register", method = RequestMethod.GET)
  public ModelAndView registerPage() {
    return new ModelAndView("/web/auth/register");
  }

  @RequestMapping(path = "/register", method = RequestMethod.POST)
  public ModelAndView register(UserDTO userDTO) {
    ModelAndView view = new ModelAndView("/web/auth/register");
    boolean registerStatus = userService.tryRegisterAccount(userDTO) != null;
    view.addObject(SystemConstant.REGISTER_STATUS, registerStatus);
    return view;
  }

  @RequestMapping(path = "/login", method = RequestMethod.POST)
  public ModelAndView login(UserDTO userDTO) {
    ModelAndView view = new ModelAndView("/web/auth/login");
    UserDTO user = userService.findOneUser(userDTO.getUsername(), userDTO.getPassword());
    String navigateUrl = userDTO.getNavigateUrl().orElse("/index");
    if (user != null) {
      sessionUtils.setUser(user);
      String redirectView = "redirect:" + navigateUrl;
      return new ModelAndView(redirectView);
    } else
      view.addObject(SystemConstant.LOGIN_FAILED, true);
    return view;
  }

  @RequestMapping(path = "/logout", method = RequestMethod.GET)
  public ModelAndView logout(HttpSession session) {
    sessionUtils.removeUser();
    return new ModelAndView("redirect:/index");
  }

  @RequestMapping(path = "/")
  public ModelAndView homepageRedirect() {
    return new ModelAndView("redirect:/index");
  }

}
