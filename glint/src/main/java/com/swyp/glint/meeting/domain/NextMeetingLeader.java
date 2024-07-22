package com.swyp.glint.meeting.domain;

import com.swyp.glint.meeting.exception.NotFoundNextMeetingLeader;
import com.swyp.glint.user.domain.Gender;
import com.swyp.glint.user.domain.UserDetail;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class NextMeetingLeader {
    private final List<JoinMeeting> meetingMemberJoinMeetings;
    private final List<UserDetail> joinMemberUserDetails;
    private final UserDetail leaderUserDetail;
    private final  String leaderGender;
    private final Map<String, List<UserDetail>> userDetailGenderMap;
    private final Map<Long, LocalDateTime> userJoinTimeMap;

    public NextMeetingLeader(List<JoinMeeting> meetingMemberJoinMeetings, List<UserDetail> joinMemberUserDetails, UserDetail leaderUserDetail) {
        this.meetingMemberJoinMeetings = meetingMemberJoinMeetings;
        this.joinMemberUserDetails = joinMemberUserDetails;
        this.leaderUserDetail = leaderUserDetail;
        this.leaderGender = leaderUserDetail.getGender();
        this.userDetailGenderMap = joinMemberUserDetails.stream().collect(Collectors.groupingBy(UserDetail::getGender));
        this.userJoinTimeMap = meetingMemberJoinMeetings.stream().collect(Collectors.toMap(JoinMeeting::getUserId, JoinMeeting::getJoinDateTime));
    }

    public Long getNextLeaderUserId() {
        Gender nextLeaderGender = getNextLeaderGender();
        List<UserDetail> userDetails = userDetailGenderMap.get(nextLeaderGender.name());

        Optional<UserDetail> latestJoinUserDetailOptional = userDetails.stream().min((o1, o2) -> {
            LocalDateTime o1JoinTime = userJoinTimeMap.get(o1.getUserId());
            LocalDateTime o2JoinTime = userJoinTimeMap.get(o2.getUserId());
            return o1JoinTime.compareTo(o2JoinTime);
        });

        UserDetail userDetail = latestJoinUserDetailOptional.orElseThrow(() -> new NotFoundNextMeetingLeader("Not found next leader user "));
        return userDetail.getUserId();
    }

    private Gender getNextLeaderGender() {
        //동성이 없음. 이성에서 뽑기
        if(userDetailGenderMap.get(leaderUserDetail.getGender()).isEmpty()) {
            return Gender.getOtherGender(Gender.valueOf(leaderUserDetail.getGender()));
        }
        // 동성이 존재
        return Gender.valueOf(leaderUserDetail.getGender());

    }
}