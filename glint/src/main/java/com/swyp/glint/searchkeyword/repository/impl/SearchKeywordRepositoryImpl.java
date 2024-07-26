package com.swyp.glint.searchkeyword.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swyp.glint.searchkeyword.domain.SearchKeyword;
import com.swyp.glint.searchkeyword.repository.OrderByNull;
import com.swyp.glint.searchkeyword.repository.SearchKeywordRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;

import static com.swyp.glint.searchkeyword.domain.QSearchKeyword.searchKeyword;

@Repository
@RequiredArgsConstructor
public class SearchKeywordRepositoryImpl implements SearchKeywordRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SearchKeyword> findAllByUserId(Long userId, Integer limit) {
        return queryFactory
                .select(searchKeyword)
                .from(searchKeyword)
                .where(
                        searchKeyword.userId.eq(userId),
                        searchKeyword.archived.isFalse()
                )
                .groupBy(searchKeyword.keyword)
                .orderBy(searchKeyword.searchKeywordId.desc())
                .limit(limit)
                .fetch();
    }



}
