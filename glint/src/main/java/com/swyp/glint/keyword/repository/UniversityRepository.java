package com.swyp.glint.keyword.repository;

import com.swyp.glint.keyword.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {

    @Query("""
                SELECT uni
                FROM University uni
                WHERE uni.universityName = :universityName
            """)
    List<University> findByUniversityName(@Param("universityName") String universityName);

    @Query("""
                SELECT DISTINCT uni.universityName
                FROM University uni
            """)
    List<String> findAllDistinctUniversityNames();

    @Query("""
                SELECT uni
                FROM University uni
                WHERE uni.universityName = :universityName AND uni.universityDepartment = :universityDepartment
            """)
    Optional<University> findByUniversityNameAndUniversityDepartment(@Param("universityName") String universityName, @Param("universityDepartment") String universityDepartment);

}
