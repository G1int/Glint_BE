package com.swyp.glint.meeting.repository.Impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swyp.glint.meeting.domain.Meeting;
import com.swyp.glint.meeting.domain.MeetingInfo;
import com.swyp.glint.meeting.repository.MeetingRepositoryCustom;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

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
                .from(joinMeeting)
                .join(meeting).on(meeting.id.eq(joinMeeting.meetingId))
                .join(userDetail).on(meeting.joinUserIds.contains(userDetail.userId))
                .leftJoin(location).on(meeting.locationIds.contains(location.id))
                .where(meeting.status.eq(status),
                        joinMeeting.userId.eq(userId),
                        statusEqProgress(status, userId))
                .orderBy(meeting.id.desc())
                .fetch();
    }

    @Override
    public List<MeetingInfo> findAllProgressMeeting() {
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
                .where(meeting.status.eq("END")).not
                .orderBy(meeting.id.desc())
                .fetch();
    }

    private BooleanExpression statusEqProgress(String status, Long userId) {
        return status.equals("PROGRESS") ? meeting.joinUserIds.contains(userId) : null;
    }


}
