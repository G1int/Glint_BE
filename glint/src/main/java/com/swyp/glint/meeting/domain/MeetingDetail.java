package com.swyp.glint.meeting.domain;

import com.swyp.glint.keyword.domain.Drinking;
import com.swyp.glint.keyword.domain.Religion;
import com.swyp.glint.keyword.domain.Smoking;
import com.swyp.glint.user.domain.UserSimpleProfile;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter

public class MeetingDetail {
    Long id;
    Long leaderUserId;
    String title;
    String description;
    List<UserSimpleProfile> users;
    LocationList locations;
    MeetingJoinCondition maleCondition;
    MeetingJoinCondition femaleCondition;
    Integer peopleCapacity;
    String status;
    List<Long> joinRequestUserIds;


    public MeetingDetail(
            Meeting meeting,
            List<UserSimpleProfile> users,
            LocationList locationList,
            List<Drinking> drinkings,
            List<Smoking> smokings,
            List<Religion> religions,
            List<Long> joinRequestUserIds
    ) {
        this.id = meeting.getId();
        this.leaderUserId = meeting.getLeaderUserId();
        this.title = meeting.getTitle();
        this.description = meeting.getDescription();
        this.users = users;
        this.locations = locationList;
        this.maleCondition = MeetingJoinCondition.of(meeting.getMaleCondition(), getDringkingIdMap(drinkings), getSmokingIdMap(smokings), getReligionIdMap(religions));
        this.femaleCondition = MeetingJoinCondition.of(meeting.getFemaleCondition(), getDringkingIdMap(drinkings), getSmokingIdMap(smokings), getReligionIdMap(religions));
        this.peopleCapacity = meeting.getPeopleCapacity();
        this.status = meeting.getStatus();
        this.joinRequestUserIds = joinRequestUserIds;
    }


    public static MeetingDetail of(
            Meeting meeting,
            List<UserSimpleProfile> users,
            LocationList locationList,
            List<Drinking> drinkings,
            List<Smoking> smokings,
            List<Religion> religions,
            List<Long> joinRequestUserIds
    ) {
        return new MeetingDetail(meeting, users, locationList, drinkings, smokings, religions, joinRequestUserIds);
    }


    private Map<Long,Drinking> getDringkingIdMap(List<Drinking> drinkings) {
        return drinkings.stream().collect(Collectors.toMap(Drinking::getId, drinking -> drinking));
    }

    private Map<Long, Smoking> getSmokingIdMap(List<Smoking> smokings) {
        return smokings.stream().collect(Collectors.toMap(Smoking::getId, smoking -> smoking));
    }

    private Map<Long, Religion> getReligionIdMap(List<Religion> religions) {
        return religions.stream().collect(Collectors.toMap(Religion::getId, religion -> religion));
    }

    public List<String> getLocationNames() {
        return locations.getLocationNames();
    }


}
