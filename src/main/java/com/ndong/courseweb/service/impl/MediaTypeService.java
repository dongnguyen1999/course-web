package com.ndong.courseweb.service.impl;

import com.ndong.courseweb.dto.CategoryDTO;
import com.ndong.courseweb.dto.MediaTypeDTO;
import com.ndong.courseweb.entity.CategoryEntity;
import com.ndong.courseweb.entity.MediaTypeEntity;
import com.ndong.courseweb.repository.MediaTypeRepository;
import com.ndong.courseweb.service.IMediaTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MediaTypeService implements IMediaTypeService {

  @Autowired
  private MediaTypeRepository mediaTypeRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public List<MediaTypeDTO> findAll() {
    List<MediaTypeEntity> mediaTypes = mediaTypeRepository.findAll();
    List<MediaTypeDTO> dtos = new ArrayList<>();
    for (MediaTypeEntity mediaType : mediaTypes) {
      dtos.add(modelMapper.map(mediaType, MediaTypeDTO.class));
    }
    return dtos;
  }

  @Override
  public MediaTypeDTO updateMediaType(MediaTypeDTO model) {
    try {
      MediaTypeEntity mediaType = (model.getId() != null) ?
          mediaTypeRepository.findById(model.getId()).orElse(new MediaTypeEntity()) :
          new MediaTypeEntity();
      modelMapper.map(model, mediaType);
      MediaTypeEntity newMediaType = mediaTypeRepository.save(mediaType);
      return modelMapper.map(newMediaType, MediaTypeDTO.class);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  @Override
  public Boolean deleteMediaTypes(Long[] ids) {
    try {
      mediaTypeRepository.deleteAll(mediaTypeRepository.findAllById(Arrays.asList(ids)));
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  @Override
  public MediaTypeDTO findByCode(String code) {
    MediaTypeEntity mediaType = mediaTypeRepository.findOneByCode(code);
    return (mediaType != null) ? modelMapper.map(mediaType, MediaTypeDTO.class) : null;
  }
}
