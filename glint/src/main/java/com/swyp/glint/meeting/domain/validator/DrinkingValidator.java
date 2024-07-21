package com.swyp.glint.meeting.domain.validator;

import com.swyp.glint.keyword.domain.Drinking;
import com.swyp.glint.meeting.domain.ConditionValidator;
import com.swyp.glint.meeting.domain.JoinConditionElement;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserProfile;

import java.util.Objects;
import java.util.Optional;

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
        Optional<Drinking> drinkingOptional = Optional.ofNullable(userProfile.getDrinking());
        if((Objects.nonNull(joinConditionElement.getDrinkingIds()) && !joinConditionElement.getDrinkingIds().isEmpty()) && drinkingOptional.isEmpty()) {
            return false;

        }
        if(drinkingOptional.isEmpty()) {
            return false;
        }
        Drinking drinking = drinkingOptional.get();
        return joinConditionElement.getDrinkingIds().contains(drinking.getId());
    }
}
