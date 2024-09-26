package com.swyp.glint.user.infra.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swyp.glint.user.application.dto.UserInfoResponse;
import com.swyp.glint.user.infra.UserCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.swyp.glint.keyword.domain.QDrinking.drinking;
import static com.swyp.glint.keyword.domain.QLocation.location;
import static com.swyp.glint.keyword.domain.QReligion.religion;
import static com.swyp.glint.keyword.domain.QSmoking.smoking;
import static com.swyp.glint.keyword.domain.QUniversity.university;
import static com.swyp.glint.keyword.domain.QUniversityCategory.universityCategory;
import static com.swyp.glint.keyword.domain.QWork.work;
import static com.swyp.glint.keyword.domain.QWorkCategory.workCategory;
import static com.swyp.glint.user.domain.QUser.user;
import static com.swyp.glint.user.domain.QUserDetail.userDetail;
import static com.swyp.glint.user.domain.QUserProfile.userProfile;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<UserInfoResponse> findUserInfoBy(Long userId) {
        return Optional.ofNullable(
                queryFactory
                .select(
                        Projections.constructor(UserInfoResponse.class,
                                    userProfile,
                                    userDetail,
                                    workCategory,
                                    universityCategory
                        )
                ).distinct()
                .from(user)
                .leftJoin(userDetail).on(userDetail.userId.eq(user.id))
                .leftJoin(userProfile).on(userProfile.userId.eq(user.id))
                .leftJoin(userProfile.work, work).fetchJoin()
                .leftJoin(userProfile.work.workCategory, workCategory).fetchJoin()
                .leftJoin(userProfile.university, university).fetchJoin()
                .leftJoin(userProfile.university.universityCategory, universityCategory).fetchJoin()
                .leftJoin(userProfile.location, location).fetchJoin()
                .leftJoin(userProfile.religion, religion).fetchJoin()
                .leftJoin(userProfile.smoking, smoking).fetchJoin()
                .leftJoin(userProfile.drinking, drinking).fetchJoin()
                .leftJoin(userProfile.hashtags)
                .leftJoin(userDetail).on(userDetail.userId.eq(userProfile.userId))
                .where(user.id.eq(userId))
                .fetchOne()
        );
    }

}
