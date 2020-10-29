package com.ndong.courseweb.controller.admin.api;

import com.ndong.courseweb.dto.CategoryDTO;
import com.ndong.courseweb.dto.MediaTypeDTO;
import com.ndong.courseweb.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
  public ResponseEntity<List<CategoryDTO>> deleteCategory(@RequestBody Long[] ids){
    List<CategoryDTO> mediaTypes = categoryService.deleteCategories(ids);
    return (mediaTypes != null)? new ResponseEntity<>(mediaTypes, HttpStatus.OK):
        new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @RequestMapping(path = "/admin/api/category", method = RequestMethod.POST)
  public ResponseEntity<CategoryDTO> addNewCategory(@RequestBody CategoryDTO categoryDTO) {
    return updateCategory(categoryDTO);
  }

  @RequestMapping(path = "/admin/api/category", method = RequestMethod.GET)
  public ResponseEntity<CategoryDTO> getCategoryInfo(@RequestParam String code) {
    CategoryDTO category = categoryService.findByCode(code);
    return (category != null)? new ResponseEntity<>(category, HttpStatus.OK):
        new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
