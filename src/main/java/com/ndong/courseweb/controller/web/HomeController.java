package com.ndong.courseweb.controller.web;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.entity.UserEntity;
import com.ndong.courseweb.model.UserModel;
import com.ndong.courseweb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

  @Autowired
  private IUserService userService;

  @RequestMapping(path = "/index", method = RequestMethod.GET)
  public ModelAndView indexPage() {
    return new ModelAndView("web/home");
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
  public ModelAndView register(UserModel userModel) {
    ModelAndView view = new ModelAndView("/web/auth/register");
    boolean registerStatus = userService.tryRegisterAccount(userModel);
    view.addObject(SystemConstant.REGISTER_STATUS, registerStatus);
    return view;
  }

  @RequestMapping(path = "/login", method = RequestMethod.POST)
  public ModelAndView login(UserModel userModel, HttpSession session) {
    ModelAndView view = new ModelAndView("/web/auth/login");
    UserEntity user = userService.findByUsernameAndPassword(userModel.getUsername(), userModel.getPassword());
    if (user != null) {
      session.setAttribute(SystemConstant.USER_MODEL, user);
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
