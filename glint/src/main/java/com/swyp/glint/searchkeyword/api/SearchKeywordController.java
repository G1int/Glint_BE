package com.swyp.glint.searchkeyword.api;

import com.swyp.glint.searchkeyword.application.SearchKeywordService;
import com.swyp.glint.searchkeyword.application.dto.SearchKeywordRequest;
import com.swyp.glint.searchkeyword.application.dto.SearchKeywordResponse;
import com.swyp.glint.searchkeyword.application.dto.SearchKeywordResponses;
import com.swyp.glint.searchkeyword.domain.SearchKeyword;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SearchKeywordController {

    private final SearchKeywordService searchKeywordService;

    @Operation(summary = "검색 키워드 저장", description = "검색 키워드 저장")
    @PostMapping(path = "/search-keywords", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SearchKeywordResponse createSearchKeyword(@RequestBody SearchKeywordRequest searchKeywordRequest) {
        return searchKeywordService.saveSearchKeyword(searchKeywordRequest);
    }


    @Operation(summary = "최근 검색 키워드 조회", description = "최근 검색 키워드 조회")
    @GetMapping(path = "/search-keywords", produces = MediaType.APPLICATION_JSON_VALUE)
    public SearchKeywordResponses getRecentSearchKeywords(@RequestParam Long userId,@RequestParam(required = false) Integer limit) {
        return searchKeywordService.getRecentSearchKeywords(userId, limit);
    }


}
