package com.swyp.glint.meeting.domain.validator;

import com.swyp.glint.meeting.domain.ConditionValidator;
import com.swyp.glint.meeting.domain.HeightRange;
import com.swyp.glint.meeting.domain.JoinConditionElement;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserProfile;

public class ReligionValidator implements ConditionValidator {
    private final JoinConditionElement joinConditionElement;
    private final UserDetail userDetail;
    private final UserProfile userProfile;

    public ReligionValidator(JoinConditionElement joinConditionElement, UserDetail userDetail, UserProfile userProfile) {
        this.joinConditionElement = joinConditionElement;
        this.userDetail = userDetail;
        this.userProfile = userProfile;
    }

    @Override
    public boolean validateCondition() {
        if((joinConditionElement.getReligion() == null && !joinConditionElement.getReligion().isEmpty()) && userProfile.getReligion() == null) {
            return false;

        }

        return joinConditionElement.getReligion().contains(userProfile.getReligion().getReligionName());
    }
}
