package com.swyp.glint.keyword.application;

import com.swyp.glint.core.common.exception.NotFoundEntityException;
import com.swyp.glint.keyword.domain.UniversityCategory;
import com.swyp.glint.keyword.repository.UniversityCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityMappingService {

    private final UniversityCategoryRepository universityCategoryRepository;

    private static final List<String> PRESTIGIOUS_UNIVERSITIES = List.of("서울대학교", "연세대학교", "고려대학교", "서강대학교", "성균관대학교",
            "한양대학교", "중앙대학교", "경희대학교", "한국외국어대학교", "서울시립대학교", "이화여자대학교", "한국과학기술원", "포항공과대학교", "울산과학기술원", "대구경북과학기술원",
            "광주과학기술원", "경찰대학교", "육군사관학교", "해군사관학교", "공군사관학교", "간호사관학교",
            "의학전문대학원", "치의학전문대학원", "법학전문대학원");
    private static final List<String> PRESTIGIOUS_DEPARTMENTS = List.of("의예과", "의학과", "치의학과", "한의예과", "한의학과", "약학과");

    private static final List<String> SEOUL_UNIVERSITIES = List.of("서울대학교", "연세대학교", "고려대학교", "서강대학교", "성균관대학교", "한양대학교",
            "중앙대학교", "경희대학교", "한국외국어대학교", "서울시립대학교", "건국대학교", "동국대학교", "홍익대학교", "국민대학교", "숭실대학교", "세종대학교",
            "단국대학교", "광운대학교", "명지대학교", "상명대학교", "가톨릭대학교", "이화여자대학교", "숙명여자대학교", "성신여자대학교", "덕성여자대학교", "서울여자대학교");

    private static final List<String> LOCAL_NATIONAL_UNIVERSITIES = List.of("경북대학교", "부산대학교", "경상국립대학교", "전남대학교", "전북대학교",
            "충남대학교", "충북대학교", "강원대학교", "제주대학교");



    public UniversityCategory determineUniversityCategory(String universityName, String universityDepartment) {
        if (PRESTIGIOUS_UNIVERSITIES.contains(universityName) || PRESTIGIOUS_DEPARTMENTS.contains(universityDepartment)) {
            return universityCategoryRepository.findByName("명문대")
                    .orElseThrow(() -> new NotFoundEntityException("University category '명문대' not found"));
        }
        if (SEOUL_UNIVERSITIES.contains(universityName)) {
            return universityCategoryRepository.findByName("인서울")
                    .orElseThrow(() -> new NotFoundEntityException("University category '인서울' not found"));
        }
        if (LOCAL_NATIONAL_UNIVERSITIES.contains(universityName)) {
            return universityCategoryRepository.findByName("지거국")
                    .orElseThrow(() -> new NotFoundEntityException("University category '지거국' not found"));
        }

        return null;
    }

}
