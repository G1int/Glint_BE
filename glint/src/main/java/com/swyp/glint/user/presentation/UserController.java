package com.swyp.glint.user.presentation;

import com.swyp.glint.user.application.dto.UserInfoResponse;
import com.swyp.glint.user.application.dto.UserResponse;
import com.swyp.glint.user.application.impl.UserMeetingFacade;
import com.swyp.glint.user.application.usecase.GetUserUseCase;
import com.swyp.glint.user.application.usecase.UserInfoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserMeetingFacade userMeetingFacade;
    private final UserInfoUseCase userInfoUseCase;
    private final GetUserUseCase getUserUseCase;

    @Operation(summary = "Get user", description = "User Id를 통한 User 정보 조회")
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> getUser(@PathVariable("userId") Long id) {

        return ResponseEntity.ok(getUserUseCase.getUserBy(id));
    }

    @Operation(summary = "Get userInfo", description = "user, userDetail, userProfile 모든 정보 조회")
    @GetMapping(value = "/{userId}/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoResponse> getUserInfo(@PathVariable("userId") Long id) {

        return ResponseEntity.ok(userInfoUseCase.getUserInfoBy(id));
    }


    @Operation(summary = "Delete user", description = "User 삭제, 참가중인 미팅에서 모두 Out,참가신청 모두 거절")
    @DeleteMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
        userMeetingFacade.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
