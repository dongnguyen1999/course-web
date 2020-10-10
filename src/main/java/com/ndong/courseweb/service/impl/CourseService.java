package com.ndong.courseweb.service.impl;

import com.ndong.courseweb.constant.CourseStatusConstant;
import com.ndong.courseweb.constant.DefaultValueConstant;
import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.constant.UserStatusConstant;
import com.ndong.courseweb.dto.CategoryDTO;
import com.ndong.courseweb.dto.CourseDTO;
import com.ndong.courseweb.dto.LessonDTO;
import com.ndong.courseweb.entity.CategoryEntity;
import com.ndong.courseweb.entity.CourseEntity;
import com.ndong.courseweb.entity.LessonEntity;
import com.ndong.courseweb.entity.UserEntity;
import com.ndong.courseweb.entity.composite_id.LessonId;
import com.ndong.courseweb.repository.CategoryRepository;
import com.ndong.courseweb.repository.CourseRepository;
import com.ndong.courseweb.repository.LessonRepository;
import com.ndong.courseweb.service.ICourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class CourseService implements ICourseService {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private LessonRepository lessonRepository;

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

  @Override
  public boolean tryCreateNewLesson(LessonDTO model) {
    try {
      Integer maxAvailableLessonNo = lessonRepository.findMaxNoByCourseId(model.getCourseId()) + 1;
      LessonEntity lesson = modelMapper.map(model, LessonEntity.class);
      lesson.setId(new LessonId(model.getCourseId(), maxAvailableLessonNo));
      lesson.setUploadTime(new Timestamp(System.currentTimeMillis()));
      lesson.setEnableFreeTrial(false);
      if (lesson.getShortDescription() == null) lesson.setShortDescription(DefaultValueConstant.LESSON_SHORT_DESCRIPTION);
      if (lesson.getContent() == null) lesson.setContent(DefaultValueConstant.LESSON_CONTENT);
      lessonRepository.save(lesson);
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  @Override
  public CourseDTO findOneCourse(Long courseId) {
    Optional<CourseEntity> course = courseRepository.findById(courseId);
    Integer maxLessonId = lessonRepository.findMaxNoByCourseId(courseId);
    CourseDTO dto = course.map(courseEntity -> modelMapper.map(courseEntity, CourseDTO.class)).orElse(null);
    if (dto != null) dto.setNextAvailableLessonNo(maxLessonId+1);
    return dto;
  }
}
