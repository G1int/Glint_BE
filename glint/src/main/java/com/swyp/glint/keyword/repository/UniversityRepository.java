package com.swyp.glint.keyword.repository;

import com.swyp.glint.keyword.domain.University;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {

    @Query(
            """
                SELECT uni
                FROM University uni
                WHERE uni.universityName = :universityName
            """
    )
    Optional<University> findByUniversityName(@Param("universityName") String universityName);
}
