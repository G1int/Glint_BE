package com.swyp.glint.meeting.api;

import com.swyp.glint.meeting.application.ConditionCategoryService;
import com.swyp.glint.meeting.application.dto.request.ConditionCategoryResponse;
import com.swyp.glint.meeting.application.dto.response.ConditionCategoryResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConditionCategoryController {

    private final ConditionCategoryService conditionCategoryService;

    @GetMapping(value = "/condition-category", produces = MediaType.APPLICATION_JSON_VALUE) // (
    public ConditionCategoryResponses getAllConditionCategory() {
        return conditionCategoryService.getAllConditionCategory();
    }


}
