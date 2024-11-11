package com.swyp.glint.meeting.business;

import com.swyp.glint.meeting.domain.JoinMeeting;
import com.swyp.glint.meeting.domain.MeetingLeaderSelector;
import com.swyp.glint.user.domain.Gender;
import com.swyp.glint.user.domain.UserDetail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MeetingLeaderSelectorTest {


    @Test
    @DisplayName("다음 리더는 동성중 가장 먼저 참여한 사람이어야 한다.")
    public void getNextLeader() {
        MeetingLeaderSelector meetingLeaderSelector = new MeetingLeaderSelector(
                List.of(
                        JoinMeeting.createByMeetingInit(1L, 1L),
                        JoinMeeting.createByMeetingInit(2L, 1L),
                        JoinMeeting.createByMeetingInit(3L, 1L),
                        JoinMeeting.createByMeetingInit(4L, 1L)
                ),
                List.of(
                        UserDetail.createNewUserDetail(
                                2L,
                                "banana",
                                Gender.MALE.name(),
                                LocalDate.of(1990, 01, 01),
                                180,
                                "profileImage"
                        ),
                        UserDetail.createNewUserDetail(
                                3L,
                                "coffee",
                                Gender.FEMALE.name(),
                                LocalDate.of(1990, 01, 01),
                                150,
                                "profileImage"
                        ),
                        UserDetail.createNewUserDetail(
                                4L,
                                "donut",
                                Gender.FEMALE.name(),
                                LocalDate.of(1990, 01, 01),
                                160,
                                "profileImage"
                        )
                ),
                UserDetail.createNewUserDetail(
                        1L,
                        "nickname",
                        Gender.MALE.name(),
                        LocalDate.of(1990, 01, 01),
                        180,
                        "profileImage"
                )
        );

        assertThat(meetingLeaderSelector.getNextLeaderUserId()).isEqualTo(2L);

    }


    @Test
    @DisplayName("미팅에 동성이 없을 경우 이성중 가장 먼저 참여한 사람이어야 한다.")
    public void getNextLeaderWhenNotExistSameGender() {
        MeetingLeaderSelector meetingLeaderSelector = new MeetingLeaderSelector(
                List.of(
                        JoinMeeting.createByMeetingInit(1L, 1L),
                        JoinMeeting.createByMeetingInit(3L, 1L),
                        JoinMeeting.createByMeetingInit(4L, 1L)
                ),
                List.of(
                        UserDetail.createNewUserDetail(
                                3L,
                                "coffee",
                                Gender.FEMALE.name(),
                                LocalDate.of(1990, 01, 01),
                                150,
                                "profileImage"
                        ),
                        UserDetail.createNewUserDetail(
                                4L,
                                "donut",
                                Gender.FEMALE.name(),
                                LocalDate.of(1990, 01, 01),
                                160,
                                "profileImage"
                        )
                ),
                UserDetail.createNewUserDetail(
                        1L,
                        "nickname",
                        Gender.MALE.name(),
                        LocalDate.of(1990, 01, 01),
                        180,
                        "profileImage"
                )
        );

        System.out.println(meetingLeaderSelector.getNextLeaderUserId());

        assertThat(meetingLeaderSelector.getNextLeaderUserId()).isEqualTo(3L);

    }
}
