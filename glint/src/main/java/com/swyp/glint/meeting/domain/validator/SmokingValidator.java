package com.swyp.glint.meeting.domain.validator;

import com.swyp.glint.meeting.domain.ConditionValidator;
import com.swyp.glint.meeting.domain.JoinConditionElement;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserProfile;

public class SmokingValidator implements ConditionValidator {
    private final JoinConditionElement joinConditionElement;
    private final UserDetail userDetail;
    private final UserProfile userProfile;

    public SmokingValidator(JoinConditionElement joinConditionElement, UserDetail userDetail, UserProfile userProfile) {
        this.joinConditionElement = joinConditionElement;
        this.userDetail = userDetail;
        this.userProfile = userProfile;
    }

    @Override
    public boolean validateCondition() {
        if((!(joinConditionElement.getSmoking() == null) && !joinConditionElement.getSmoking().isEmpty()) && userProfile.getSmoking() == null) {
            return false;

        }

        if(joinConditionElement.getSmoking() == null) {
            return false;
        }

        return joinConditionElement.getSmoking().contains(userProfile.getSmoking().getSmokingName());
    }
}
