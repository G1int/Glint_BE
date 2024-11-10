package com.swyp.glint.keyword.repository;

import com.swyp.glint.keyword.domain.University;
import com.swyp.glint.keyword.domain.UniversityCategory;
import com.swyp.glint.user.domain.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UniversityCategoryRepository extends JpaRepository<UniversityCategory, Long> {

    @Query("""
                SELECT uc
                FROM UniversityCategory uc
                WHERE uc.universityCategoryName = :universityCategoryName
            """)
    Optional<UniversityCategory> findByName(@Param("universityCategoryName") String universityCategoryName);
}
