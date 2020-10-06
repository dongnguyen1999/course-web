package com.ndong.courseweb.repository;

import com.ndong.courseweb.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findOneByUsernameAndPassword(String username, String password);
  UserEntity findOneByUsername(String username);
}
