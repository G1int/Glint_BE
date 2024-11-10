package com.swyp.glint.searchkeyword.repository;

import com.swyp.glint.searchkeyword.domain.SearchKeyword;

import java.util.List;

public interface SearchKeywordRepositoryCustom {

    List<SearchKeyword> findAllByUserId(Long userId, Integer limit);
}
