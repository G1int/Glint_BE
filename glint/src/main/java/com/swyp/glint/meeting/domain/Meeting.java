package com.swyp.glint.meeting.domain;

import com.swyp.glint.core.common.baseentity.BaseTimeEntity;
import com.swyp.glint.meeting.exception.NumberOfPeopleException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Meeting extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long id;

    // 미팅 방장 userId
    @Column
    private Long leaderUserId;

    //미팅 방 제목
    @Column
    private String title;

    //미팅 방 내용
    @Column
    private String description;

    //참가된 유저 Id
    @ElementCollection
    @Column(name = "join_user_id")
    private List<Long> joinUserIds = new ArrayList<>();

    @ElementCollection
    @Column(name = "location_id")
    private List<Long> locationIds;

    @Embedded
    @Column(name = "male_condition")
    @AttributeOverrides({
            @AttributeOverride(name = "selectConditions", column = @Column(name = "man_select_conditions")),
            @AttributeOverride(name = "affiliation", column = @Column(name = "man_affiliation")),
            @AttributeOverride(name = "ageRange.maxAge", column = @Column(name = "man_age_max_age")),
            @AttributeOverride(name = "ageRange.minAge", column = @Column(name = "man_age_min_age")),
            @AttributeOverride(name = "heightRange.maxHeight", column = @Column(name = "man_height_max_height")),
            @AttributeOverride(name = "heightRange.minHeight", column = @Column(name = "man_height_min_height")),
            @AttributeOverride(name = "religionIds", column = @Column(name = "man_religion_id")),
            @AttributeOverride(name = "smokingIds", column = @Column(name = "man_smoking_id")),
            @AttributeOverride(name = "drinkingIds", column = @Column(name = "man_drinking_id")),
    })
    private JoinConditionElement maleCondition;

    @Embedded
    @Column(name = "female_condition")
    @AttributeOverrides({
            @AttributeOverride(name = "selectConditions", column = @Column(name = "woman_select_conditions")),
            @AttributeOverride(name = "affiliation", column = @Column(name = "woman_affiliation")),
            @AttributeOverride(name = "ageRange.maxAge", column = @Column(name = "woman_age_max_age")),
            @AttributeOverride(name = "ageRange.minAge", column = @Column(name = "woman_age_min_age")),
            @AttributeOverride(name = "heightRange.maxHeight", column = @Column(name = "woman_height_max_height")),
            @AttributeOverride(name = "heightRange.minHeight", column = @Column(name = "woman_height_min_height")),
            @AttributeOverride(name = "religionIds", column = @Column(name = "woman_religion_id")),
            @AttributeOverride(name = "smokingIds", column = @Column(name = "woman_smoking_id")),
            @AttributeOverride(name = "drinkingIds", column = @Column(name = "woman_drinking_id")),
    })
    private JoinConditionElement femaleCondition;

    private Integer peopleCapacity;

    private String status;

    private String meetingImage;

    @Builder(access = AccessLevel.PRIVATE)
    private Meeting(Long id, Long leaderUserId, String title, String description, List<Long> joinUserIds, List<Long> locationIds, JoinConditionElement maleCondition, JoinConditionElement femaleCondition, Integer peopleCapacity, String status) {
        this.id = id;
        this.leaderUserId = leaderUserId;
        this.title = title;
        this.description = description;
        this.joinUserIds = joinUserIds;
        this.locationIds = locationIds;
        this.maleCondition = maleCondition;
        this.femaleCondition = femaleCondition;
        this.peopleCapacity = peopleCapacity;
        this.status = status;
    }

    public static Meeting createNewMeeting(String title, String description, Long leaderUserId, List<Long> locationIds, JoinConditionElement maleCondition, JoinConditionElement femaleCondition, Integer peopleCapacity) {
        return Meeting.builder()
                .title(title)
                .description(description)
                .leaderUserId(leaderUserId)
                .joinUserIds(List.of(leaderUserId))
                .locationIds(locationIds)
                .maleCondition(maleCondition)
                .femaleCondition(femaleCondition)
                .peopleCapacity(peopleCapacity)
                .status(MeetingStatus.WAITING.getName())
                .build();

    }


    public boolean isJoinUser(Long userId) {
        return joinUserIds.contains(userId);
    }

    public boolean isJoinUser(List<Long> sendUserIds) {
        for (Long sendUserId : sendUserIds) {
            if (joinUserIds.contains(sendUserId)) {
                return true;
            }
        }
        return false;
    }

    public List<Long> isJoinUsers(List<Long> userIds) {
        List<Long> invalidUser = new ArrayList<>();

        userIds.forEach(userId -> {
            if (!joinUserIds.contains(userId)) {
                invalidUser.add(userId);
            }
        });
        return invalidUser;
    }

    public void addUser(Long userId) {
        if(joinUserIds.size() >= getTotalPeoPleCapacity(this.peopleCapacity)) {
            throw new NumberOfPeopleException("인원수 초과");
        }
        joinUserIds.add(userId);
    }

    public boolean isFull() {
        if(joinUserIds.size() == getTotalPeoPleCapacity(this.peopleCapacity)) {
            this.status = MeetingStatus.PROGRESS.getName();
            return true;
        }
        return false;
    }

    public void outUser(Long userId) {
        joinUserIds.remove(userId);

        if(isEmpty()) {
           archive();
        }

        if(isProgress()) {
            this.status = MeetingStatus.WAITING.getName();
        }
    }

    private boolean isProgress() {
        return status.equals(MeetingStatus.PROGRESS.getName());
    }

    public boolean isLeader(Long userId) {
        return leaderUserId.equals(userId) && isJoinUser(userId);
    }

    public void changeLeader(Long nextLeaderUserId) {
        this.leaderUserId = nextLeaderUserId;
    }

    public boolean isEmpty() {
        return joinUserIds.isEmpty();
    }

    public void archive() {
        this.status = MeetingStatus.ARCHIVED.getName();
    }

    public void out(Long userId) {
        joinUserIds.removeIf(id -> id.equals(userId));
    }

    public boolean isLeaderAndAlone(Long userId) {
        return isLeader(userId) && isAlone();
    }

    public boolean isAlone() {
        return joinUserIds.size() == 1;
    }

    public boolean isJoinedUser() {
        int joinUserCount = joinUserIds.size();
        return joinUserCount != 1 && joinUserCount > 0;
    }

    public boolean isUpdatable() {
        return this.status.equals(MeetingStatus.WAITING.getName());
    }

    public boolean isUnableUpdatable() {
        return !this.status.equals(MeetingStatus.WAITING.getName());
    }
    
    public void update(Meeting meeting) {
        updatePeopleCapacity(meeting.peopleCapacity);
        this.title = meeting.title;
        this.description = meeting.description;
        this.locationIds = meeting.locationIds;
        this.maleCondition = meeting.maleCondition;
        this.femaleCondition = meeting.femaleCondition;
    }

    private void updatePeopleCapacity(Integer peopleCapacity) {
        if(joinUserIds.size() > getTotalPeoPleCapacity(peopleCapacity)) {
            throw new NumberOfPeopleException("인원수 초과");
        }
        this.peopleCapacity = peopleCapacity;
    }

    private int getTotalPeoPleCapacity(Integer peopleCapacity) {
        return peopleCapacity * 2;
    }
}
