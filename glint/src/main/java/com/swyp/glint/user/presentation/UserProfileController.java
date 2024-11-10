package com.swyp.glint.user.presentation;

import com.swyp.glint.user.application.dto.UserInfoResponse;
import com.swyp.glint.user.application.dto.UserProfileRequest;
import com.swyp.glint.user.application.dto.UserProfileResponse;
import com.swyp.glint.user.application.usecase.GetUserProfileUseCase;
import com.swyp.glint.user.application.usecase.UpdateUserProfileUseCase;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final UpdateUserProfileUseCase updateUserProfileUseCase;
    private final GetUserProfileUseCase getUserProfileUseCase;

    @Operation(summary = "Update or Create User Profile", description = "유저 프로필 수정 및 생성")
    @PutMapping(path = "/{userId}/profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoResponse> updateUserProfile(@PathVariable Long userId, @Valid @RequestBody UserProfileRequest userProfileRequest) {
        return ResponseEntity.ok(updateUserProfileUseCase.updateUserProfile(userId, userProfileRequest));
    }

    @Operation(summary = "Get User Profile", description = "User Id를 통한 User 프로필 조회")
    @GetMapping(path = "/{userId}/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfileResponse> getUserDetail(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(getUserProfileUseCase.getUserProfileBy(userId));
    }

}
