package com.ndong.courseweb.controller.admin;

import java.util.List;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.dto.CategoryDTO;
import com.ndong.courseweb.dto.MediaTypeDTO;
import com.ndong.courseweb.service.ICategoryService;
import com.ndong.courseweb.service.IMediaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ManagerPageController {

  @Autowired
  private ICategoryService categoryService;

  @Autowired
  private IMediaTypeService mediaTypeService;

  @RequestMapping(path = "/admin/manage/category", method = RequestMethod.GET)
  public ModelAndView getListCategory() {
    ModelAndView view = new ModelAndView("/admin/manager/category");
    List<CategoryDTO> categories = categoryService.findAll();
    view.addObject(SystemConstant.CATEGORY_DTO_LIST, categories);
    return view;
  }

  @RequestMapping(path = "/admin/manage/media-type", method = RequestMethod.GET)
  public ModelAndView getListMediaType() {
    ModelAndView view = new ModelAndView("/admin/manager/media-type");
    List<MediaTypeDTO> mediaTypes = mediaTypeService.findAll();
    view.addObject(SystemConstant.MEDIA_TYPE_DTO_LIST, mediaTypes);
    return view;
  }
}
