package com.ndong.courseweb.service.impl;

import com.ndong.courseweb.constant.CourseStatusConstant;
import com.ndong.courseweb.constant.DefaultValueConstant;
import com.ndong.courseweb.constant.FilterCodeConstant;
import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.dto.CourseDTO;
import com.ndong.courseweb.dto.LessonDTO;
import com.ndong.courseweb.dto.MediaDTO;
import com.ndong.courseweb.dto.UserDTO;
import com.ndong.courseweb.dto.query_result.NbUserPerCourseIdDTO;
import com.ndong.courseweb.entity.*;
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
  private CourseFilter courseFilter;

  @Autowired
  private SessionUtils sessionUtils;

  @Override
  @Transactional
  public CourseDTO tryOpenNewCourse(CourseDTO model) {
    try {
      CourseEntity course = modelMapper.map(model, CourseEntity.class);
      CategoryEntity category = categoryRepository.findOneByCode(model.getCategoryCode());
      UserDTO user = sessionUtils.getUser();
      course.setUser(userRepository.findOneByUsername(user.getUsername()));
      course.setCategory(category);
      course.setThumbnail("thumbnail");
      course.setCode("code");
      course.setOpenTime(new Timestamp(System.currentTimeMillis()));
      course.setStatus(CourseStatusConstant.EARLY_ACCESS);
      course = courseRepository.save(course);
      course.setCode(CodeFactory.from(course));
      MediaDTO mediaDTO = mediaService.saveThumbnail(model.getThumbnailFile(), course);
      if (mediaDTO != null) course.setThumbnail(mediaDTO.getCode());
      course = courseRepository.save(course);
      return modelMapper.map(course, CourseDTO.class);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  @Override
  public LessonDTO tryCreateNewLesson(LessonDTO model) {
    try {
      Integer maxAvailableLessonNo = lessonRepository.findMaxNoByCourseId(model.getCourseId()) + 1;
      LessonEntity lesson = modelMapper.map(model, LessonEntity.class);
      lesson.setId(new LessonId(model.getCourseId(), maxAvailableLessonNo));
      lesson.setUploadTime(new Timestamp(System.currentTimeMillis()));
      if (lesson.getShortDescription() == null)
        lesson.setShortDescription(DefaultValueConstant.LESSON_SHORT_DESCRIPTION);
      if (lesson.getContent() == null) lesson.setContent(DefaultValueConstant.LESSON_CONTENT);
      lesson = lessonRepository.save(lesson);
      CourseEntity course = courseRepository.findById(model.getCourseId()).orElse(new CourseEntity());
      course.setStatus(CourseStatusConstant.PROGRESSING);
      courseRepository.save(course);
      return modelMapper.map(lesson, LessonDTO.class);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
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
  public List<CourseDTO> listCourses(String searchText, String filterCode, Pageable pageable, String categoryCode) {
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

  @Override
  public CourseDTO completeCourse(Long courseId) {
    CourseEntity course = courseRepository.findById(courseId).orElse(new CourseEntity());
    course.setStatus(CourseStatusConstant.COMPLETE);
    return modelMapper.map(courseRepository.save(course), CourseDTO.class);
  }

  @Override
  public LessonDTO popLesson(Long courseId) {
    CourseEntity course = courseRepository.findById(courseId).orElse(new CourseEntity());
    LessonEntity latestLesson = lessonRepository.findTopByCourseOrderByIdDesc(course);
    lessonRepository.delete(latestLesson);
    return modelMapper.map(latestLesson, LessonDTO.class);
  }

  @Override
  public List<LessonDTO> listLessons(Long courseId) {
    CourseEntity course = courseRepository.findById(courseId).orElse(new CourseEntity());
    List<LessonEntity> lessons = lessonRepository.findAllByCourseOrderById(course);
    return lessons.stream().map(lesson -> modelMapper.map(lesson, LessonDTO.class)).
        collect(Collectors.toList());
  }

  @Override
  public CourseDTO updateCourse(CourseDTO model) {
    CourseEntity course = courseRepository.findById(model.getId()).orElse(new CourseEntity());
    CategoryEntity category = categoryRepository.findOneByCode(model.getCategoryCode());
    course.setCategory(category);
    model.setThumbnail(course.getThumbnail());
    model.setCode(CodeFactory.from(model));
    model.setOpenTime(course.getOpenTime());
    model.setStatus(course.getStatus());
    model.setUserId(course.getUser().getId());
    modelMapper.map(model, course);
    MediaDTO mediaDTO = mediaService.saveThumbnail(model.getThumbnailFile(), course);
    if (mediaDTO != null) course.setThumbnail(mediaDTO.getCode());
    course = courseRepository.save(course);
    return modelMapper.map(course, CourseDTO.class);
  }

  @Override
  public CourseDTO deleteCourse(Long courseId) {
    try {
      CourseEntity course = courseRepository.findById(courseId).orElse(new CourseEntity());
      course.setStatus(CourseStatusConstant.DELETED);
      CourseDTO dto = modelMapper.map(courseRepository.save(course), CourseDTO.class);
      Set<PurchaseDetailEntity> members = course.getPurchaseDetailSet();
      if (members.size() == 0) {
        mediaService.cleanCourseMedia(course);
        courseRepository.delete(course);
      }
      return dto;
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }
}
