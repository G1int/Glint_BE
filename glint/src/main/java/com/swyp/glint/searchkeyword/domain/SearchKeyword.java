package com.swyp.glint.searchkeyword.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchKeyword {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "search_keyword_id")
    private Long searchKeywordId;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "user_id", nullable = true)
    private Long userId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "archived")
    private Boolean archived;

    @Builder(access = AccessLevel.PRIVATE)
    private SearchKeyword(Long searchKeywordId, String keyword, Long userId, LocalDateTime createdAt) {
        this.searchKeywordId = searchKeywordId;
        this.keyword = keyword;
        this.userId = userId;
        this.createdAt = createdAt;
        this.archived = false;
    }

    public static SearchKeyword createNew(Long userId,String keyword) {
        return SearchKeyword.builder()
                .keyword(keyword)
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void archive() {
        this.archived = true;
    }




}
