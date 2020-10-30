package com.ndong.courseweb.service.impl;

import com.ndong.courseweb.constant.UserConstant;
import com.ndong.courseweb.dto.CourseDTO;
import com.ndong.courseweb.dto.MediaDTO;
import com.ndong.courseweb.dto.UserDTO;
import com.ndong.courseweb.entity.CourseEntity;
import com.ndong.courseweb.entity.PurchaseDetailEntity;
import com.ndong.courseweb.entity.UserEntity;
import com.ndong.courseweb.repository.CourseRepository;
import com.ndong.courseweb.repository.UserRepository;
import com.ndong.courseweb.service.ICourseService;
import com.ndong.courseweb.service.IMediaService;
import com.ndong.courseweb.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private IMediaService mediaService;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private CourseRepository courseRepository;


  @Override
  public UserDTO tryRegisterAccount(UserDTO model) {
    try {
      UserEntity newUser = modelMapper.map(model, UserEntity.class);
      newUser.setStatusCode(UserConstant.NORMAL_USER);
      newUser.setCoin(0d);
      newUser = userRepository.save(newUser);
      MediaDTO mediaDTO = mediaService.saveAvatar(model.getAvatarFile(), newUser);
      if (mediaDTO != null) {
        newUser.setAvatar(mediaDTO.getCode());
        newUser = userRepository.save(newUser);
      }
      return modelMapper.map(newUser, UserDTO.class);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
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

  @Override
  public UserDTO findPermissionOnCourse(String username, String courseCode) {
    String role = null;
    UserEntity user = userRepository.findOneByUsername(username);
    CourseEntity course = courseRepository.findOneByCode(courseCode);
    if (course.getUser().getUsername().equals(username)) role = UserConstant.ROLE_AUTHOR;
    else {
      Set<PurchaseDetailEntity> purchaseDetailEntitySet = user.getPurchaseDetailEntitySet();
      List<Long> purchasedCourseIds = purchaseDetailEntitySet.stream().
          map(purchaseDetailEntity -> purchaseDetailEntity.getId().getCourseId()).
          collect(Collectors.toList());
      if (purchasedCourseIds.contains(course.getId())) role = UserConstant.ROLE_OWNER;
    }
    UserDTO dto = modelMapper.map(user, UserDTO.class);
    dto.setRole(role);
    return dto;
  }


}
