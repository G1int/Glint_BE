package com.swyp.glint.meeting.domain;

import jakarta.persistence.*;

import java.util.List;

//참가 조건
@Entity
public class MeetingJoinCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<String> selectConditionCategoryNames;

    @Column
    private Long conditionValueId;

}
