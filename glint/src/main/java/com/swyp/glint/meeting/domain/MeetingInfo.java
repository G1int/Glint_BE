package com.swyp.glint.meeting.domain;

import com.swyp.glint.keyword.domain.Location;
import com.swyp.glint.user.domain.UserDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class MeetingInfo {
    private Integer peopleCapacity;
    private List<String> locationKeywords;
    private AgeRange manAgeRange;
    private AgeRange womanAgeRange;
    private String title;
    private String status;
    private String meetingImage;
    private Integer maleCount;
    private Integer femaleCount;
    // REF
    //  인원 (남, 여)
    //  나이범위 (남, 여)
    //  마감여부
    //  제목
    //  미팅 이미지.
    //  지역


    public MeetingInfo(
            Meeting meeting,
            List<UserDetail> userDetails,
            List<Location> location
    ) {
        this.peopleCapacity = meeting.getPeopleCapacity();
        this.locationKeywords = location.stream().map(l -> l.getState() + " " + l.getCity()).toList();
        this.manAgeRange = Optional.ofNullable(meeting.getManCondition()).map(JoinConditionElement::getAgeRange).orElse(null);
        this.womanAgeRange =  Optional.ofNullable(meeting.getWomanCondition()).map(JoinConditionElement::getAgeRange).orElse(null);
        this.title = meeting.getTitle();
        this.status = meeting.getStatus();
        this.meetingImage = meeting.getMeetingImage();
        this.maleCount = userDetails.stream().filter(UserDetail::isMale).toList().size();
        this.femaleCount = userDetails.stream().filter(UserDetail::isFemale).toList().size();

    }
}
