package com.swyp.glint.user.application.dto;

import com.swyp.glint.common.exception.InvalidValueException;
import com.swyp.glint.keyword.domain.UniversityCategory;
import com.swyp.glint.keyword.domain.WorkCategory;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {

    @Schema(description = "User ID", example = "1", nullable = false)
    Long userId;
    UserDetailResponse userDetail;
    UserProfileResponse userProfile;

    public UserInfoResponse(UserProfile userProfile, UserDetail userDetail, WorkCategory workCategory, UniversityCategory universityCategory) {
                if (userDetail == null) {
                    throw new InvalidValueException("UserDetail cannot be null");
                }
                this.userId = userDetail.getUserId();
                this.userDetail = UserDetailResponse.from(userDetail);
                this.userProfile = UserProfileResponse.from(userProfile, workCategory, universityCategory);
    }
}
