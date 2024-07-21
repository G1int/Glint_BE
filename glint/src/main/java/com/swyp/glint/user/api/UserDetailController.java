package com.swyp.glint.user.api;

import com.swyp.glint.keyword.application.DrinkingService;
import com.swyp.glint.user.application.UserDetailService;
import com.swyp.glint.user.application.dto.UserDetailRequest;
import com.swyp.glint.user.application.dto.UserDetailResponse;
import com.swyp.glint.user.application.dto.UserNickNameRequest;
import com.swyp.glint.user.application.dto.UserNickNameValidationResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserDetailController {

    private final UserDetailService userDetailService;

    @Operation(summary = "Create User Detail", description = "새로운 User 추가 정보 생성", hidden = true)
    @PostMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetailResponse> createUserDetail(@PathVariable("id") Long userId, @Valid @RequestBody UserDetailRequest userDetailRequest) {

        return ResponseEntity.ok(userDetailService.createUserDetail(userId, userDetailRequest));
    }

    @Operation(summary = "User NickName Validate Check", description = "닉네임 유효성 검사")
    @GetMapping(path = "/nickname", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserNickNameValidationResponse> userNickNameValidate(@RequestParam String nickname) {

        return ResponseEntity.ok(userDetailService.isNicknameTaken(nickname));
    }


    @Operation(summary = "User NickName Validate Check", description = "유저 닉네임 중복 체크, 닉네임 유효성 체크, 유효성 통과시 임시 UserDetail 생성 후 반환 (닉네임 선점)")
    @PostMapping(path = "/{userId}/nickname", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetailResponse> userNickNameValidate(@PathVariable("id") Long userId, @RequestBody UserNickNameRequest userNickNameRequest) {

        return ResponseEntity.ok(userDetailService.createTempUserDetail(userId, userNickNameRequest.nickname()));
    }

    @Operation(summary = "Get User Detail", description = "User Id를 통한 User 추가 정보 조회")
    @GetMapping(path = "/{userId}/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetailResponse> getUserDetail(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userDetailService.getUserDetailById(id));
    }

    @Operation(summary =  "Update user detail", description = "유저 추가 정보 생성 및 수정")
    @PutMapping(path = "/{userId}/detail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetailResponse> updateUserDetail(@PathVariable("id") Long userId, @Valid @RequestBody UserDetailRequest userDetailRequest) {
        return ResponseEntity.ok(userDetailService.updateUserDetail(userId, userDetailRequest));
    }

}
