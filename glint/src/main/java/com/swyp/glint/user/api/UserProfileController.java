package com.swyp.glint.user.api;

import com.swyp.glint.user.application.UserProfileService;
import com.swyp.glint.user.application.dto.UserInfoResponse;
import com.swyp.glint.user.application.dto.UserProfileRequest;
import com.swyp.glint.user.application.dto.UserProfileWithDetailResponse;
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
    public ResponseEntity<UserInfoResponse> createUserProfile(
            @PathVariable Long userId, @Valid @RequestBody UserProfileRequest userProfileRequest) {
        return ResponseEntity.ok(userProfileService.createUserProfile(userId, userProfileRequest));
    }

    @Operation(summary = "Update User Profile", description = "유저 프로필 수정 (직업의 경우, 사용자 직접 입력이기 때문에 DB에 존재하지 않는 경우 새로 생성해서 반환해드리지만, 나머지 대학교, 위치, 종교, 흡연, 음주의 경우에는 보내주시는 값이 DB의 값과 정확히 일치해야 합니다.)")
    @PutMapping(path = "/{userId}/profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoResponse> updateUserProfile(@PathVariable Long userId, @Valid @RequestBody UserProfileRequest userProfileRequest) {
        return ResponseEntity.ok(userProfileService.updateUserProfile(userId, userProfileRequest));
    }


    // todo 어디서 사용하는지?
    //  여기도 Response 변경 부탁드립니다.
    @GetMapping(path = "/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List all User's Profile", description = "모든 유저 프로필 조회")
    public ResponseEntity<List<UserProfileWithDetailResponse>> getAllUserProfile() {
        return ResponseEntity.ok(userProfileService.getAllUserProfile());
    }

}
