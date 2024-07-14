package com.swyp.glint.keyword.repository;

import com.swyp.glint.keyword.domain.Work;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {

    @Query("""
                SELECT w
                FROM Work w
                WHERE :workName LIKE CONCAT('%', w.workName, '%')
            """)
    Optional<Work> findByWorkName(@Param("workName") String workName);

}
