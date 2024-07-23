package com.swyp.glint.searchkeyword.application;

import com.swyp.glint.searchkeyword.application.dto.SearchKeywordRequest;
import com.swyp.glint.searchkeyword.application.dto.SearchKeywordResponse;
import com.swyp.glint.searchkeyword.application.dto.SearchKeywordResponses;
import com.swyp.glint.searchkeyword.repository.SearchKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchKeywordService {

    private final SearchKeywordRepository searchKeywordRepository;

    public SearchKeywordResponse saveSearchKeyword(SearchKeywordRequest searchKeywordRequest) {
        return SearchKeywordResponse.from(searchKeywordRepository.save(searchKeywordRequest.toEntity()));
    }


    public SearchKeywordResponses getRecentSearchKeywords(Long userId, Integer limit) {
        return SearchKeywordResponses.from(searchKeywordRepository.findAllByUserId(userId, Optional.ofNullable(limit).orElse(10)));
    }

}
