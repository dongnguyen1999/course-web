package com.ndong.courseweb.service.impl;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.constant.UserStatusConstant;
import com.ndong.courseweb.entity.UserEntity;
import com.ndong.courseweb.model.UserModel;
import com.ndong.courseweb.repository.UserRepository;
import com.ndong.courseweb.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ModelMapper modelMapper;


  @Override
  public boolean tryRegisterAccount(UserModel model) {
    try {
      UserEntity user = new UserEntity();
      modelMapper.map(model, user);
      user.setStatusCode(UserStatusConstant.NORMAL_USER);
      user.setCoin(0d);
      userRepository.save(user);
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  @Override
  public UserEntity findByUsernameAndPassword(String username, String password) {
    List<UserEntity> users = userRepository.findByUsernameAndPassword(username, password);
    return (users.isEmpty())? null: users.get(0);
  }
}
