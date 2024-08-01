package com.swyp.glint.meeting.domain;

import com.swyp.glint.meeting.domain.validator.*;
import com.swyp.glint.user.domain.Gender;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserProfile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserMeetingValidator {

    private final UserProfile userProfile;
    private final UserDetail userDetail;
    private final Meeting meeting;
    private final UserDetail leaderUserDetail;
    private final JoinConditionElement matchCondition;
    private final Map<String, ConditionValidator> selectedConditionValueMap;


    public UserMeetingValidator(UserProfile userProfile, UserDetail userDetail, UserDetail leaderUserDetail, Meeting meeting) {
        this.userProfile = userProfile;
        this.userDetail = userDetail;
        this.meeting = meeting;
        this.leaderUserDetail = leaderUserDetail;
        this.matchCondition = getMatchCondition();
        this.selectedConditionValueMap = getSelectedConditionValueMap();
    }

    public JoinConditionElement getMatchCondition() {
        // todo 일치 개수 체크
        if(userDetail.sameGender(Gender.MALE.name())) {
            return meeting.getMaleCondition();
        }
        return meeting.getFemaleCondition();
    }

    public HashMap<String, ConditionValidator> getSelectedConditionValueMap() {
        HashMap<String, ConditionValidator> conditionValueMap = new HashMap<>();


        for(String condition : matchCondition.getSelectConditions()) {
            if (condition.equals("AFFILIATION")) {
                conditionValueMap.put(condition, new AffiliationValidator(matchCondition, userDetail, userProfile));
            }
            if (condition.equals("AGE")) {
                conditionValueMap.put(condition, new AgeValidator(matchCondition, userDetail, userProfile));
            }

            if (condition.equals("HEIGHT")) {
                conditionValueMap.put(condition, new HeightValidator(matchCondition, userDetail, userProfile));
            }

            if (condition.equals("RELIGION")) {
                conditionValueMap.put(condition, new ReligionValidator(matchCondition, userDetail, userProfile));
            }

            if (condition.equals("SMOKING")) {
                conditionValueMap.put(condition, new SmokingValidator(matchCondition, userDetail, userProfile));
            }

            if (condition.equals("DRINKING")) {
                conditionValueMap.put(condition, new DrinkingValidator(matchCondition, userDetail, userProfile));
            }
        }

        return conditionValueMap;
    }

    public boolean validate() {
        if(Objects.isNull(userProfile)) {
            return false;
        }

        List<String> selectConditions = matchCondition.getSelectConditions();
        for(String condition : selectConditions) {
            if(!selectedConditionValueMap.get(condition).validateCondition()) {
                return false;
            }
        }
        return true;
    }

}
