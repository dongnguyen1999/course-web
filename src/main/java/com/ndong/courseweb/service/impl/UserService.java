package com.ndong.courseweb.service.impl;

import com.ndong.courseweb.constant.UserStatusConstant;
import com.ndong.courseweb.dto.UserDTO;
import com.ndong.courseweb.entity.UserEntity;
import com.ndong.courseweb.repository.UserRepository;
import com.ndong.courseweb.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ModelMapper modelMapper;


  @Override
  public boolean tryRegisterAccount(UserDTO model) {
    try {
      UserEntity newUser = modelMapper.map(model, UserEntity.class);
      newUser.setStatusCode(UserStatusConstant.NORMAL_USER);
      newUser.setCoin(0d);
      userRepository.save(newUser);
      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  @Override
  public UserDTO findOneUser(String username, String password) {
    UserEntity user = userRepository.findOneByUsernameAndPassword(username, password);
    return (user != null)? modelMapper.map(user, UserDTO.class): null;
  }

  @Override
  public UserDTO findOneUser(String username) {
    UserEntity user = userRepository.findOneByUsername(username);
    return (user != null)? modelMapper.map(user, UserDTO.class): null;
  }

  @Override
  public UserDTO findOneUser(Long id) {
    UserEntity user = userRepository.findById(id).orElse(null);
    return (user != null)? modelMapper.map(user, UserDTO.class): null;
  }


}
