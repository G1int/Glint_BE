package com.swyp.glint.searchkeyword.repository;

import com.swyp.glint.searchkeyword.domain.SearchKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, Long> {

    @Query("""
        SELECT sk
        FROM SearchKeyword sk
        WHERE sk.userId = :userId
        ORDER BY sk.createdAt DESC
        limit :limit
""")
    List<SearchKeyword> findAllByUserId(Long userId, Integer limit);
}
