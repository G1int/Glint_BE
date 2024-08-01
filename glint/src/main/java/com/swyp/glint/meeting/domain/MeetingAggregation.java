package com.swyp.glint.meeting.domain;

import com.swyp.glint.keyword.domain.Drinking;
import com.swyp.glint.keyword.domain.Religion;
import com.swyp.glint.keyword.domain.Smoking;
import com.swyp.glint.meeting.application.dto.response.JoinConditionResponse;
import com.swyp.glint.user.application.dto.UserMeetingResponse;
import com.swyp.glint.user.domain.UserSimpleProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class MeetingAggregation {
    Long id;
    Long leaderUserId;
    String title;
    String description;
    List<UserSimpleProfile> users;
    List<String> locations;
    JoinConditionAggregation maleCondition;
    JoinConditionAggregation femaleCondition;
    Integer peopleCapacity;
    String status;
    List<Long> joinRequestUserIds;


    public MeetingAggregation(
            Meeting meeting,
            List<UserSimpleProfile> users,
            LocationList locationList,
            Map<Long, Drinking> drinkingIdMap,
            Map<Long, Smoking> smokingIdMap,
            Map<Long, Religion> religionIdMap,
            List<Long> joinRequestUserIds
    ) {
        this.id = meeting.getId();
        this.leaderUserId = meeting.getLeaderUserId();
        this.title = meeting.getTitle();
        this.description = meeting.getDescription();
        this.users = users;
        this.locations = locationList.getLocationNames();
        this.maleCondition = JoinConditionAggregation.of(meeting.getMaleCondition(), drinkingIdMap, smokingIdMap, religionIdMap);
        this.femaleCondition = JoinConditionAggregation.of(meeting.getFemaleCondition(), drinkingIdMap, smokingIdMap, religionIdMap);
        this.peopleCapacity = meeting.getPeopleCapacity();
        this.status = meeting.getStatus();
        this.joinRequestUserIds = joinRequestUserIds;
    }


}
