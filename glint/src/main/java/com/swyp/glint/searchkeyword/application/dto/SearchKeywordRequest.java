package com.swyp.glint.searchkeyword.application.dto;

import com.swyp.glint.searchkeyword.domain.SearchKeyword;

public record SearchKeywordRequest(
        String keyword,
        Long userId
) {


    public SearchKeyword toEntity() {
        return SearchKeyword.createNew(userId, keyword);
    }
}
