package com.swyp.glint.searchkeyword.application.dto;

import com.swyp.glint.searchkeyword.domain.SearchKeyword;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public record SearchKeywordResponse(
        Long id,
        String keyword,
        Long userId,
        String createAt
) {

    public static SearchKeywordResponse from(SearchKeyword searchKeyword) {
        return new SearchKeywordResponse(
                searchKeyword.getSearchKeywordId(),
                searchKeyword.getKeyword(),
                searchKeyword.getUserId(),
                Optional.ofNullable(searchKeyword.getCreatedAt())
                        .map(localDateTime -> localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .orElse(null)
        );
    }
}
