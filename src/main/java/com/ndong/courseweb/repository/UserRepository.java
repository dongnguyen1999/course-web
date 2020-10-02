package com.ndong.courseweb.repository;

import com.ndong.courseweb.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  List<UserEntity> findByUsernameAndPassword(String username, String password);
}
