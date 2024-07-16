package com.swyp.glint.meeting.application;

import com.swyp.glint.meeting.application.dto.request.ConditionCategoryResponse;
import com.swyp.glint.meeting.application.dto.response.ConditionCategoryResponses;
import com.swyp.glint.meeting.domain.ConditionCategory;
import com.swyp.glint.meeting.repository.ConditionCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConditionCategoryService {

    private final ConditionCategoryRepository conditionCategoryRepository;


    public ConditionCategoryResponses getAllConditionCategory() {
         return ConditionCategoryResponses.from(conditionCategoryRepository.findAll());
    }

}
