package com.ndong.courseweb.repository;

import com.ndong.courseweb.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findOneByUsernameAndPassword(String username, String password);
  UserEntity findOneByUsername(String username);
}
