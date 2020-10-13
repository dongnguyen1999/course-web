package com.ndong.courseweb.repository;

import com.ndong.courseweb.entity.MediaTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaTypeRepository extends JpaRepository<MediaTypeEntity, Long> {
  MediaTypeEntity findOneByCode(String code);
}
