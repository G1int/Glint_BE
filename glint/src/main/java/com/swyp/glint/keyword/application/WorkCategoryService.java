package com.swyp.glint.keyword.application;

import com.swyp.glint.keyword.application.dto.WorkCategoryListResponse;
import com.swyp.glint.keyword.domain.University;
import com.swyp.glint.keyword.domain.UniversityCategory;
import com.swyp.glint.keyword.domain.Work;
import com.swyp.glint.keyword.domain.WorkCategory;
import com.swyp.glint.keyword.repository.WorkCategoryRepository;
import com.swyp.glint.user.application.dto.WorkCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkCategoryService {

    private final WorkCategoryRepository workCategoryRepository;

    public WorkCategory findCategoryByWorkName(String workName) {
        return workCategoryRepository.findByWorkNameContainingKeyword(workName)
                .orElse(null);
    }

    public Optional<WorkCategory> getUWorkCategoryByWork(Work work) {
        if (work == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(work.getWorkCategory());
    }

    public WorkCategoryListResponse getAllWorkCategory() { // 직업 카테고리 전체 조회
        return WorkCategoryListResponse.from(workCategoryRepository.findAll());
    }

}
