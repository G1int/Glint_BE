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
    @Column(name = "religion_id")
    @ElementCollection
    private List<Long> religionIds;

    // 흡연
    @Column(name = "smoking_id")
    @ElementCollection
    private List<Long> smokingIds;

    // 음주
    @Column(name = "drinking_id")
    @ElementCollection
    private List<Long> drinkingIds;



    @Builder(access = AccessLevel.PRIVATE)
    private JoinConditionElement(List<String> selectConditionKeywords, List<String> affiliation, AgeRange ageRange, HeightRange heightRange, List<Long> religionIds, List<Long> smokingIds, List<Long> drinkingIds) {
        this.selectConditions = selectConditionKeywords;
        this.affiliation = affiliation;
        this.ageRange = ageRange;
        this.heightRange = heightRange;
        this.religionIds = religionIds;
        this.smokingIds = smokingIds;
        this.drinkingIds = drinkingIds;
    }

    public static JoinConditionElement createNew(List<String> selectConditionKeywords, List<String> affiliation, AgeRange ageRange, HeightRange heightRange, List<Long> religionIds, List<Long> smokingIds, List<Long> drinkingIds){
        return JoinConditionElement.builder()
                .selectConditionKeywords(selectConditionKeywords)
                .affiliation(affiliation)
                .ageRange(ageRange)
                .heightRange(heightRange)
                .religionIds(religionIds)
                .smokingIds(smokingIds)
                .drinkingIds(drinkingIds)
                .build();
    }



}
