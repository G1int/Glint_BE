package com.swyp.glint.meeting.domain.validator;

import com.swyp.glint.keyword.domain.Smoking;
import com.swyp.glint.meeting.domain.ConditionValidator;
import com.swyp.glint.meeting.domain.JoinConditionElement;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserProfile;

import java.util.Objects;
import java.util.Optional;

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
        if((Objects.nonNull(joinConditionElement.getSmokingIds()) && !joinConditionElement.getSmokingIds().isEmpty()) && userProfile.getSmoking() == null) {
            return false;

        }

        if(joinConditionElement.getSmokingIds() == null) {
            return false;
        }
        Smoking userSmoking = userProfile.getSmoking();
        if(Objects.isNull(userSmoking)) {
            return false;
        }

        return joinConditionElement.getSmokingIds().contains(userSmoking.getId());
    }
}
