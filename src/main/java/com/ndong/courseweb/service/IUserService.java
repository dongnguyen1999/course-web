package com.ndong.courseweb.service;

import com.ndong.courseweb.entity.UserEntity;
import com.ndong.courseweb.model.UserModel;

public interface IUserService {
  boolean tryRegisterAccount(UserModel model);
  UserEntity findByUsernameAndPassword(String username, String password);
}
