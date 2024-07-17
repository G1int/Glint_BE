package com.swyp.glint.keyword.application;

import com.swyp.glint.keyword.domain.Work;
import com.swyp.glint.keyword.domain.WorkCategory;
import com.swyp.glint.keyword.repository.WorkRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkMappingService {

    private final WorkRepository workRepository;
    private final WorkCategoryService workCategoryService;

    @Transactional
    public void mapAllWorksToCategories() {
        List<Work> works = workRepository.findAll();
        for (Work work : works) {
            Long workCategoryId = workCategoryService.findCategoryByWorkName(work.getWorkName())
                    .map(WorkCategory::getId)
                    .orElse(null);
            work.updateWork(work.getWorkName(), workCategoryId);
            workRepository.save(work);
        }
    }
}
