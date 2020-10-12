package com.ndong.courseweb.service.impl;

import com.ndong.courseweb.dto.CategoryDTO;
import com.ndong.courseweb.entity.CategoryEntity;
import com.ndong.courseweb.repository.CategoryRepository;
import com.ndong.courseweb.service.ICategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public CategoryDTO findByCode(String code) {
    CategoryEntity category = categoryRepository.findOneByCode(code);
    return (category != null) ? modelMapper.map(category, CategoryDTO.class) : null;
  }

  @Override
  public List<CategoryDTO> findAll() {
    List<CategoryEntity> categories = categoryRepository.findAll();
    List<CategoryDTO> dtos = new ArrayList<>();
    for (CategoryEntity category : categories) {
      dtos.add(modelMapper.map(category, CategoryDTO.class));
    }
    return dtos;
  }

  @Override
  public CategoryDTO updateCategory(CategoryDTO model) {
    try {
      CategoryEntity category = (model.getId() != null) ?
          categoryRepository.findById(model.getId()).orElse(new CategoryEntity()) :
          new CategoryEntity();
      modelMapper.map(model, category);
      CategoryEntity newCategory = categoryRepository.save(category);
      return modelMapper.map(newCategory, CategoryDTO.class);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  @Override
  public Boolean deleteCategories(Long[] ids) {
    try {
      categoryRepository.deleteAll(categoryRepository.findAllById(Arrays.asList(ids)));
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

}
