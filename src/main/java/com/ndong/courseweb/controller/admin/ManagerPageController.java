package com.ndong.courseweb.controller.admin;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.dto.CategoryDTO;
import com.ndong.courseweb.service.ICategoryService;
import com.ndong.courseweb.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ManagerPageController {

  @Autowired
  private ICategoryService categoryService;

  @RequestMapping(path = "/admin/manage/category", method = RequestMethod.GET)
  public ModelAndView getListCategory() {
    ModelAndView view = new ModelAndView("/admin/manager/category");
    List<CategoryDTO> categories = categoryService.findAll();
    view.addObject(SystemConstant.CATEGORY_DTO_LIST, categories);
    return view;
  }
}