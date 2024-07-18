package com.swyp.glint.meeting.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

//참가 조건
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinConditionElement {

    // 적용선택 조건
    @ElementCollection
    @Column(name = "select_condition")
    private List<String> selectConditions;

    // 소속 (회사, 대학교)
    @ElementCollection
    @Column(name = "affiliation")
    private List<String> affiliation;

    @Embedded
    @Column(name = "age_range")
    private AgeRange ageRange;

    @Embedded
    @Column(name = "height_range")
    private HeightRange heightRange;

    //종교
    @ElementCollection
    private List<String> religion;

    // 흡연
    @ElementCollection
    private List<String> smoking;

    // 음주
    @ElementCollection
    private List<String> drinking;



    @Builder(access = AccessLevel.PRIVATE)
    private JoinConditionElement(List<String> selectConditionKeywords, List<String> affiliation, AgeRange ageRange, HeightRange heightRange, List<String> religion, List<String> smoking, List<String> drinking) {
        this.selectConditions = selectConditionKeywords;
        this.affiliation = affiliation;
        this.ageRange = ageRange;
        this.heightRange = heightRange;
        this.religion = religion;
        this.smoking = smoking;
        this.drinking = drinking;
    }

    public static JoinConditionElement createNew(List<String> selectConditionKeywords, List<String> affiliation, AgeRange ageRange, HeightRange heightRange, List<String> religion, List<String> smoking, List<String> drinking){
        return JoinConditionElement.builder()
                .selectConditionKeywords(selectConditionKeywords)
                .affiliation(affiliation)
                .ageRange(ageRange)
                .heightRange(heightRange)
                .religion(religion)
                .smoking(smoking)
                .drinking(drinking)
                .build();
    }



}
