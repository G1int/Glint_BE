package com.swyp.glint.user.api;

import com.swyp.glint.image.application.dto.ImageResponse;
import com.swyp.glint.keyword.application.DrinkingService;
import com.swyp.glint.user.application.UserDetailService;
import com.swyp.glint.user.application.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserDetailController {

    private final UserDetailService userDetailService;

    @Operation(summary = "Create User Detail", description = "새로운 User 추가 정보 생성", hidden = true)
    @PostMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetailResponse> createUserDetail(@PathVariable("userId") Long userId, @Valid @RequestBody UserDetailRequest userDetailRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userDetailService.createUserDetail(userId, userDetailRequest));
    }

    @Operation(summary = "User NickName Validate Check", description = "닉네임 유효성 검사")
    @GetMapping(path = "/nickname", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserNickNameValidationResponse> userNickNameValidate(@RequestParam String nickname) {

        return ResponseEntity.ok(userDetailService.isNicknameTaken(nickname));
    }


    @Operation(summary = "User NickName Validate Check", description = "유저 닉네임 중복 체크, 닉네임 유효성 체크, 유효성 통과시 임시 UserDetail 생성 후 반환 (닉네임 선점)")
    @PostMapping(path = "/{userId}/nickname", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetailResponse> userNickNameValidate(@PathVariable("userId") Long userId, @RequestBody UserNickNameRequest userNickNameRequest) {

        return ResponseEntity.ok(userDetailService.createTempUserDetail(userId, userNickNameRequest.nickname()));
    }

    @Operation(summary = "Get User Detail", description = "User Id를 통한 User 추가 정보 조회")
    @GetMapping(path = "/{userId}/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetailResponse> getUserDetail(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userDetailService.getUserDetailById(userId));
    }

    @Operation(summary =  "Update user detail", description = "유저 추가 정보 생성 및 수정")
    @PutMapping(path = "/{userId}/detail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetailResponse> updateUserDetail(@PathVariable("userId") Long userId, @Valid @RequestBody UserDetailRequest userDetailRequest) {
        return ResponseEntity.ok(userDetailService.updateUserDetail(userId, userDetailRequest));
    }

    @Operation(summary =  "userProfileImage 변경", description = "유저 프로필 이미지 변경")
    @PutMapping(path = "/{userId}/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetailResponse> updateUserProfileImage(
            @PathVariable Long userId,
            @RequestPart MultipartFile imageFile
    ) {
        return ResponseEntity.ok(userDetailService.updateUserProfileImage(userId, imageFile));
    }
}
