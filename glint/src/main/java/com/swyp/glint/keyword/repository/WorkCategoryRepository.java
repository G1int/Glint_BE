package com.swyp.glint.keyword.repository;

import com.swyp.glint.keyword.domain.WorkCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkCategoryRepository extends JpaRepository<WorkCategory, Long> {

    // 인수로 받은 workName이 키워드와 일치했을 시 해당하는 WorkCategory 엔티티 List 반환
    @Query("""
                SELECT wc
                FROM WorkCategory wc
                WHERE :workName LIKE CONCAT('%', wc.workCategoryKeyword, '%')
            """)
    Optional<List<WorkCategory>> findByWorkNameContainingKeyword(@Param("workName") String workName);

    // 인수로 받은 workName과 일치하는 키워드가 존재한다면 true 반환
    @Query("""
                SELECT CASE
                    WHEN COUNT(wc) > 0 THEN true
                    ELSE false
                END
                FROM WorkCategory wc
                WHERE :workName LIKE CONCAT('%', wc.workCategoryKeyword, '%')
            """)
    Optional<Boolean> existsByWorkNameContainingKeyword(@Param("workName") String workName);

}
