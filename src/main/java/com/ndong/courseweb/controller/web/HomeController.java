package com.ndong.courseweb.controller.web;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.dto.UserDTO;
import com.ndong.courseweb.entity.UserEntity;
import com.ndong.courseweb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

  @Autowired
  private IUserService userService;

  @RequestMapping(path = "/index", method = RequestMethod.GET)
  public ModelAndView indexPage(
      @RequestParam(required = false) String feature,
      @RequestParam(required = false) String contact) {
    ModelAndView view = new ModelAndView("web/home");
    if (feature != null) view.addObject(SystemConstant.NAV_FEATURE, true);
    if (contact != null) view.addObject(SystemConstant.NAV_CONTACT, true);
    return view;
  }

  @RequestMapping(path = "/login", method = RequestMethod.GET)
  public ModelAndView loginPage() {
    return new ModelAndView("/web/auth/login");
  }

  @RequestMapping(path = "/register", method = RequestMethod.GET)
  public ModelAndView registerPage() {
    return new ModelAndView("/web/auth/register");
  }

  @RequestMapping(path = "/register", method = RequestMethod.POST)
  public ModelAndView register(UserDTO userDTO) {
    ModelAndView view = new ModelAndView("/web/auth/register");
    boolean registerStatus = userService.tryRegisterAccount(userDTO);
    view.addObject(SystemConstant.REGISTER_STATUS, registerStatus);
    return view;
  }

  @RequestMapping(path = "/login", method = RequestMethod.POST)
  public ModelAndView login(UserDTO userDTO, HttpSession session) {
    ModelAndView view = new ModelAndView("/web/auth/login");
    UserDTO user = userService.findOneUser(userDTO.getUsername(), userDTO.getPassword());
    if (user != null) {
      session.setAttribute(SystemConstant.USER_DTO, user);
      String redirectView = "redirect:/index";
      return new ModelAndView(redirectView);
    } else view.addObject(SystemConstant.LOGIN_FAILED, true);
    return view;
  }

  @RequestMapping(path = "/")
  public ModelAndView homepageRedirect() {
    return new ModelAndView("redirect:/index");
  }

}
