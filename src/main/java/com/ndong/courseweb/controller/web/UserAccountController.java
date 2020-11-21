package com.ndong.courseweb.controller.web;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.dto.UserDTO;
import com.ndong.courseweb.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserAccountController {

  @Autowired
  private IUserService userService;

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

}
