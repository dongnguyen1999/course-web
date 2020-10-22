package com.ndong.courseweb.controller.web;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.dto.UserDTO;
import com.ndong.courseweb.entity.UserEntity;
import com.ndong.courseweb.service.IUserService;
import com.ndong.courseweb.utils.SessionUtils;
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
  public ModelAndView indexPage() {
    return new ModelAndView("web/home");
  }

  @RequestMapping(path = "/login", method = RequestMethod.GET)
  public ModelAndView loginPage(@RequestParam(required = false) String nav) {
    ModelAndView view = new ModelAndView("/web/auth/login");
    if (nav != null) view.addObject(SystemConstant.NAVIGATE_URL, nav);
    return view;
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
  public ModelAndView login(UserDTO userDTO,
                            HttpSession session) {
    ModelAndView view = new ModelAndView("/web/auth/login");
    UserDTO user = userService.findOneUser(userDTO.getUsername(), userDTO.getPassword());
    String navigateUrl = userDTO.getNavigateUrl().orElse("/index");
    if (user != null) {
      SessionUtils sessionUtils = new SessionUtils(session);
      sessionUtils.setUser(user);
      String redirectView = "redirect:" + navigateUrl;
      return new ModelAndView(redirectView);
    } else view.addObject(SystemConstant.LOGIN_FAILED, true);
    return view;
  }

  @RequestMapping(path = "/logout", method = RequestMethod.GET)
  public ModelAndView logout(HttpSession session) {
    SessionUtils sessionUtils = new SessionUtils(session);
    sessionUtils.removeUser();
    return new ModelAndView("redirect:/index");
  }

  @RequestMapping(path = "/")
  public ModelAndView homepageRedirect() {
    return new ModelAndView("redirect:/index");
  }

}
