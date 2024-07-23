package com.swyp.glint.searchkeyword.application.dto;

import com.swyp.glint.searchkeyword.domain.SearchKeyword;

import java.time.format.DateTimeFormatter;

public record SearchKeywordResponse(
        Long id,
        String keyword,
        Long userId,
        String createAt
) {

    public static SearchKeywordResponse from(SearchKeyword searchKeyword) {
        return new SearchKeywordResponse(
                searchKeyword.getId(),
                searchKeyword.getKeyword(),
                searchKeyword.getUserId(),
                searchKeyword.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }
}
