package com.swyp.glint.keyword.application;

import com.swyp.glint.core.common.exception.NotFoundEntityException;
import com.swyp.glint.keyword.application.dto.WorkListResponse;
import com.swyp.glint.keyword.domain.Work;
import com.swyp.glint.keyword.domain.WorkCategory;
import com.swyp.glint.keyword.repository.WorkRepository;
import com.swyp.glint.user.application.dto.WorkResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkService {

    private final WorkRepository workRepository;
    private final WorkCategoryService workCategoryService;
    private final WorkMappingService workMappingService;

    public WorkResponse findById(Long workId) { // work id를 통한 Work 엔티티 반환
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new NotFoundEntityException("Work not found with id: " + workId));
        return WorkResponse.from(work);
    }

    public WorkResponse findByName(String workName) {
        Work work = getEntityByName(workName);
        return WorkResponse.from(work);
    }

    public Work getEntityByName(String workName) {
        return workRepository.findByWorkName(workName)
                .orElseThrow(() -> new NotFoundEntityException("Work not found with name: " + workName));
    }

    @Transactional
    public Work createNewWork(String workName) { // 이미 해당하는 workName을 가진 work가 있다면, 해당 객체를 반환하고, 없다면 work객체를 새로 생성하고 저장한 후 반환.
        Work work = workRepository.findByWorkName(workName).orElseGet(() -> Work.createNewWork(workName));
        WorkCategory workCategory = workCategoryService.findCategoryByWorkName(workName);
        work.updateWork(workName, workCategory);
        return workRepository.save(work);
    }

    public WorkResponse createNewWorkReturnDTO(String workName) {
        return WorkResponse.from(createNewWork(workName));
    }

    @Transactional
    public Work updateWorkById(Long workId, String workName) { // workId를 통한 직업명 업데이트
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new NotFoundEntityException("Work not found with work id: " + workId));

        WorkCategory workCategory = workCategoryService.findCategoryByWorkName(workName);

        work.updateWork(workName, workCategory);
        return workRepository.save(work);
    }

    @Transactional
    public void deleteWork(Long workId) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new NotFoundEntityException("Work not found with id: " + workId));
        workRepository.delete(work);
    }

    public WorkListResponse getAllWork() { // 직업 전체 조회
        return WorkListResponse.from(workRepository.findAll());
    }

    public void mapAllWorksToCategories() { // 초기 매핑 작업 (현재 DB에 있는 모든 work들의 카테고리를 매핑)
        workMappingService.mapAllWorksToCategories();
    }

}
