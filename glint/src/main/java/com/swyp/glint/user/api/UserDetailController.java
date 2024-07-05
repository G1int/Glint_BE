package com.swyp.glint.user.api;

import com.swyp.glint.user.application.UserDetailService;
import com.swyp.glint.user.application.dto.UserDetailRequest;
import com.swyp.glint.user.application.dto.UserDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserDetailController {

    private final UserDetailService userDetailService;

    @Operation(summary = "Create User Detail", description = "새로운 User 추가 정보 생성")
    @PostMapping
    public ResponseEntity<UserDetailResponse> createUserDetail(@RequestBody UserDetailRequest userDetailRequest) {
        if (userDetailService.isNicknameTaken(userDetailRequest.nickname())) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(userDetailService.createUserDetail(userDetailRequest));
    }

    @Operation(summary = "Get User Detail", description = "User Id를 통한 User 추가 정보 조회")
    @GetMapping("/{id}/detail")
    public ResponseEntity<UserDetailResponse> getUserDetail(@PathVariable Long id) {
        return ResponseEntity.ok(userDetailService.getUserDetailById(id));
    }

    @Operation(summary =  "Update user detail", description = "User Id를 통한 추가 정보 수정")
    @PutMapping("/{id}/detail")
    public ResponseEntity<UserDetailResponse> updateUserDetail(@PathVariable Long id, @RequestBody UserDetailRequest userDetailRequest) {
        return ResponseEntity.ok(userDetailService.updateUserDetail(id, userDetailRequest));
    }
}
