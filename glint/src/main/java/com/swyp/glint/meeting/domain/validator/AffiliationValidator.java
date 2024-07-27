package com.swyp.glint.meeting.domain.validator;

import com.swyp.glint.keyword.domain.University;
import com.swyp.glint.keyword.domain.Work;
import com.swyp.glint.meeting.domain.ConditionValidator;
import com.swyp.glint.meeting.domain.JoinConditionElement;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserProfile;

import java.util.Objects;


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
        University university = userProfile.getUniversity();
        Work work = userProfile.getWork();

        String universityName = university.getUniversityName();
        String workName = work.getWorkName();

        if(Objects.isNull(universityName) && Objects.isNull(workName)) {
            return false;
        }

        if(Objects.nonNull(universityName) && joinConditionElement.getAffiliation().contains(universityName) ) {
            return true;
        }

        if(Objects.nonNull(workName) && !joinConditionElement.getAffiliation().contains(workName)) {
            return true;
        }

        return false;
    }
}
