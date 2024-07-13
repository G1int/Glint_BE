package com.swyp.glint.user.api;

import com.swyp.glint.user.application.UserProfileService;
import com.swyp.glint.user.application.dto.UserProfileRequest;
import com.swyp.glint.user.application.dto.UserProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Operation(summary = "Create User Profile", description = "새로운 유저 프로필 생성")
    @PostMapping(path = "/{id}/profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfileResponse> createUserProfile(
             @PathVariable Long id, @Valid @RequestBody UserProfileRequest userProfileRequest) {
        return ResponseEntity.ok(userProfileService.createUserProfile(id, userProfileRequest));
    }

    @Operation(summary = "Get User Profile", description = "유저 프로필 조회")
    @GetMapping(path = "/{id}/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable Long id) {
        return ResponseEntity.ok(userProfileService.getUserProfileById(id));
    }

    @Operation(summary = "Update User Profile", description = "유저 프로필 수정")
    @PutMapping(path = "/{id}/profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfileResponse> updateUserProfile(@PathVariable Long id, @Valid @RequestBody UserProfileRequest userProfileRequest) {
        return ResponseEntity.ok(userProfileService.updateUserProfile(id, userProfileRequest));
    }
}
