package com.swyp.glint.meeting.domain.validator;

import com.swyp.glint.meeting.domain.ConditionValidator;
import com.swyp.glint.meeting.domain.JoinConditionElement;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserProfile;

public class AffiliationValidator implements ConditionValidator {

    private final JoinConditionElement joinConditionElement;
    private final UserDetail userDetail;
    private final UserProfile userProfile;

    public AffiliationValidator(JoinConditionElement joinConditionElement, UserDetail userDetail, UserProfile userProfile) {
        this.joinConditionElement = joinConditionElement;
        this.userDetail = userDetail;
        this.userProfile = userProfile;
    }

    @Override
    public boolean validateCondition() {
        String universityName = userProfile.getUniversity().getUniversityName();
        String workName = userProfile.getWork().getWorkName();

        if(!joinConditionElement.getAffiliation().contains(universityName) && !joinConditionElement.getAffiliation().contains(workName)) {
//            throw new InvalidValueException("Invalid affiliation");
            return false;
        }

        return true;
    }
}
