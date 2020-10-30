package com.ndong.courseweb.utils;

import com.ndong.courseweb.dto.CourseDTO;
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
    return fromCourse(course.getId(), course.getTitle());
  }

  public static String from(CourseDTO course) {
    return fromCourse(course.getId(), course.getTitle());
  }

  public static String fromCourse(Long courseId, String courseTitle) {
    String uTitle = Normalizer.normalize(courseTitle, Normalizer.Form.NFD);
    uTitle = uTitle.replaceAll("[^\\p{ASCII}]", "");
    uTitle = uTitle.replaceAll("['’:,/]", "");
    uTitle += " " + courseId;
    return String.join("-", uTitle.toLowerCase().split(" "));
  }

}
