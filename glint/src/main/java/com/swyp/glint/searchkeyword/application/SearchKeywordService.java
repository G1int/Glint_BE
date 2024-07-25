package com.swyp.glint.searchkeyword.application;

import com.swyp.glint.searchkeyword.application.dto.SearchKeywordRequest;
import com.swyp.glint.searchkeyword.application.dto.SearchKeywordResponse;
import com.swyp.glint.searchkeyword.application.dto.SearchKeywordResponses;
import com.swyp.glint.searchkeyword.domain.SearchKeyword;
import com.swyp.glint.searchkeyword.repository.SearchKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchKeywordService {

    private final SearchKeywordRepository searchKeywordRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public SearchKeywordResponse saveSearchKeyword(String keyword, Long userId) {

        return SearchKeywordResponse.from(searchKeywordRepository.save(SearchKeyword.createNew(userId, keyword)));
    }

    public SearchKeywordResponses getRecentSearchKeywords(Long userId, Integer limit) {
        return SearchKeywordResponses.from(searchKeywordRepository.findAllByUserId(userId, Optional.ofNullable(limit).orElse(5)));
    }


}
