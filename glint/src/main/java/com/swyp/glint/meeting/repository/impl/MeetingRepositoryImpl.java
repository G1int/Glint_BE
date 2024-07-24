package com.swyp.glint.meeting.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swyp.glint.meeting.application.dto.MeetingSearchCondition;
import com.swyp.glint.meeting.domain.JoinStatus;
import com.swyp.glint.meeting.domain.MeetingInfo;
import com.swyp.glint.meeting.domain.MeetingStatus;
import com.swyp.glint.meeting.repository.MeetingRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.Projections.list;
import static com.swyp.glint.keyword.domain.QLocation.location;
import static com.swyp.glint.meeting.domain.QJoinMeeting.joinMeeting;
import static com.swyp.glint.meeting.domain.QMeeting.meeting;
import static com.swyp.glint.user.domain.QUserDetail.userDetail;
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
                        joinMeeting.status.eq(status),
                        statusEqProgressJoinUserContain(status, userId),
                        getLt(lastMeetingId)
                )
                .limit(getLimit(limit))
                .orderBy(meeting.id.desc())
                .fetch();
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
                .limit(getLimit(searchCondition.getLimit()))
                .fetch();
    }


    private BooleanBuilder searchBooleanBuilder(String keyword) {

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (hasText(keyword)) {
            booleanBuilder
                    .or(titleLike(keyword))
                    .or(descriptionLike(keyword));
        }


        return booleanBuilder;
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
        return status.equals(JoinStatus.ACCEPTED.getName()) ? meeting.joinUserIds.contains(userId) : joinMeeting.userId.eq(userId);
    }




}
