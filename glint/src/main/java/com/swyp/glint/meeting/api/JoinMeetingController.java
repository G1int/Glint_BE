package com.swyp.glint.meeting.api;

import com.swyp.glint.meeting.application.JoinMeetingService;
import com.swyp.glint.meeting.application.UserJoinMeetingResponses;
import com.swyp.glint.meeting.application.dto.response.JoinMeetingResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class JoinMeetingController {

    private final JoinMeetingService joinMeetingService;

    @Operation(summary = "미팅방 참가 신청", description = "현재 미팅방 참가 신청 (조건 검사 미달시 Exception)")
    @PostMapping(path = "/meetings/{meetingId}/join/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JoinMeetingResponse> joinMeeting(@PathVariable Long meetingId, @PathVariable Long userId) {
        return new ResponseEntity<>(joinMeetingService.joinMeetingRequest(userId, meetingId), HttpStatus.CREATED);
    }

    @Operation(summary = "미팅방 참가 신청 목록", description = "현재 미팅방 참가 신청 유저 리스트 ")
    @GetMapping(path = "/meetings/{meetingId}/join", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserJoinMeetingResponses> getAllJoinMeeting(@PathVariable Long meetingId, @RequestParam(required = false) Long lastJoinMeetingId){
        return ResponseEntity.ok(joinMeetingService.getAllJoinMeeting(meetingId, lastJoinMeetingId));
    }

    @Operation(summary = "미팅방 참가 신청 수락", description = "현재 미팅방 참가 신청 수락")
    @PutMapping(path = "/meetings/{meetingId}/join/users/{userId}/accept", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JoinMeetingResponse> acceptJoinMeeting(@PathVariable Long meetingId, @PathVariable Long userId) {
        return ResponseEntity.ok(joinMeetingService.acceptJoinMeeting(userId, meetingId));
    }

    @Operation(summary = "미팅방 참가 신청 거절", description = "현재 미팅방 참가 신청 거절")
    @PutMapping(path = "/meetings/{meetingId}/join/users/{userId}/reject", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JoinMeetingResponse> rejectJoinMeeting(@PathVariable Long meetingId, @PathVariable Long userId) {
        return ResponseEntity.ok(joinMeetingService.rejectJoinMeeting(userId, meetingId));
    }


}
