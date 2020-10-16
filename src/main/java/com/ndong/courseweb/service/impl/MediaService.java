package com.ndong.courseweb.service.impl;

import com.ndong.courseweb.dto.MediaDTO;
import com.ndong.courseweb.dto.MediaTypeDTO;
import com.ndong.courseweb.entity.CourseEntity;
import com.ndong.courseweb.entity.MediaEntity;
import com.ndong.courseweb.entity.MediaTypeEntity;
import com.ndong.courseweb.entity.UserEntity;
import com.ndong.courseweb.repository.MediaRepository;
import com.ndong.courseweb.repository.MediaTypeRepository;
import com.ndong.courseweb.service.IMediaService;
import com.ndong.courseweb.service.IMediaTypeService;
import com.ndong.courseweb.service.IStorageService;
import com.ndong.courseweb.utils.CodeFactory;
import com.ndong.courseweb.utils.MediaTextUtils;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
public class MediaService implements IMediaService {

  @Autowired
  private IStorageService storageService;

  @Autowired
  private MediaTypeRepository mediaTypeRepository;

  @Autowired
  private IMediaTypeService mediaTypeService;

  @Autowired
  private MediaRepository mediaRepository;

  @Autowired
  private ModelMapper modelMapper;


  @Override
  public MediaDTO saveAvatar(MultipartFile file, UserEntity user) {
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
    try {
      String path = MediaTextUtils.thumbnailPath(course.getUser().getUsername(), course.getCode());
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
  public byte[] loadResource(String code) {
    MediaEntity media = mediaRepository.findOneByCode(code);
    if (media.getMediaType().getCode().contains("image")) {
      Resource resource = storageService.load(media.getSource());
      try {
        return IOUtils.toByteArray(resource.getInputStream());
      } catch (IOException e) {
        System.out.println(e.getMessage());
        return null;
      }
    }
    return null;
  }

}
