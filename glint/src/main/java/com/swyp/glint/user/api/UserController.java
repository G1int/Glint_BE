package com.swyp.glint.user.api;

import com.swyp.glint.user.api.dto.UserRequest;
import com.swyp.glint.user.api.dto.UserResponse;
import com.swyp.glint.user.application.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

//    private final UserService userService;

    @Operation(summary = "Get member profile", description = "Get member profile")
    @GetMapping(value = "hi", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUser() {
        // 201
        return "hi glint!";
    }


//    @Operation(summary = "Get member profile")
//    @PutMapping(value = "/hi", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
//
//        // 200
//        return ResponseEntity.created(URI.create("/hi")).body(new UserResponse("name", "email"));
//    }
//
//
//    @PutMapping("/hi")
//    @Operation(summary = "Get member profile")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "성공"),
//            @ApiResponse(responseCode = "404", description = "해당 ID의 유저가 존재하지 않습니다.")
//    })
//    public ResponseEntity<UserResponse> modifyUser() {
//        System.out.println("hello glint!");
//
//        // 200
//        return ResponseEntity.ok(new UserResponse("name", "email"));
//    }
//
//
//    @PutMapping("/hi")
//    @Operation(summary = "Get member profile")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "성공"),
//            @ApiResponse(responseCode = "404", description = "해당 ID의 유저가 존재하지 않습니다.")
//    })
//    public ResponseEntity removeUser() {
//        System.out.println("hello glint!");
//
//        // 204
//        return ResponseEntity.noContent().build();
//    }
//


}
