package com.ndong.courseweb.service.impl;

import com.ndong.courseweb.constant.CourseStatusConstant;
import com.ndong.courseweb.constant.DefaultValueConstant;
import com.ndong.courseweb.constant.FilterCodeConstant;
import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.dto.CourseDTO;
import com.ndong.courseweb.dto.LessonDTO;
import com.ndong.courseweb.dto.MediaDTO;
import com.ndong.courseweb.dto.query_result.NbUserPerCourseIdDTO;
import com.ndong.courseweb.entity.CategoryEntity;
import com.ndong.courseweb.entity.CourseEntity;
import com.ndong.courseweb.entity.LessonEntity;
import com.ndong.courseweb.entity.composite_id.LessonId;
import com.ndong.courseweb.filter.impl.CourseFilter;
import com.ndong.courseweb.repository.*;
import com.ndong.courseweb.service.ICourseService;
import com.ndong.courseweb.service.IMediaService;
import com.ndong.courseweb.utils.CodeFactory;
import com.ndong.courseweb.utils.SessionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

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

  @Autowired
  private IMediaService mediaService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PurchaseDetailRepository purchaseDetailRepository;

  @Autowired
  private HttpSession session;

  @Autowired
  private CourseFilter courseFilter;

  @Override
  @Transactional
  public boolean tryOpenNewCourse(CourseDTO model) {
    try {
      CourseEntity course = modelMapper.map(model, CourseEntity.class);
      CategoryEntity category = categoryRepository.findOneByCode(model.getCategoryCode());
      course.setUser(userRepository.getOne(1L));
      course.setCategory(category);
      course.setThumbnail("thumbnail");
      course.setCode("code");
      course.setOpenTime(new Timestamp(System.currentTimeMillis()));
      course.setStatus(CourseStatusConstant.EARLY_ACCESS);
      course = courseRepository.save(course);
      course.setCode(CodeFactory.from(course));
      MultipartFile thumbnailFile = model.getThumbnailFile();
      if (thumbnailFile != null &&
          !Objects.requireNonNull(thumbnailFile.getOriginalFilename()).isBlank()) {
        MediaDTO mediaDTO = mediaService.saveThumbnail(thumbnailFile, course);
        course.setThumbnail(mediaDTO.getCode());
      }
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
      if (lesson.getShortDescription() == null)
        lesson.setShortDescription(DefaultValueConstant.LESSON_SHORT_DESCRIPTION);
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
    if (dto != null) {
      dto.setNextAvailableLessonNo(maxLessonId + 1);
      dto.setNbMembers(purchaseDetailRepository.countByCourseId(courseId));
    }
    return dto;
  }

  @Override
  public CourseDTO findOneCourse(String courseCode) {
    CourseEntity course = courseRepository.findOneByCode(courseCode);
    return findOneCourse(course.getId());
  }

  @Override
  public List<CourseDTO> listTopPopularCourses(Integer limit) {
    List<NbUserPerCourseIdDTO> nbUserPerCourse = purchaseDetailRepository.countNbUserPerCourseId();
    if (limit == null) limit = SystemConstant.DEFAULT_LIMIT_ITEM;
    if (nbUserPerCourse.size() > limit)
      nbUserPerCourse = nbUserPerCourse.stream().limit(limit).collect(Collectors.toList());
    return nbUserPerCourse.stream().map(element -> {
      CourseEntity course = courseRepository.findById(element.getCourseId()).orElse(new CourseEntity());
      CourseDTO dto = modelMapper.map(course, CourseDTO.class);
      dto.setNbMembers(element.getNumberUser());
      return dto;
    }).collect(Collectors.toList());
  }

  @Override
  public List<CourseDTO> listCourse(String searchText, String filterCode, Pageable pageable, String categoryCode) {
    List<CourseEntity> courses = new ArrayList<>();
    courses = courseFilter.filter(searchText, filterCode, pageable, categoryCode);
    return courses.stream().map(course -> {
      CourseDTO dto = modelMapper.map(course, CourseDTO.class);
      dto.setNbMembers(purchaseDetailRepository.countByCourseId(dto.getId()));
      return dto;
    }).collect(Collectors.toList());
  }

  @Override
  public List<CourseDTO> listRelatedCourses(String categoryCode) {
    CategoryEntity category = categoryRepository.findOneByCode(categoryCode);
    Page<CourseEntity> courses = courseRepository.findAllByCategoryOrderByOpenTimeDesc(category,
        PageRequest.of(0, 8));
    return courses.get().map(course -> modelMapper.map(course, CourseDTO.class)).
        collect(Collectors.toList());
  }

}
