package com.ndong.courseweb.service;

import com.ndong.courseweb.dto.CategoryDTO;
import com.ndong.courseweb.dto.MediaTypeDTO;

import java.util.List;

public interface IMediaTypeService {
  List<MediaTypeDTO> findAll();
  MediaTypeDTO updateMediaType(MediaTypeDTO model);
  Boolean deleteMediaTypes(Long[] ids);
  MediaTypeDTO findByCode(String code);
}
