package com.swyp.glint.meeting.domain;

import com.querydsl.core.annotations.QueryProjection;
import com.swyp.glint.keyword.domain.Location;
import com.swyp.glint.user.domain.UserDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class MeetingInfo {
    private Long meetingId;
    private Integer peopleCapacity;
    private List<String> locationKeywords;
    private AgeRange manAgeRange;
    private AgeRange womanAgeRange;
    private String title;
    private String status;
    private String meetingImage;
    private Integer maleCount;
    private Integer femaleCount;


    public MeetingInfo(
            Meeting meeting,
            List<UserDetail> userDetails,
            List<Location> locations
    ) {
        this.meetingId = meeting.getId();
        this.peopleCapacity = meeting.getPeopleCapacity();
        this.locationKeywords = locations.stream().filter(Objects::nonNull).map(location -> location.getState() + " " + location.getCity()).toList();
        this.manAgeRange = Optional.ofNullable(meeting.getMaleCondition()).map(JoinConditionElement::getAgeRange).orElse(null);
        this.womanAgeRange =  Optional.ofNullable(meeting.getFemaleCondition()).map(JoinConditionElement::getAgeRange).orElse(null);
        this.title = meeting.getTitle();
        this.status = meeting.getStatus();
        this.meetingImage = meeting.getMeetingImage();
        this.maleCount = userDetails.stream().filter(UserDetail::isMale).toList().size();
        this.femaleCount = userDetails.stream().filter(UserDetail::isFemale).toList().size();

    }
}
