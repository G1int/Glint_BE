package com.swyp.glint.meeting.domain;

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
public class Meeting {

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

    //동성 조건
    @Embedded
//    @JoinColumn(name = "our_condition")
    @Column(name = "our_condition")
    @AttributeOverrides({
            @AttributeOverride(name = "selectConditions", column = @Column(name = "our_select_conditions")),
            @AttributeOverride(name = "affiliation", column = @Column(name = "our_affiliation")),
            @AttributeOverride(name = "ageRange.maxAge", column = @Column(name = "our_age_max_age")),
            @AttributeOverride(name = "ageRange.minAge", column = @Column(name = "our_age_min_age")),
            @AttributeOverride(name = "heightRange.maxHeight", column = @Column(name = "our_height_max_height")),
            @AttributeOverride(name = "heightRange.minHeight", column = @Column(name = "our_height_min_height")),
            @AttributeOverride(name = "religion", column = @Column(name = "our_religion")),
            @AttributeOverride(name = "smoking", column = @Column(name = "our_smoking")),
            @AttributeOverride(name = "drinking", column = @Column(name = "our_drinking")),
    })
    private JoinConditionElement ourCondition;
    // 상대 조건
    @Embedded
//    @JoinColumn(name = "other_condition")
    @AttributeOverrides({
            @AttributeOverride(name = "selectConditions", column = @Column(name = "other_select_conditions")),
            @AttributeOverride(name = "affiliation", column = @Column(name = "other_affiliation")),
            @AttributeOverride(name = "ageRange.maxAge", column = @Column(name = "other_age_max_age")),
            @AttributeOverride(name = "ageRange.minAge", column = @Column(name = "other_age_min_age")),
            @AttributeOverride(name = "heightRange.maxHeight", column = @Column(name = "other_height_max_height")),
            @AttributeOverride(name = "heightRange.minHeight", column = @Column(name = "other_height_min_height")),
            @AttributeOverride(name = "religion", column = @Column(name = "other_religion")),
            @AttributeOverride(name = "smoking", column = @Column(name = "other_smoking")),
            @AttributeOverride(name = "drinking", column = @Column(name = "other_drinking")),
    })
    private JoinConditionElement otherCondition;

    // 인원수
    private Integer peopleCapacity;

    // 미팅 상태
    private String status;

    @Builder(access = AccessLevel.PRIVATE)
    private Meeting(Long id, Long leaderUserId, String title, String description, List<Long> joinUserIds, List<Long> locationIds, JoinConditionElement ourCondition, JoinConditionElement otherCondition, Integer peopleCapacity, String status) {
        this.id = id;
        this.leaderUserId = leaderUserId;
        this.title = title;
        this.description = description;
        this.joinUserIds = joinUserIds;
        this.locationIds = locationIds;
        this.ourCondition = ourCondition;
        this.otherCondition = otherCondition;
        this.peopleCapacity = peopleCapacity;
        this.status = status;
    }

    public static Meeting createNewMeeting(String title, String description, Long leaderUserId, String location, JoinConditionElement ourCondition, JoinConditionElement otherCondition, Integer peopleCapacity) {
        return Meeting.builder()
                .title(title)
                .description(description)
                .leaderUserId(leaderUserId)
                .joinUserIds(List.of(leaderUserId))
                .locationIds(new ArrayList<>())
                .ourCondition(ourCondition)
                .otherCondition(otherCondition)
                .peopleCapacity(peopleCapacity)
                .status("WAIT")
                .build();

    }


    public boolean isJoinUser(Long sendUserId) {
        return joinUserIds.contains(sendUserId);
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
}
