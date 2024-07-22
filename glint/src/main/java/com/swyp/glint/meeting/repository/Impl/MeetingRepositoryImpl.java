package com.swyp.glint.meeting.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swyp.glint.meeting.domain.JoinStatus;
import com.swyp.glint.meeting.domain.Meeting;
import com.swyp.glint.meeting.domain.MeetingInfo;
import com.swyp.glint.meeting.repository.MeetingRepositoryCustom;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
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
public class MeetingRepositoryImpl extends QuerydslRepositorySupport implements MeetingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MeetingRepositoryImpl(EntityManager em) {
        super(Meeting.class);
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MeetingInfo> findAllMeetingInfoByStatus(Long userId, String status) {
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
                        statusEqProgressJoinUserContain(status, userId)
                        )
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
                .limit(getSize(size))
                .fetch();
    }


    private static Integer getSize(Integer size) {
        return Optional.ofNullable(size).orElse(10);
    }

    private static BooleanExpression getLt(Long lastId) {
        return Optional.ofNullable(lastId).map(meeting.id::lt).orElse(null);
    }

    private BooleanExpression statusEqProgressJoinUserContain(String status, Long userId) {
        return status.equals(JoinStatus.ACCEPTED.getName()) ? meeting.joinUserIds.contains(userId) : null;
    }




}
