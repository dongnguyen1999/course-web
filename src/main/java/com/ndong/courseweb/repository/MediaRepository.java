package com.ndong.courseweb.repository;

import com.ndong.courseweb.entity.MediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<MediaEntity, Long> {
  MediaEntity findOneByCode(String code);
}
