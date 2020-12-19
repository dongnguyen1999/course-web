package com.ndong.courseweb.service.impl;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.dto.LessonDTO;
import com.ndong.courseweb.dto.MediaDTO;
import com.ndong.courseweb.dto.UserDTO;
import com.ndong.courseweb.entity.CourseEntity;
import com.ndong.courseweb.entity.LessonEntity;
import com.ndong.courseweb.entity.MediaEntity;
import com.ndong.courseweb.entity.MediaTypeEntity;
import com.ndong.courseweb.entity.UserEntity;
import com.ndong.courseweb.entity.composite_id.LessonId;
import com.ndong.courseweb.repository.LessonRepository;
import com.ndong.courseweb.repository.MediaRepository;
import com.ndong.courseweb.repository.MediaTypeRepository;
import com.ndong.courseweb.service.IMediaService;
import com.ndong.courseweb.service.IMediaTypeService;
import com.ndong.courseweb.service.IStorageService;
import com.ndong.courseweb.utils.CodeFactory;
import com.ndong.courseweb.utils.MediaTextUtils;
import com.ndong.courseweb.utils.SessionUtils;

import com.ndong.courseweb.utils.YoutubeApiUtils;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MediaService implements IMediaService {

  @Autowired
  private IStorageService storageService;

  @Autowired
  private IMediaTypeService mediaTypeService;

  @Autowired
  private MediaTypeRepository mediaTypeRepository;

  @Autowired
  private MediaRepository mediaRepository;

  @Autowired
  private LessonRepository lessonRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private SessionUtils sessionUtils;

  @Autowired
  private YoutubeApiUtils youtubeApiUtils;

  @Override
  public MediaDTO saveAvatar(MultipartFile file, UserEntity user) {
    if (file == null || Objects.requireNonNull(file.getOriginalFilename()).isBlank())
      return null;
    try {
      String path = MediaTextUtils.avatarPath(user.getUsername());
      MediaEntity media = new MediaEntity();
      media.setSource(storageService.store(file, path));
      media.setCode(CodeFactory.from(media));
      media.setCaption(MediaTextUtils.avatarCaption(user.getId()));
      media.setMediaType(mediaTypeService.getMediaTypeFromFile(file));
      media = mediaRepository.save(media);
      return modelMapper.map(media, MediaDTO.class);
    } catch (IOException e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  @Override
  public MediaDTO saveAvatar(MultipartFile file) {
    return null;
  }

  @Override
  public MediaDTO saveThumbnail(MultipartFile file) {
    return null;
  }

  @Override
  public MediaDTO saveThumbnail(MultipartFile file, CourseEntity course) {
    if (file == null || Objects.requireNonNull(file.getOriginalFilename()).isBlank())
      return null;
    try {
      String path = MediaTextUtils.thumbnailPath(course.getUser().getUsername(), course.getId());
      MediaEntity media = new MediaEntity();
      media.setSource(storageService.store(file, path));
      media.setCode(CodeFactory.from(media));
      media.setCaption(MediaTextUtils.thumbnailCaption(course.getId()));
      media.setMediaType(mediaTypeService.getMediaTypeFromFile(file));
      media = mediaRepository.save(media);
      return modelMapper.map(media, MediaDTO.class);
    } catch (IOException e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  @Override
  public void cleanCourseMedia(CourseEntity course) {
    try {
      UserEntity author = course.getUser();
      String path = MediaTextUtils.courseDirectory(author.getUsername(), course.getId());
      storageService.deleteByRelativePath(path);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void cleanLessonMedia(LessonEntity lesson) {
    try {
      UserEntity author = lesson.getCourse().getUser();
      String path = MediaTextUtils.mediaPath(author.getUsername(), lesson.getId());
      storageService.deleteByRelativePath(path);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public byte[] loadResource(String code) {
    MediaEntity media = mediaRepository.findOneByCode(code);
    if (media.getMediaType().getCode().contains(SystemConstant.IMAGE_MEDIA_CODE)) {
      Resource resource = storageService.load(media.getSource());
      try {
        return IOUtils.toByteArray(resource.getInputStream());
      } catch (IOException e) {
        System.out.println(e.getMessage());
        return null;
      }
    } else if (media.getMediaType().getCode().contains(SystemConstant.VIDEO_MEDIA_CODE)) {
      try {
        String videoId = MediaTextUtils.getYoutubeMediaId(media.getSource());
        String thumbnailUrl = youtubeApiUtils.getThumbnailImageLink(videoId);
        return youtubeApiUtils.getThumbnailResource(thumbnailUrl);
      } catch (Exception e) {
        System.out.println(e.getMessage());
        return null;
      }
    }
    return null;
  }

  @Override
  public List<MediaDTO> listMedia(Long courseId, Integer lessonNo) {
    LessonId lessonId = new LessonId(courseId, lessonNo);
    LessonEntity lesson = lessonRepository.getOne(lessonId);
    List<MediaEntity> medias = new ArrayList<>(lesson.getMediaSet());
    List<MediaDTO> dtos = medias.stream().map(media -> modelMapper.map(media, MediaDTO.class))
        .collect(Collectors.toList());
    return dtos.stream().sorted((dto1, dto2) -> dto2.getId().compareTo(dto1.getId())).map(dto -> {
      File file = new File(dto.getSource());
      dto.setFilename(file.getName());
      MediaTypeEntity mediaType = mediaTypeRepository.findOneByCode(dto.getMediaTypeCode());
      String mediaTypeName = mediaType.getName();
      dto.setMediaTypeName(mediaTypeName.substring(0, mediaTypeName.indexOf("(")));
      return dto;
    }).collect(Collectors.toList());
  }

  @Override
  public MediaDTO uploadMedia(MediaDTO model) {
    return uploadMedia(model, null);
  }

  @Override
  public MediaDTO deleteMedia(Long mediaId) {
    try {
      MediaEntity media = mediaRepository.getOne(mediaId);
      MediaDTO dto = modelMapper.map(media, MediaDTO.class);
      if (media.getMediaType().getCode().contains(SystemConstant.IMAGE_MEDIA_CODE))
        storageService.deleteByPath(media.getSource());
      mediaRepository.delete(media);
      return dto;
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public MediaDTO uploadMedia(MediaDTO model, LessonDTO lessonDTO) {
    MultipartFile file = model.getRawFile();
    if (file == null || Objects.requireNonNull(file.getOriginalFilename()).isBlank())
      return null;
    try {
      String filename = model.getRawFile().getOriginalFilename();
      assert filename != null;
      String extension = filename.substring(filename.lastIndexOf(".") + 1);
      MediaTypeEntity mediaType = mediaTypeRepository.findTopByExtensionContaining(extension);
      LessonEntity lesson;
      if (lessonDTO != null)
        lesson = lessonRepository.getOne(new LessonId(lessonDTO.getCourseId(), lessonDTO.getIdNo()));
      else
        lesson = lessonRepository.getOne(new LessonId(model.getCourseId(), model.getLessonNo()));
      MediaEntity media = modelMapper.map(model, MediaEntity.class);
      media.setMediaType(mediaType);
      media.setLesson(lesson);
      if (mediaType.getCode().contains(SystemConstant.VIDEO_MEDIA_CODE)) {
        String videoId = youtubeApiUtils.upload(file, media.getCaption());
        media.setSource(videoId + File.separator + filename);
      } else {
        UserDTO currentUser = sessionUtils.getUser();
        String path = MediaTextUtils.mediaPath(currentUser.getUsername(), lesson.getId());
        media.setSource(storageService.store(file, path));
      }
      media.setCode(CodeFactory.from(media));
      media = mediaRepository.save(media);
      return modelMapper.map(media, MediaDTO.class);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }
}
