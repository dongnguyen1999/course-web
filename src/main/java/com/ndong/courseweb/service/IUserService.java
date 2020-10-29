package com.ndong.courseweb.service;

import com.ndong.courseweb.dto.CourseDTO;
import com.ndong.courseweb.dto.UserDTO;
import com.ndong.courseweb.entity.UserEntity;

public interface IUserService {
  UserDTO tryRegisterAccount(UserDTO model);
  UserDTO findOneUser(String username, String password);
  UserDTO findOneUser(String username);
  UserDTO findOneUser(Long id);
  UserDTO findPermissionOnCourse(String username, String courseCode);
}
