package com.swyp.glint.meeting.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swyp.glint.keyword.domain.Drinking;
import com.swyp.glint.keyword.domain.Location;
import com.swyp.glint.keyword.domain.Religion;
import com.swyp.glint.keyword.domain.Smoking;
import com.swyp.glint.meeting.application.dto.MeetingSearchCondition;
import com.swyp.glint.meeting.application.dto.response.MeetingInfoCountResponses;
import com.swyp.glint.meeting.domain.*;
import com.swyp.glint.meeting.repository.MeetingRepositoryCustom;
import com.swyp.glint.user.domain.UserSimpleProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.core.types.Projections.list;
import static com.swyp.glint.keyword.domain.QDrinking.drinking;
import static com.swyp.glint.keyword.domain.QLocation.location;
import static com.swyp.glint.keyword.domain.QReligion.religion;
import static com.swyp.glint.keyword.domain.QSmoking.smoking;
import static com.swyp.glint.meeting.domain.QJoinConditionElement.joinConditionElement;
import static com.swyp.glint.meeting.domain.QJoinMeeting.joinMeeting;
import static com.swyp.glint.meeting.domain.QMeeting.meeting;
import static com.swyp.glint.user.domain.QUserDetail.userDetail;
import static com.swyp.glint.user.domain.QUserProfile.userProfile;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class MeetingRepositoryImpl implements MeetingRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<MeetingInfo> findAllMeetingInfoByStatus(Long userId, String status, Long lastMeetingId, Integer limit) {
        return queryFactory
                .select(
                        Projections.constructor(
                                MeetingInfo.class,
                                meeting,
                                list(userDetail),
                                list(location)
                        )
                )
                .from(meeting)
                .join(joinMeeting).on(meeting.id.eq(joinMeeting.meetingId))
                .join(userDetail).on(meeting.joinUserIds.contains(userDetail.userId))
                .leftJoin(location).on(meeting.locationIds.contains(location.id))
                .where(
                        meeting.status.eq(status),
                        joinMeeting.userId.eq(userId),
                        statusEqProgressJoinUserContain(status, userId),
                        getLt(lastMeetingId)
                )
                .limit(getLimit(limit))
                .orderBy(meeting.id.desc())
                .distinct()
                .fetch();
    }

    @Override
    public Optional<MeetingDetail> findMeetingDetail(Long meetingId) {

        Meeting foundMeeting = queryFactory
                .selectFrom(meeting)
                .where(meeting.id.eq(meetingId)).fetchOne();

        List<UserSimpleProfile> userSimpleProfiles = queryFactory
                .select(
                        constructor(
                                UserSimpleProfile.class,
                                userDetail,
                                userProfile
                        ))
                .from(userDetail)
                .leftJoin(userProfile).on(userDetail.userId.eq(userProfile.userId))
                .where(userDetail.userId.in(foundMeeting.getJoinUserIds()))
                .fetch();

        List<Location> locations = queryFactory.select(location).from(location).where(location.id.in(foundMeeting.getLocationIds())).fetch();
        List<Drinking> drinkings = queryFactory.selectFrom(drinking).where(drinking.id.in(foundMeeting.getAllDrinkingIds())).fetch();
        List<Smoking> smokings = queryFactory.selectFrom(smoking).where(smoking.id.in(foundMeeting.getAllSmokingIds())).fetch();
        List<Religion> religions = queryFactory.selectFrom(religion).where(religion.id.in(foundMeeting.getAllReligionIds())).fetch();

        List<Long> joinRequestUserIds = queryFactory.select(joinMeeting.userId).from(joinMeeting).where(joinMeeting.meetingId.eq(meetingId)).fetch();



        return
                Optional.of(
                        MeetingDetail.of(
                        foundMeeting,
                        userSimpleProfiles,
                        new LocationList(locations),
                        drinkings,
                        smokings,
                        religions,
                        joinRequestUserIds
                ))
        ;


    }

    private static BooleanExpression meetingIdEq(Long meetingId) {
        return meeting.id.eq(meetingId);
    }


    @Override
    public List<MeetingInfo> findAllNotFinishMeeting(Long lastId, Integer size) {
        return queryFactory
                .select(
                        Projections.constructor(
                                MeetingInfo.class,
                                meeting,
                                list(userDetail),
                                list(location)
                        )
                )
                .from(meeting)
                .join(userDetail).on(meeting.joinUserIds.contains(userDetail.userId))
                .leftJoin(location).on(meeting.locationIds.contains(location.id))
                .where(
                        meeting.status.ne("END"),
                        getLt(lastId)
                )
                .orderBy(meeting.createdDate.desc())
                .limit(getLimit(size))
                .fetch();
    }


    @Override
    public List<MeetingInfo> searchMeeting(MeetingSearchCondition searchCondition) {
        return queryFactory
                .select(
                        Projections.constructor(
                                MeetingInfo.class,
                                meeting,
                                list(userDetail),
                                list(location)
                        )
                )
                .from(meeting)
                .join(userDetail).on(meeting.joinUserIds.contains(userDetail.userId))
                .leftJoin(location).on(meeting.locationIds.contains(location.id))
                .where(
                        meeting.status.ne(MeetingStatus.END.getName()),
                        getLt(searchCondition.getLastMeetingId()),
                        searchBooleanBuilder(searchCondition.getKeyword())
                )
                .orderBy(meeting.createdDate.desc())
                .groupBy(meeting.id, userDetail.id, location.id)
                .limit(getLimit(searchCondition.getLimit()))
                .fetch();
    }


    @Override
    public MeetingInfoCountResponses searchMeetingWithTotalCount(MeetingSearchCondition searchCondition) {
        int total = queryFactory
                .selectFrom(meeting)
                .join(userDetail).on(meeting.joinUserIds.contains(userDetail.userId))
                .leftJoin(location).on(meeting.locationIds.contains(location.id))
                .where(
                        meeting.status.ne(MeetingStatus.END.getName()),
                        searchBooleanBuilder(searchCondition.getKeyword()),
                        locationIdsIn(searchCondition.getLocationIds())
                        )
                .fetch()
                .size();


        List<MeetingInfo> meetingInfos = queryFactory
                .select(
                        Projections.constructor(
                                MeetingInfo.class,
                                meeting,
                                list(userDetail),
                                list(location)
                        )
                )
                .from(meeting)
                .join(userDetail).on(meeting.joinUserIds.contains(userDetail.userId))
                .leftJoin(location).on(meeting.locationIds.contains(location.id))
                .where(
                        meeting.status.ne(MeetingStatus.END.getName()),
                        getLt(searchCondition.getLastMeetingId()),
                        searchBooleanBuilder(searchCondition.getKeyword()),
                        locationIdsIn(searchCondition.getLocationIds())
                )
                .orderBy(meeting.createdDate.desc())
                .groupBy(meeting.id, userDetail.id, location.id)
                .limit(getLimit(searchCondition.getLimit()))
                .fetch();

        return MeetingInfoCountResponses.from(meetingInfos, total);

    }

    private static BooleanExpression locationIdsIn(List<Long> locationIds) {
        if(locationIds == null || locationIds.isEmpty()) {
            return null;
        }
        return location.id.in(locationIds);
    }


    private BooleanBuilder searchBooleanBuilder(String keyword) {

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (hasText(keyword)) {
            booleanBuilder
                    .or(titleLike(keyword))
                    .or(descriptionLike(keyword))
                    .or(locationCityLike(keyword))
                    .or(locationStateLike(keyword));

        }


        return booleanBuilder;
    }

    private BooleanExpression locationStateLike(String keyword) {
        return hasText(keyword) ? location.state.like("%" + keyword + "%") : null;
    }

    private BooleanExpression locationCityLike(String keyword) {
        return hasText(keyword) ? location.city.like("%" + keyword + "%") : null;
    }

    private  BooleanExpression descriptionLike(String keyword) {
        return hasText(keyword) ? meeting.description.like("%" + keyword + "%" ) : null;
    }

    private BooleanExpression titleLike(String keyword) {
        return hasText(keyword) ? meeting.title.like("%" + keyword + "%" ) : null;
    }


    private Integer getLimit(Integer size) {
        return Optional.ofNullable(size).orElse(10);
    }

    private BooleanExpression getLt(Long lastId) {
        return Optional.ofNullable(lastId).map(meeting.id::lt).orElse(null);
    }


    private BooleanExpression statusEqProgressJoinUserContain(String status, Long userId) {
        if(status.equals(JoinStatus.ACCEPTED.getName())) {
            return meeting.joinUserIds.contains(userId);
        }

        return null;
    }

    private BooleanExpression joinMeetingIdEqAndStatusEq(Long userId, JoinStatus joinStatus) {
        return joinMeeting.userId.eq(userId).and(joinMeeting.status.eq(joinStatus.getName()));
    }


}
