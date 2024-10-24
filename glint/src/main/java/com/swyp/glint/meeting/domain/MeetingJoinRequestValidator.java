package com.swyp.glint.meeting.domain;

import com.swyp.glint.user.domain.UserDetail;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MeetingJoinRequestValidator {

    private final List<UserDetail> joinUserDetail;
    private final Meeting meeting;
    private final UserDetail userDetail;
    private final Map<String, List<UserDetail>> userGenderMap;

    public MeetingJoinRequestValidator(List<UserDetail> joinUserDetail, Meeting meeting, UserDetail joinRequestUserDetail) {
        this.joinUserDetail = joinUserDetail;
        this.meeting = meeting;
        this.userDetail = joinRequestUserDetail;
        this.userGenderMap = joinUserDetail.stream().collect(Collectors.groupingBy(UserDetail::getGender));
    }

    public boolean isValidJoinUser() {
        List<UserDetail> userGenderDetails = Optional.ofNullable(userGenderMap.get(userDetail.getGender())).orElse(List.of());

        return userGenderDetails.size() < meeting.getPeopleCapacity();
    }


}
