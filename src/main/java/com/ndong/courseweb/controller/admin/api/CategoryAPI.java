package com.ndong.courseweb.controller.admin.api;

import com.ndong.courseweb.dto.CategoryDTO;
import com.ndong.courseweb.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryAPI {

  @Autowired
  private ICategoryService categoryService;

  @RequestMapping(path = "/admin/api/category", method = RequestMethod.PUT)
  public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO) {
    CategoryDTO category = categoryService.updateCategory(categoryDTO);
    return (category != null)? new ResponseEntity<>(category, HttpStatus.OK):
        new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @RequestMapping(path = "/admin/api/category", method = RequestMethod.DELETE)
  public Boolean deleteCategory(@RequestBody Long[] ids){
    return categoryService.deleteCategories(ids);
  }

  @RequestMapping(path = "/admin/api/category", method = RequestMethod.POST)
  public ResponseEntity<CategoryDTO> addNewCategory(@RequestBody CategoryDTO categoryDTO) {
    return updateCategory(categoryDTO);
  }
}
