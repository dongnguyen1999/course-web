package com.ndong.courseweb.service;

import com.ndong.courseweb.dto.UserDTO;

public interface IUserService {
  UserDTO tryRegisterAccount(UserDTO model);
  UserDTO findOneUser(String username, String password);
  UserDTO findOneUser(String username);
  UserDTO findOneUser(Long id);
  UserDTO findPermissionOnCourse(String username, String courseCode);
}
