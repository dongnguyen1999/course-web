package com.ndong.courseweb.service;

import com.ndong.courseweb.dto.MediaDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IMediaService {
  MediaDTO saveAvatar(MultipartFile file, String username);
  byte[] loadResource(String code);
}
