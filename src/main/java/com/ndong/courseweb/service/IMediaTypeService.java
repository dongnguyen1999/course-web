package com.ndong.courseweb.service;

import com.ndong.courseweb.dto.CategoryDTO;
import com.ndong.courseweb.dto.MediaTypeDTO;
import com.ndong.courseweb.entity.MediaTypeEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMediaTypeService {
  List<MediaTypeDTO> findAll();
  MediaTypeDTO updateMediaType(MediaTypeDTO model);
  List<MediaTypeDTO> deleteMediaTypes(Long[] ids);
  MediaTypeDTO findByCode(String code);
  MediaTypeEntity getMediaTypeFromFile(MultipartFile file);
}
