package com.swyp.glint.keyword.repository;

import com.swyp.glint.keyword.domain.Smoking;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SmokingRepository extends JpaRepository<Smoking, Long> {

    @Query("""
                SELECT s
                FROM Smoking s
                WHERE s.smokingName = :smokingName
            """)
    Optional<Smoking> findBySmokingName(@Param("smokingName") String smokingName);

}
