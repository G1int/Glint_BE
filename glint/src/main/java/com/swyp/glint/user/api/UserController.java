package com.swyp.glint.user.api;

import com.swyp.glint.user.api.dto.UserRequest;
import com.swyp.glint.user.api.dto.UserResponse;
import com.swyp.glint.user.application.UserService;
import com.swyp.glint.user.application.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get user", description = "Get user ")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {

        return ResponseEntity.ok(userService.getUser(id));
    }


    @Operation(summary = "create user", description = "create user")
    @PutMapping(value = "/hi", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.createUser(userRequest);
        return ResponseEntity.created(URI.create("/" + userResponse.id())).body(userService.createUser(userRequest));
    }

}
