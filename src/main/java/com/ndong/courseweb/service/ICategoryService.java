package com.ndong.courseweb.service;

import com.ndong.courseweb.dto.CategoryDTO;

import java.util.List;

public interface ICategoryService {
  CategoryDTO findByCode(String code);
  List<CategoryDTO> findAll();
}
