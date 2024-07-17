package com.swyp.glint.keyword.application;

import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.keyword.domain.Work;
import com.swyp.glint.keyword.domain.WorkCategory;
import com.swyp.glint.keyword.repository.WorkCategoryRepository;
import com.swyp.glint.keyword.repository.WorkRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkService {

    private final WorkRepository workRepository;

    private final WorkCategoryService workCategoryService;
    private final WorkMappingService workMappingService;

    public Work findById(Long workId) { // work id를 통한 Work 엔티티 반환
        return workRepository.findById(workId)
                .orElseThrow(() -> new NotFoundEntityException("Work not found with id: " + workId));
    }

    public Work findByName(String workName) {
        return workRepository.findByWorkName(workName)
                .orElseThrow(() -> new NotFoundEntityException("Work not found with name: " + workName));
    }

    @Transactional
    public Work createNewWork(String workName) { // 이미 해당하는 workName을 가진 work가 있다면, 해당 객체를 반환하고, 없다면 work객체를 새로 생성하고 저장한 후 반환.
        Work work = workRepository.findByWorkName(workName)
                .orElseGet(() -> Work.createNewWork(workName));

        Long workCategoryId = workCategoryService.findCategoryIdByWorkName(workName);
        work.updateWork(workName, workCategoryId);
        return workRepository.save(work);
    }

    @Transactional
    public Work updateWorkById(Long workId, String workName) { // workId를 통한 직업명 업데이트
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new NotFoundEntityException("Work not found with work id: " + workId));

        Long workCategoryId = workCategoryService.findCategoryByWorkName(workName)
                .map(WorkCategory::getId)
                .orElse(null);

        work.updateWork(workName, workCategoryId);
        return workRepository.save(work);
    }

    @Transactional
    public void deleteWork(Long workId) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new NotFoundEntityException("Work not found with id: " + workId));
        workRepository.delete(work);
    }

    public List<Work> getAllWork() { // 직업 전체 조회
        return workRepository.findAll();
    }

    public String getWorkNameById(Long workId) { // work id를 통한 직업명 반환
        return workRepository.findById(workId)
                .map(Work::getWorkName)
                .orElseThrow(() -> new NotFoundEntityException("Work name not found with id: " + workId));
    }

    public Long getWorkIdByName(String workName) { // 직업명을 통한 work id 반환
        return workRepository.findByWorkName(workName)
                .map(Work::getId)
                .orElseThrow(() -> new NotFoundEntityException("Work id not found with name: " + workName));
    }

    public void mapAllWorksToCategories() {
        workMappingService.mapAllWorksToCategories();
    }

}
