package com.ndong.courseweb.repository;

import java.util.List;

import com.ndong.courseweb.dto.query_result.NbUserPerCourseIdDTO;
import com.ndong.courseweb.entity.PurchaseDetailEntity;
import com.ndong.courseweb.entity.composite_id.PurchaseDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetailEntity, PurchaseDetailId> {

  @Query("SELECT " +
      "new com.ndong.courseweb.dto.query_result.NbUserPerCourseIdDTO(pd.course.id, count(pd.user.id))" +
      "FROM PurchaseDetailEntity pd GROUP BY pd.course.id ORDER BY count(pd.user.id) DESC"
  )
  List<NbUserPerCourseIdDTO> countNbUserPerCourseId();

  @Query("select count(pd.user.id) from PurchaseDetailEntity pd where pd.course.id = ?1")
  Long countByCourseId(Long courseId);

}
