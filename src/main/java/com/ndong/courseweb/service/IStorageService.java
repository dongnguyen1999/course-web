package com.ndong.courseweb.service;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
  String store(MultipartFile file, String path) throws IOException;
  Boolean delete(String path) throws IOException;
  Resource load(String filename);
}
