package com.ndong.courseweb.repository;

import com.ndong.courseweb.entity.CategoryEntity;
import com.ndong.courseweb.entity.CourseEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
  CourseEntity findOneByCode(String code);
  Page<CourseEntity> findAllByCategory(CategoryEntity category, Pageable pageable);
  Page<CourseEntity> findAllByCategoryOrderByOpenTimeDesc(CategoryEntity category, Pageable pageable);
  Page<CourseEntity> findByTitleContaining(String title, Pageable pageable);
  Page<CourseEntity> findByCategoryAndTitleContaining(CategoryEntity category, String title, Pageable pageable);
  Page<CourseEntity> findAllByCategoryAndStatusNotOrderByOpenTimeDesc(CategoryEntity category, String courseStatus, Pageable pageable);
  Page<CourseEntity> findAllByCategoryAndIdNotAndStatusNotOrderByOpenTimeDesc(CategoryEntity category, Long courseId, String courseStatus, Pageable pageable);
  Page<CourseEntity> findByTitleContainingAndStatusNotOrderByOpenTimeDesc(String title, String courseStatus, Pageable pageable);
  Page<CourseEntity> findByCategoryAndTitleContainingAndStatusNotOrderByOpenTimeDesc(CategoryEntity category, String title, String statusCode, Pageable pageable);
  Page<CourseEntity> findByStatusNotOrderByOpenTimeDesc(String statusCode, Pageable pageable);
  Page<CourseEntity> findAllByCategoryAndStatusNot(CategoryEntity category, String courseStatus, Pageable pageable);
  Page<CourseEntity> findByTitleContainingAndStatusNot(String title, String courseStatus, Pageable pageable);
  Page<CourseEntity> findByCategoryAndTitleContainingAndStatusNot(CategoryEntity category, String title, String statusCode, Pageable pageable);
  Page<CourseEntity> findByStatusNot(String statusCode, Pageable pageable);
}
