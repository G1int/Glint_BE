package com.swyp.glint.user.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swyp.glint.user.application.dto.UserProfileWithDetailResponse;
import com.swyp.glint.user.domain.UserProfile;
import com.swyp.glint.user.repository.UserProfileCustom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.querydsl.core.types.Projections.list;
import static com.swyp.glint.keyword.domain.QDrinking.drinking;
import static com.swyp.glint.keyword.domain.QLocation.location;
import static com.swyp.glint.keyword.domain.QReligion.religion;
import static com.swyp.glint.keyword.domain.QSmoking.smoking;
import static com.swyp.glint.keyword.domain.QUniversity.university;
import static com.swyp.glint.keyword.domain.QUniversityCategory.universityCategory;
import static com.swyp.glint.keyword.domain.QWork.work;
import static com.swyp.glint.keyword.domain.QWorkCategory.workCategory;
import static com.swyp.glint.user.domain.QUserDetail.userDetail;
import static com.swyp.glint.user.domain.QUserProfile.userProfile;

@Repository
@RequiredArgsConstructor
public class UserProfileRepositoryImpl implements UserProfileCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Optional<UserProfileWithDetailResponse> findUserInfoBy(Long userId) {
        return Optional.ofNullable(
                queryFactory
                .select(
                        Projections.constructor(UserProfileWithDetailResponse.class,
                                    userProfile,
                                    userDetail
                        )
                ).distinct()
                .from(userProfile)
                .leftJoin(userProfile.work, work).fetchJoin()
                .leftJoin(userProfile.university, university).fetchJoin()
                .leftJoin(userProfile.location, location).fetchJoin()
                .leftJoin(userProfile.religion, religion).fetchJoin()
                .leftJoin(userProfile.smoking, smoking).fetchJoin()
                .leftJoin(userProfile.drinking, drinking).fetchJoin()
                .join(userProfile.hashtags)
                .leftJoin(userDetail).on(userDetail.userId.eq(userProfile.userId))
                .leftJoin(workCategory).on(workCategory.id.eq(userProfile.work.workCategoryId))
                .leftJoin(universityCategory).on(universityCategory.id.eq(userProfile.university.universityCategory.id))
                .where(userProfile.userId.eq(userId))
                .fetchOne()
        );
    }

}
