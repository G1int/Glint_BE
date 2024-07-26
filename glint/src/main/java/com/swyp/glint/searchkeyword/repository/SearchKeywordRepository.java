package com.swyp.glint.searchkeyword.repository;

import com.swyp.glint.searchkeyword.domain.SearchKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, Long>, SearchKeywordRepositoryCustom {

    @Query("""
        SELECT sk
        FROM SearchKeyword sk
        WHERE sk.userId = :userId
        AND sk.keyword = :keyword
        AND sk.archived = false        
        """)
    List<SearchKeyword> findByKeyword(String keyword, Long userId);

    @Query("""
        SELECT sk
        FROM SearchKeyword sk
        WHERE sk.userId = :userId
        AND sk.searchKeywordId = :searchKeywordId
        AND sk.archived = false        
        """)
    Optional<SearchKeyword> findByUserIdAndSearchKeywordId(Long userId, Long searchKeywordId);
}
