package com.swyp.glint.meeting.domain.validator;

import com.swyp.glint.meeting.domain.ConditionValidator;
import com.swyp.glint.meeting.domain.JoinConditionElement;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserProfile;

public class DrinkingValidator implements ConditionValidator {
    private final JoinConditionElement joinConditionElement;
    private final UserDetail userDetail;
    private final UserProfile userProfile;

    public DrinkingValidator(JoinConditionElement joinConditionElement, UserDetail userDetail, UserProfile userProfile) {
        this.joinConditionElement = joinConditionElement;
        this.userDetail = userDetail;
        this.userProfile = userProfile;
    }

    @Override
    public boolean validateCondition() {
        if((!(joinConditionElement.getDrinking() == null) && !joinConditionElement.getDrinking().isEmpty()) && userProfile.getDrinking() == null) {
            return false;

        }
        if(joinConditionElement.getDrinking() == null || joinConditionElement.getDrinking().isEmpty()) {
            return true;
        }

        return joinConditionElement.getDrinking().contains(userProfile.getDrinking().getDrinkingName());
    }
}
