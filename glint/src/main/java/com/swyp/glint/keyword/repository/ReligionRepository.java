package com.swyp.glint.keyword.repository;

import com.swyp.glint.keyword.domain.Religion;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReligionRepository extends JpaRepository<Religion, Long> {

    @Query("""
                SELECT r
                FROM Religion r
                WHERE r.religionName = :religionName
            """)
    Optional<Religion> findByReligionName(@Param("religionName") String religionName);

}
