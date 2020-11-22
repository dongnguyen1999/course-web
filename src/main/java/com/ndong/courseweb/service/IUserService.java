package com.ndong.courseweb.service;

import com.ndong.courseweb.dto.UserDTO;

public interface IUserService {
  UserDTO tryRegisterAccount(UserDTO model);
  UserDTO findOneUser(String username, String password);
  UserDTO findOneUser(String username);
  UserDTO findOneUser(Long id);
  UserDTO queryForProfile(String username);
  UserDTO findPermissionOnCourse(String username, String courseCode);
  UserDTO tryWithdrawValue(Double value);
  UserDTO tryPayInValue(Double value);
  Boolean checkBalanceForPurchase(String courseCode);
  Boolean purchaseCourse(String username, String courseCode);
  UserDTO updateUser(UserDTO model);
}
