package com.ndong.courseweb.utils;

import com.ndong.courseweb.dto.MediaDTO;
import com.ndong.courseweb.entity.CourseEntity;
import com.ndong.courseweb.entity.MediaEntity;
import org.springframework.util.DigestUtils;

import java.text.Normalizer;

public class CodeFactory {
  public static String from(MediaEntity media) {
    return DigestUtils.md5DigestAsHex(media.getSource().getBytes());
  }

  public static String from(CourseEntity course) {
    String uTitle = Normalizer.normalize(course.getTitle(), Normalizer.Form.NFD);
    uTitle = uTitle.replaceAll("[^\\p{ASCII}]", "");
    uTitle += " " + course.getId();
    return String.join("-", uTitle.toLowerCase().split(" "));
  }

}
