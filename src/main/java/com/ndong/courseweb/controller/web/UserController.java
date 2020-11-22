package com.ndong.courseweb.controller.web;

import java.util.Collections;
import java.util.List;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.dto.CourseDTO;
import com.ndong.courseweb.dto.UserDTO;
import com.ndong.courseweb.service.ICourseService;
import com.ndong.courseweb.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

  @Autowired
  private IUserService userService;

  @Autowired
  private ICourseService courseService;

  @RequestMapping(path = "/account/payin", method = RequestMethod.GET)
  public ModelAndView getPayInView(@RequestParam(required = false) String nav) {
    ModelAndView view = new ModelAndView("web/wallet/payin");
    if (nav != null) view.addObject(SystemConstant.NAVIGATE_URL, nav);
    return view;
  }

  @RequestMapping(path = "/account/payin", method = RequestMethod.POST)
  public ModelAndView payIn(@RequestParam Double transactionValue, @RequestParam(required = false) String navigateUrl) {
    ModelAndView view = new ModelAndView("web/wallet/payin");
    if (navigateUrl == null) navigateUrl = "/index";
    UserDTO user = userService.tryPayInValue(transactionValue);
    boolean transactionStatus = user != null;
    view.addObject(SystemConstant.NAVIGATE_URL, navigateUrl);
    view.addObject(SystemConstant.TRANSACTION_STATUS, transactionStatus);
    return view;
  }

  @RequestMapping(path = "/account/withdraw", method = RequestMethod.GET)
  public ModelAndView getWithdrawView() {
    return new ModelAndView("web/wallet/withdraw");
  }

  @RequestMapping(path = "/account/withdraw", method = RequestMethod.POST)
  public ModelAndView withdraw(@RequestParam Double transactionValue) {
    ModelAndView view = new ModelAndView("web/wallet/withdraw");
    UserDTO user = userService.tryWithdrawValue(transactionValue);
    boolean transactionStatus = user != null;
    if (user != null) view.addObject(SystemConstant.USER_DTO, user);
    view.addObject(SystemConstant.TRANSACTION_STATUS, transactionStatus);
    return view;
  }

  @RequestMapping(path = "/profile/{username}", method = RequestMethod.GET)
  public ModelAndView getUserProfile(@PathVariable String username) {
    ModelAndView view = new ModelAndView("/web/auth/user-info");
    UserDTO user = userService.queryForProfile(username);
    List<CourseDTO> courses = courseService.listManagedCourses(username);
    courses.sort((course1, course2) -> course1.getNbMembers().compareTo(course2.getNbMembers()));
    Collections.reverse(courses);
    courses = courses.subList(0, Math.min(SystemConstant.BEST_COURSE_LIMIT_ITEM, courses.size()));
    view.addObject(SystemConstant.COURSE_AUTHOR_DTO, user);
    view.addObject(SystemConstant.COURSE_DTO_LIST, courses);
    return view;
  }

  @RequestMapping(path = "/account/edit-profile", method = RequestMethod.GET)
  public ModelAndView getEditProfileView() {
    ModelAndView view = new ModelAndView("web/auth/register");
    return view;
  }

  @RequestMapping(path = "/account/edit-profile", method = RequestMethod.POST)
  public ModelAndView editProfile(UserDTO userDTO) {
    UserDTO user = userService.updateUser(userDTO);
    user = userService.queryForProfile(user.getUsername());
    ModelAndView view = new ModelAndView("redirect:/profile/" + user.getUsername());
    view.addObject(SystemConstant.USER_DTO, user);
    return view;
  }

}
