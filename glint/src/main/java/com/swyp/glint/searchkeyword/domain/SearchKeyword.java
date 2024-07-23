package com.swyp.glint.searchkeyword.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private Long id;

    private String keyword;

    private Long userId;

    private LocalDateTime createdAt;

    @Builder(access = AccessLevel.PRIVATE)
    private SearchKeyword(Long id, String keyword, Long userId, LocalDateTime createdAt) {
        this.id = id;
        this.keyword = keyword;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public static SearchKeyword createNew(Long userId,String keyword) {
        return SearchKeyword.builder()
                .keyword(keyword)
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .build();
    }




}
