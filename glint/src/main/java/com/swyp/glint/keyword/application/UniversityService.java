package com.swyp.glint.keyword.application;

import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.keyword.domain.University;
import com.swyp.glint.keyword.domain.UniversityCategory;
import com.swyp.glint.keyword.repository.UniversityCategoryRepository;
import com.swyp.glint.keyword.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;
    private final UniversityCategoryRepository universityCategoryRepository;

    public University findById(Long universityId) { // university id를 통한 University 엔티티 반환
        return universityRepository.findById(universityId)
                .orElseThrow(() -> new NotFoundEntityException("University not found with id: " + universityId));
    }

    public University findByName(String universityName) { // university name을 통한 University 엔티티 반환
        return universityRepository.findByUniversityName(universityName)
                .orElseThrow(() -> new NotFoundEntityException("University not found with name: " + universityName));
    }

    public List<University> getAllUniversity() { // 대학 전체 조회
        return universityRepository.findAll();
    }

    public String getUniversityNameById(Long universityId) { // university id를 통한 대학명 반환
        return universityRepository.findById(universityId)
                .map(University::getUniversityName)
                .orElseThrow(() -> new NotFoundEntityException("University name not found with id: " + universityId));
    }

    public Long getUniversityIdByName(String universityName) { // 대학명을 통한 university id 반환
        return universityRepository.findByUniversityName(universityName)
                .map(University::getId)
                .orElseThrow(() -> new NotFoundEntityException("University id not found with name: " + universityName));
    }


    public List<UniversityCategory> getAllUniversityCategory() { // 대학 카테고리 전체 조회
        return universityCategoryRepository.findAll();
    }

    public UniversityCategory getUniversityCategoryByUniversityId(Long universityId) { // university id를 통한 Category 엔티티 반환
        University university = universityRepository.findById(universityId)
                .orElseThrow(() -> new NotFoundEntityException("University not found with id: " + universityId));
        return universityCategoryRepository.findById(university.getUniversityCategoryId())
                 .orElseThrow(() -> new NotFoundEntityException("University Category not found with id: " + universityId));
    }

    public UniversityCategory getUniversityCategoryByUniversityName(String universityName) { // 대학명을 통한 Category 엔티티 반환
        University university = universityRepository.findByUniversityName(universityName)
                .orElseThrow(() -> new NotFoundEntityException("University not found with name: " + universityName));
        return universityCategoryRepository.findById(university.getUniversityCategoryId())
                .orElseThrow(() -> new NotFoundEntityException("University category not found for university: " + universityName));
    }
}
