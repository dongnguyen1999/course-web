package com.ndong.courseweb.repository;

import com.ndong.courseweb.entity.LessonEntity;
import com.ndong.courseweb.entity.composite_id.LessonId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LessonRepository extends JpaRepository<LessonEntity, LessonId> {
    @Query("SELECT coalesce(max(ls.id.no), 0) FROM LessonEntity ls WHERE ls.id.courseId = ?1")
    Integer findMaxNoByCourseId(Long courseId);
}
