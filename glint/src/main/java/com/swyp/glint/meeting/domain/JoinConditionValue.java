package com.swyp.glint.meeting.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinConditionValue {

    private Long id;

    @Column
    private String conditionCategoryName;

    @Column
    private String value;

    // range (~으로 split), value
    @Column
    private String type;


    private JoinConditionValue(String conditionCategoryName, String value, String type) {
        this.conditionCategoryName = conditionCategoryName;
        this.value = value;
        this.type = type;
    }

    public static JoinConditionValue createNew(String conditionCategoryName, String value, String type) {
        return new JoinConditionValue(conditionCategoryName, value, type);
    }
}
