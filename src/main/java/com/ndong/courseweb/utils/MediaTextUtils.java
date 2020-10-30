package com.ndong.courseweb.utils;

import java.util.Objects;

public class MediaTextUtils {
  public static String avatarPath(String username){
    String pattern =  "/${username}/avatar";
    return pattern.replace("${username}", username);
  }

  public static String thumbnailPath(String username, Long courseId){
    String pattern =  "/${username}/course/${courseId}/thumbnail";
    return pattern.replace("${username}", username)
        .replace("${courseId}", courseId.toString());
  }

  public static String courseDirectory(String username, Long courseId) {
    String pattern =  "/${username}/course/${courseId}";
    return pattern.replace("${username}", username)
        .replace("${courseId}", courseId.toString());
  }

  public static String avatarCaption(Long userId){
    String pattern = "@Avatar(${userId})";
    return pattern.replace("${userId}", userId.toString());
  }

  public static String thumbnailCaption(Long id){
    String pattern = "@Thumbnail(${courseId})";
    return pattern.replace("${courseId}", id.toString());
  }

  public static String getExtension(String filename) {
    try {
      return filename.substring(filename.lastIndexOf(".")+1);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return "";
    }
  }
}
