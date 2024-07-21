package com.swyp.glint.meeting.domain;

import com.swyp.glint.keyword.domain.Drinking;
import com.swyp.glint.keyword.domain.Religion;
import com.swyp.glint.keyword.domain.Smoking;

import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
public class JoinConditionAggregation {

    // 적용선택 조건
    private final List<String> selectConditions;

    private final List<String> affiliation;

    private final AgeRange ageRange;

    private final HeightRange heightRange;

    private final List<Religion> religionConditions;

    private final List<Smoking> smokingConditions;

    private final List<Drinking> drinkingConditions;



    private JoinConditionAggregation(JoinConditionElement joinConditionElement, Map<Long, Drinking> drinkingIdMap, Map<Long, Smoking> smokingIdMap, Map<Long, Religion> religionIdMap) {
        this.selectConditions = joinConditionElement.getSelectConditions();
        this.affiliation = joinConditionElement.getAffiliation();
        this.ageRange = joinConditionElement.getAgeRange();
        this.heightRange = joinConditionElement.getHeightRange();
        this.religionConditions = getReligions(joinConditionElement.getReligionIds(), religionIdMap);
        this.smokingConditions = getSmokings(joinConditionElement.getSmokingIds(), smokingIdMap);
        this.drinkingConditions = getDrinkings(joinConditionElement.getDrinkingIds(), drinkingIdMap);
    }

    public static JoinConditionAggregation of(JoinConditionElement joinConditionElement, Map<Long, Drinking> drinkingIdMap, Map<Long, Smoking> smokingIdMap, Map<Long, Religion> religionIdMap) {
        if(Objects.isNull(joinConditionElement)) {
            return null;
        }
        return new JoinConditionAggregation(joinConditionElement, drinkingIdMap, smokingIdMap, religionIdMap);
    }

    private List<Drinking> getDrinkings(List<Long> drinkingIds, Map<Long, Drinking> drinkingIdMap) {
        return drinkingIds.stream().map(drinkingIdMap::get).toList();
    }


    private List<Smoking> getSmokings(List<Long> smokingIds, Map<Long, Smoking> smokingIdMap) {
        return smokingIds.stream().map(smokingIdMap::get).toList();
    }

    private List<Religion> getReligions(List<Long> religionIds, Map<Long, Religion> religionIdMap) {
        return religionIds.stream().map(religionIdMap::get).toList();
    }


}
