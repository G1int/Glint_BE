package com.swyp.glint.user.api;

import com.swyp.glint.keyword.application.DrinkingService;
import com.swyp.glint.keyword.domain.Drinking;
import com.swyp.glint.user.application.UserProfileService;
import com.swyp.glint.user.application.dto.UserProfileRequest;
import com.swyp.glint.user.application.dto.UserProfileResponse;
import com.swyp.glint.user.domain.UserProfile;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping(path = "/{userId}/profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create User Profile", description = "새로운 유저 프로필 생성")
    public ResponseEntity<UserProfileResponse> createUserProfile(
            @PathVariable Long userId, @Valid @RequestBody UserProfileRequest userProfileRequest) {
        return ResponseEntity.ok(userProfileService.createUserProfile(userId, userProfileRequest));
    }

    @Operation(summary = "Get User Profile by User Id", description = "user id를 통한 유저 프로필 조회")
    @GetMapping(path = "/{userId}/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userProfileService.getUserProfileById(userId));
    }

    @Operation(summary = "Update User Profile", description = "유저 프로필 수정 (직업의 경우, 사용자 직접 입력이기 때문에 DB에 존재하지 않는 경우 새로 생성해서 반환해드리지만, 나머지 대학교, 위치, 종교, 흡연, 음주의 경우에는 보내주시는 값이 DB의 값과 정확히 일치해야 합니다.)")
    @PutMapping(path = "/{userId}/profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfileResponse> updateUserProfile(@PathVariable Long userId, @Valid @RequestBody UserProfileRequest userProfileRequest) {
        return ResponseEntity.ok(userProfileService.updateUserProfile(userId, userProfileRequest));
    }


    @GetMapping(path = "/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List all User's Profile", description = "모든 유저 프로필 조회")
    public ResponseEntity<List<UserProfileResponse>> getAllUserProfile() {
        return ResponseEntity.ok(userProfileService.getAllUserProfile());
    }

}
