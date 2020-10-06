package com.ndong.courseweb.service.impl;

import com.ndong.courseweb.constant.CourseStatusConstant;
import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.constant.UserStatusConstant;
import com.ndong.courseweb.dto.CategoryDTO;
import com.ndong.courseweb.dto.CourseDTO;
import com.ndong.courseweb.entity.CategoryEntity;
import com.ndong.courseweb.entity.CourseEntity;
import com.ndong.courseweb.entity.UserEntity;
import com.ndong.courseweb.repository.CategoryRepository;
import com.ndong.courseweb.repository.CourseRepository;
import com.ndong.courseweb.service.ICourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class CourseService implements ICourseService {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Override
  public boolean tryOpenNewCourse(CourseDTO model) {
    try {
      CourseEntity course = modelMapper.map(model, CourseEntity.class);
      CategoryEntity category = categoryRepository.findOneByCode(model.getCategoryCode());
      course.setCategory(category);
      course.setThumbnail("empty-now-for-demo");
      course.setOpenTime(new Timestamp(System.currentTimeMillis()));
      course.setStatus(CourseStatusConstant.EARLY_ACCESS);
      courseRepository.save(course);
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }
}
