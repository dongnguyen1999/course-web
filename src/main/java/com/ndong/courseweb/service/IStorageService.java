package com.ndong.courseweb.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface IStorageService {
  String store(MultipartFile file, String path) throws IOException;
  Boolean delete(String path) throws IOException;
  Resource load(String filename);
}
