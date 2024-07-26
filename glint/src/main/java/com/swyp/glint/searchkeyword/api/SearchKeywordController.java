package com.swyp.glint.searchkeyword.api;

import com.amazonaws.Response;
import com.swyp.glint.searchkeyword.application.SearchKeywordService;
import com.swyp.glint.searchkeyword.application.dto.SearchKeywordRequest;
import com.swyp.glint.searchkeyword.application.dto.SearchKeywordResponse;
import com.swyp.glint.searchkeyword.application.dto.SearchKeywordResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SearchKeywordController {

    private final SearchKeywordService searchKeywordService;

    @Operation(summary = "검색 키워드 저장", description = "검색 키워드 저장, 미팅 검색때 자동으로 들어갑니다. 따로 api를 호출할 필요는 없습니다!")
    @PostMapping(path = "/search-keywords", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SearchKeywordResponse> createSearchKeyword(@RequestBody SearchKeywordRequest searchKeywordRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(searchKeywordService.saveSearchKeyword(searchKeywordRequest.keyword(), searchKeywordRequest.userId()));
    }


    @Operation(summary = "최근 검색 키워드 조회", description = "최근 검색 키워드 조회, limit 안넣을시 기본 5개")
    @GetMapping(path = "/search-keywords/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SearchKeywordResponses> getRecentSearchKeywords(
            @PathVariable Long userId,
            @RequestParam(required = false) Integer limit) {
        return ResponseEntity.ok(searchKeywordService.getRecentSearchKeywords(userId, limit));
    }

    @Operation(summary = "최근 검색 키워드 삭제", description = "검색 키워드 삭제")
    @DeleteMapping(path = "/search-keywords/{searchKeywordId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeRecentSearchKeywords(@PathVariable Long searchKeywordId) {
            searchKeywordService.removeRecentSearchKeywords(searchKeywordId);
        return ResponseEntity.noContent().build();
    }

}
