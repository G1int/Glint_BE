package com.swyp.glint.user.api;

import com.swyp.glint.user.application.UserService;
import com.swyp.glint.user.application.dto.UserInfoResponse;
import com.swyp.glint.user.application.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get user", description = "User Id를 통한 User 정보 조회")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "Get userInfo", description = "user , userDetail, userProfile 정보 조회")
    @GetMapping(value = "/info/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoResponse> getUserInfo(@PathVariable("id") Long id) {

        return ResponseEntity.ok(userService.getUserInfoBy(id));
    }



    // 닉네임 중복검사, 유효성 체크
}
