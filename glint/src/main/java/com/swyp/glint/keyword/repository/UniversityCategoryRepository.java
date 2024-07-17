package com.swyp.glint.keyword.repository;

import com.swyp.glint.keyword.domain.UniversityCategory;
import com.swyp.glint.user.domain.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityCategoryRepository extends JpaRepository<UniversityCategory, Long> {
}
