package com.swyp.glint.user.application.dto;

import com.swyp.glint.keyword.domain.UniversityCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UniversityCategoryResponse {

    @Schema(description = "University Category ID", example = "1")
    Long universityCategoryId;
    @Schema(description = "universityCategoryName", example = "명문대")
    String universityCategoryName;


    public static UniversityCategoryResponse from(UniversityCategory universityCategory) {
        if(universityCategory == null) {
            return null;
        }
        return UniversityCategoryResponse.builder()
                .universityCategoryId(universityCategory.getId())
                .universityCategoryName(universityCategory.getUniversityCategoryName())
                .build();
    }
}
