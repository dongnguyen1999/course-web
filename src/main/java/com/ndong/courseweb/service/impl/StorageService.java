package com.ndong.courseweb.service.impl;

import com.ndong.courseweb.service.IStorageService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Service

public class StorageService implements IStorageService {

  private static final String STORAGE_LOCATION = "/data/upload";

  @Autowired
  private ResourceLoader resourceLoader;

  @Override
  public String store(MultipartFile file, String path) throws IOException {
    String filename = file.getOriginalFilename();
    File storeDir = new File(STORAGE_LOCATION + File.separator + path);
    boolean storageReady = true;
    if (!storeDir.exists()) storageReady = storeDir.mkdirs();
    if (storageReady) {
      File dest = new File( storeDir.getPath() + File.separator + filename);
      file.transferTo(dest);
      return dest.getPath();
    }
    return null;
  }

  @Override
  public Boolean delete(String path) throws IOException {
    File dir = new File(STORAGE_LOCATION + File.separator + path);
    if (dir.exists()) {
      FileUtils.deleteDirectory(dir);
      return true;
    }
    return false;
  }

  @Override
  public Resource load(String filename) {
    return resourceLoader.getResource("file://" + filename);
  }

}
