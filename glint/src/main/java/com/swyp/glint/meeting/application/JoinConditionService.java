package com.swyp.glint.meeting.application;

import com.swyp.glint.meeting.application.dto.request.JoinConditionRequest;
import com.swyp.glint.meeting.repository.JoinConditionRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinConditionService {

    private final JoinConditionRespository joinConditionRespository;


//    private JoinCondition createJoinCondition(JoinConditionRequest joinConditionRequest) {
//        JoinCondition joinCondition = joinConditionRequest.toEntity();
//
//        joinConditionRespository.save(joinCondition);
//
//        return JoinConditionResponse.from(joinCondition);
//    }


}
