package com.swyp.glint.user.application.dto;

import com.swyp.glint.keyword.domain.University;
import com.swyp.glint.keyword.domain.UniversityCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UniversityResponse {

    @Schema(description = "University ID", example = "1")
    Long universityId;
    @Schema(description = "대학명", example = "서울대학교")
    String universityName;
    @Schema(description = "대학 학명", example = "의예과")
    String universityDepartment;

    UniversityCategoryResponse universityCategory;

    public static UniversityResponse from(University university) {
        if(university == null) return null;

        return UniversityResponse.builder()
                .universityId(university.getId())
                .universityName(university.getUniversityName())
                .universityDepartment(university.getUniversityDepartment())
                .universityCategory(UniversityCategoryResponse.from(university.getUniversityCategory()))
                .build();
    }
}
