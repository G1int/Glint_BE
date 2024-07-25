package com.swyp.glint.keyword.application.dto;

import com.swyp.glint.keyword.domain.University;
import com.swyp.glint.user.application.dto.UniversityResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record UniversityListResponse(

        @Schema(description = "대학 리스트")
        List<UniversityResponse> universities

) {
    public static UniversityListResponse from(List<University> universityList) {
        if(universityList == null) return null;
        List<UniversityResponse> universityResponses = universityList.stream()
                .map(university -> UniversityResponse.from(university, university.getUniversityCategory()))
                .toList();
        return new UniversityListResponse(universityResponses);
    }
}
