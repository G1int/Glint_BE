package com.swyp.glint.searchkeyword.application.dto;

import com.swyp.glint.searchkeyword.domain.SearchKeyword;

import java.util.List;

public record SearchKeywordResponses(
        List<SearchKeywordResponse> searchKeywords
) {

    public static SearchKeywordResponses from(List<SearchKeyword> searchKeywords) {
        return new SearchKeywordResponses(
                searchKeywords.stream().map(SearchKeywordResponse::from).toList()
        );
    }
}
