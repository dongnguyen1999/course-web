package com.ndong.courseweb.repository;

import com.ndong.courseweb.entity.CategoryEntity;
import com.ndong.courseweb.entity.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
  CourseEntity findOneByCode(String code);
  Page<CourseEntity> findAllByCategory(CategoryEntity category, Pageable pageable);
  Page<CourseEntity> findByTitleContaining(String title, Pageable pageable);
  Page<CourseEntity> findByCategoryAndTitleContaining(CategoryEntity category, String title, Pageable pageable);
}
