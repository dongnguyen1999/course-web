package com.ndong.courseweb.utils;

import java.util.Objects;

public class MediaTextUtils {
  public static String avatarPath(String username){
    String pattern =  "/avatar/${username}";
    return pattern.replace("${username}", username);
  }

  public static String avatarCaption(String username){
    String pattern = "@Avatar(${username})";
    return pattern.replace("${username}", username);
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
