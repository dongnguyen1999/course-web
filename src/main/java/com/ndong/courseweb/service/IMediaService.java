package com.ndong.courseweb.service;

import java.util.List;

import com.ndong.courseweb.dto.LessonDTO;
import com.ndong.courseweb.dto.MediaDTO;
import com.ndong.courseweb.entity.CourseEntity;
import com.ndong.courseweb.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IMediaService {
  MediaDTO saveAvatar(MultipartFile file, UserEntity user);
  MediaDTO saveAvatar(MultipartFile file);
  MediaDTO saveThumbnail(MultipartFile file);
  MediaDTO saveThumbnail(MultipartFile file, CourseEntity course);
  Boolean cleanCourseMedia(CourseEntity course);
  byte[] loadResource(String code);
  List<MediaDTO> listMedia(Long courseId, Integer lessonNo);
  MediaDTO uploadMedia(MediaDTO model);
  MediaDTO uploadMedia(MediaDTO model, LessonDTO lesson);
  MediaDTO deleteMedia(Long mediaId);
}
