package com.swyp.glint.keyword.application;

import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.keyword.domain.University;
import com.swyp.glint.keyword.domain.UniversityCategory;
import com.swyp.glint.keyword.repository.UniversityCategoryRepository;
import com.swyp.glint.keyword.repository.UniversityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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

    public University findByName(String universityName, String universityDepartment) { // 대학명과 학과를 통한 university 반환
        return universityRepository.findByUniversityNameAndUniversityDepartment(universityName, universityDepartment)
                .orElseThrow(() -> new NotFoundEntityException("University not found with name: " + universityName));
    }

    public List<University> getUniversitiesByName(String universityName) { // 대학명을 통한 University 엔티티들 반환
        List<University> universities = universityRepository.findByUniversityName(universityName);
        if(universities.isEmpty()) {
            throw new NotFoundEntityException("University not found with name: " + universityName);
        }
        return universities;
    }

    public List<University> getAllUniversity() { // 대학 전체 조회
        return universityRepository.findAll();
    }

    public String getUniversityNameById(Long universityId) { // university id를 통한 대학명 반환
        return universityRepository.findById(universityId)
                .map(University::getUniversityName)
                .orElseThrow(() -> new NotFoundEntityException("University name not found with id: " + universityId));
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
        List<University> universities = universityRepository.findByUniversityName(universityName);
        if (universities.isEmpty()) {
            throw new NotFoundEntityException("universities not found with universityName: " + universityName);
        }
        University university = universities.stream().findFirst().orElseThrow(() -> new NotFoundEntityException("University not found with university name: " + universityName));
        // 대학명을 통해 찾은 대학들의 리스트 중 하나의 University 엔티티만 추출하여 그 엔티티의 category id를 통해 Category 엔티티 반환
        return universityCategoryRepository.findById(university.getUniversityCategoryId())
                .orElseThrow(() -> new NotFoundEntityException("University category not found for university: " + universityName));
    }

    @Transactional
    public University createNewUniversity(String universityName, String universityDepartment) {
        return universityRepository.findByUniversityNameAndUniversityDepartment(universityName, universityDepartment)
                .orElseGet(() -> {
                    University newUniversity = University.createNewUniversity(universityName, universityDepartment);
                    return universityRepository.save(newUniversity);
                });
    }

    @Transactional
    public University updateUniversityById(Long universityId, String universityName, String universityDepartment) {
        University university = universityRepository.findById(universityId)
                .orElseThrow(() -> new NotFoundEntityException("University not found with university id: " + universityId));
        university.updateUniversity(universityName, universityDepartment);
        return  universityRepository.save(university);
    }

    @Transactional
    public void deleteUniversity(Long universityId) {
        University university = universityRepository.findById(universityId)
                .orElseThrow(() -> new NotFoundEntityException("University not found with university id: " + universityId));
        universityRepository.delete(university);
    }
}
