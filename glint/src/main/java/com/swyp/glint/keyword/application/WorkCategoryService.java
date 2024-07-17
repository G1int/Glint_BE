package com.swyp.glint.keyword.application;

import com.swyp.glint.keyword.domain.WorkCategory;
import com.swyp.glint.keyword.repository.WorkCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkCategoryService {

    private final WorkCategoryRepository workCategoryRepository;

    public Optional<WorkCategory> findCategoryByWorkName(String workName) {
        List<WorkCategory> workCategories = workCategoryRepository.findByWorkNameContainingKeyword(workName);
        if(!workCategories.isEmpty()) {
            return Optional.of(workCategories.get(0));
        }
        return Optional.empty();
    }

    public Long findCategoryIdByWorkName(String workName) {
        return workCategoryRepository.findByWorkNameContainingKeyword(workName)
                .stream().findFirst()
                .map(WorkCategory::getId)
                .orElse(null);
    }

    public List<WorkCategory> getAllWorkCategory() { // 직업 카테고리 전체 조회
        return workCategoryRepository.findAll();
    }

}
