package com.swyp.glint.keyword.application;

import com.swyp.glint.core.common.exception.NotFoundEntityException;
import com.swyp.glint.keyword.application.dto.UniversityListResponse;
import com.swyp.glint.keyword.domain.University;
import com.swyp.glint.keyword.domain.UniversityCategory;
import com.swyp.glint.keyword.repository.UniversityCategoryRepository;
import com.swyp.glint.keyword.repository.UniversityRepository;
import com.swyp.glint.user.application.dto.UniversityResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;
    private final UniversityCategoryRepository universityCategoryRepository;
    private final UniversityMappingService universityMappingService;

    public UniversityResponse findById(Long universityId) { // university id를 통한 University 응답 반환
        University university = universityRepository.findById(universityId)
                .orElseThrow(() -> new NotFoundEntityException("University not found with id: " + universityId));
        return UniversityResponse.from(university);
    }

    public UniversityResponse findByName(String universityName, String universityDepartment) { // 대학교+학과명을 통한 University 응답 반환
        University university = universityRepository.findByUniversityNameAndUniversityDepartment(universityName, universityDepartment)
                .orElseThrow(() -> new NotFoundEntityException("University not found with name: " + universityName + " department: " + universityDepartment));
        return UniversityResponse.from(university);
    }

    public University getEntityByName(String universityName, String universityDepartment) { // 대학교+학과명을 통한 University 응답 반환
        return universityRepository.findByUniversityNameAndUniversityDepartment(universityName, universityDepartment)
                .orElse(null);
    }

    public UniversityListResponse getUniversitiesByName(String universityName) { // 대학명을 통한 University 응답 리스트 반환
        return UniversityListResponse.from(universityRepository.findByUniversityName(universityName));
    }

    public UniversityListResponse getAllUniversity() { // 대학 전체 조회
        return UniversityListResponse.from(universityRepository.findAll());
    }

    public List<UniversityCategory> getAllUniversityCategory() { // 대학 카테고리 전체 조회
        return universityCategoryRepository.findAll();
    }

    public Optional<UniversityCategory> getUniversityCategoryByUniversity(University university) {
        if (university == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(university.getUniversityCategory());
    }

    @Transactional
    public UniversityResponse createNewUniversity(String universityName, String universityDepartment) { // 우선 먼저 DB에서 찾고 없으면 생성
        UniversityCategory universityCategory = universityMappingService.determineUniversityCategory(universityName, universityDepartment);
        University university = universityRepository.findByUniversityNameAndUniversityDepartment(universityName, universityDepartment)
                .orElseGet(() -> { return universityRepository.save(University.createNewUniversity(universityName, universityDepartment, universityCategory)); });

        return UniversityResponse.from(university);
    }

    @Transactional
    public UniversityResponse updateUniversityById(Long universityId, String universityName, String universityDepartment) {
        University university = universityRepository.findById(universityId)
                .orElseThrow(() -> new NotFoundEntityException("University not found with university id: " + universityId));
        UniversityCategory universityCategory = universityMappingService.determineUniversityCategory(universityName, universityDepartment);
        university.updateUniversity(universityName, universityDepartment, universityCategory);
        return  UniversityResponse.from(universityRepository.save(university));
    }

    @Transactional
    public void deleteUniversity(Long universityId) {
        University university = universityRepository.findById(universityId)
                .orElseThrow(() -> new NotFoundEntityException("University not found with university id: " + universityId));
        universityRepository.delete(university);
    }


}
