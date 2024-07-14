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

    @Operation(summary = "Get User Profile by User Id", description = "유저 프로필 조회")
    @GetMapping(path = "/{userId}/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userProfileService.getUserProfileById(userId));
    }

    @GetMapping(path = "/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List all User's Profile", description = "모든 유저 프로필 조회")
    public ResponseEntity<List<UserProfile>> getAllUserProfile() {
        List<UserProfile> userProfiles = userProfileService.getAllUserProfile();
        return ResponseEntity.ok(userProfiles);
    }

    @Operation(summary = "Update User Profile", description = "유저 프로필 수정")
    @PutMapping(path = "/{userId}/profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfileResponse> updateUserProfile(@PathVariable Long userId, @Valid @RequestBody UserProfileRequest userProfileRequest) {
        return ResponseEntity.ok(userProfileService.updateUserProfile(userId, userProfileRequest));
    }

}
