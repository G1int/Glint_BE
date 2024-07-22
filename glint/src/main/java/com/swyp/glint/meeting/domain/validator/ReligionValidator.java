package com.swyp.glint.meeting.domain.validator;

import com.swyp.glint.keyword.domain.Religion;
import com.swyp.glint.meeting.domain.ConditionValidator;
import com.swyp.glint.meeting.domain.HeightRange;
import com.swyp.glint.meeting.domain.JoinConditionElement;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserProfile;

import java.util.Objects;
import java.util.Optional;

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
        Optional<Religion> religionOptional = Optional.ofNullable(userProfile.getReligion());
        if(Objects.nonNull(joinConditionElement.getReligionIds()) && !joinConditionElement.getReligionIds().isEmpty() && religionOptional.isEmpty()) {
            return false;

        }

        if(religionOptional.isEmpty()) {
            return false;
        }
        Religion religion = religionOptional.get();
        return joinConditionElement.getReligionIds().contains(religion.getId());
    }
}
