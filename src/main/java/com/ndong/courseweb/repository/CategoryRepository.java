package com.ndong.courseweb.repository;

import com.ndong.courseweb.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
  CategoryEntity findOneByCode(String code);
}
