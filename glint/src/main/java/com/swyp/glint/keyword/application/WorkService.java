package com.swyp.glint.keyword.application;

import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.keyword.domain.Work;
import com.swyp.glint.keyword.domain.WorkCategory;
import com.swyp.glint.keyword.repository.WorkCategoryRepository;
import com.swyp.glint.keyword.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkService {

    private final WorkRepository workRepository;
    private final WorkCategoryRepository workCategoryRepository;

    public Work findById(Long workId) { // work id를 통한 Work 엔티티 반환
        return workRepository.findById(workId)
                .orElseThrow(() -> new NotFoundEntityException("Work not found with id: " + workId));
    }

    public Work findByName(String workName) {
        return workRepository.findByWorkName(workName)
                .orElseThrow(() -> new NotFoundEntityException("Work not found with name: " + workName));
    }

    public Work createNewWork(String workName) { // Work 엔티티 생성 및 저장
        if (workName == null) {
            throw new IllegalArgumentException("Work name must not be null");
        }
        return workRepository.save(Work.createNewWork(workName)); // Save the provided Work entity to the database
    }

    public Work updateWork(String workName) {
        Work work = workRepository.findByWorkName(workName)
                .orElseGet(() -> {
                    return Work.createNewWork(workName);
                });
        work.updateWork(workName);
        return workRepository.save(work); // 저장하는 과정 꼭 필요
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

    public List<WorkCategory> getAllWorkCategory() { // 직업 카테고리 전체 조회
        return workCategoryRepository.findAll();
    }

    public List<WorkCategory> getWorkCategoryByName(String workName) { // 직업명에 카테고리키워드가 포함되어 있다면 해당 카테고리 반환
        return workCategoryRepository.findByWorkNameContainingKeyword(workName)
                .orElseThrow(() -> new NotFoundEntityException("Work Categories not found with name: " + workName));
    }

}
