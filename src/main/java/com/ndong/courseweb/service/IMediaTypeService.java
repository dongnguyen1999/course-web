package com.ndong.courseweb.service;

import java.util.List;

import com.ndong.courseweb.dto.MediaTypeDTO;
import com.ndong.courseweb.entity.MediaTypeEntity;

import org.springframework.web.multipart.MultipartFile;

public interface IMediaTypeService {
  List<MediaTypeDTO> findAll();
  MediaTypeDTO updateMediaType(MediaTypeDTO model);
  List<MediaTypeDTO> deleteMediaTypes(Long[] ids);
  MediaTypeDTO findByCode(String code);
  MediaTypeEntity getMediaTypeFromFile(MultipartFile file);
}
