package com.swyp.glint.user.application.dto;

import com.swyp.glint.core.common.exception.InvalidValueException;
import com.swyp.glint.keyword.domain.UniversityCategory;
import com.swyp.glint.keyword.domain.WorkCategory;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserInfo;
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

    public static UserInfoResponse from(UserInfo userInfo) {
        if(userInfo == null) return null;
        return UserInfoResponse.builder()
                .userId(userInfo.getUserId())
                .userDetail(UserDetailResponse.from(userInfo.getUserDetail()))
                .userProfile(UserProfileResponse.from(userInfo.getUserProfile()))
                .build();
    }


}
