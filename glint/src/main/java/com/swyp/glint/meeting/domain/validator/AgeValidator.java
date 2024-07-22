package com.swyp.glint.meeting.domain.validator;

import com.swyp.glint.meeting.domain.AgeRange;
import com.swyp.glint.meeting.domain.ConditionValidator;
import com.swyp.glint.meeting.domain.JoinConditionElement;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserProfile;

public class AgeValidator implements ConditionValidator {

    private final JoinConditionElement joinConditionElement;
    private final UserDetail userDetail;
    private final UserProfile userProfile;

    public AgeValidator(JoinConditionElement joinConditionElement, UserDetail userDetail, UserProfile userProfile) {
        this.joinConditionElement = joinConditionElement;
        this.userDetail = userDetail;
        this.userProfile = userProfile;
    }

    @Override
    public boolean validateCondition() {
        AgeRange ageRange = joinConditionElement.getAgeRange();
        return userDetail.isAgeIn(ageRange.getMinAge(), ageRange.getMaxAge());
    }
}
