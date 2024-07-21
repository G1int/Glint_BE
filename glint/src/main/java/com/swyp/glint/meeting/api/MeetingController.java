package com.swyp.glint.meeting.api;

import com.swyp.glint.meeting.application.dto.response.MeetingInfoResponses;
import com.swyp.glint.meeting.application.dto.response.MeetingResponse;
import com.swyp.glint.meeting.application.MeetingService;
import com.swyp.glint.meeting.application.dto.request.MeetingRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    @Operation(summary = "미팅 생성", description = "미팅 생성")
    @PostMapping(path = "/meeting", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MeetingResponse> createMeeting(@RequestBody @Valid MeetingRequest meetingRequest) {

        return new ResponseEntity<>(meetingService.createMeeting(meetingRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "미팅 조회", description = "미팅 id를 통한 조회")
    @GetMapping(path = "/meetings/{meetingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MeetingResponse> getMeeting(@PathVariable Long meetingId) {

        return ResponseEntity.ok(meetingService.getMeeting(meetingId));
    }

    @Operation(summary = "New 미팅 조회", description = "메인화면 New 미팅 조회 ")
    @GetMapping(path = "/meetings/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MeetingInfoResponses> getNewMeeting(
            @RequestParam(required = false) Long lastId,
            @RequestParam(required = false) Integer size
    ) {

        return ResponseEntity.ok(meetingService.getNewMeeting(lastId, size));
    }


    @Operation(summary = "내가 속한 미팅 조회", description = "메인화면 New 미팅 조회, status : WAITING(대기중미팅), PROGRESS (참가미팅)")
    @GetMapping(path = "/meetings/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MeetingInfoResponses> getNewMeeting(
            @RequestParam @Pattern(regexp = "WAITING|PROGRESS") @Valid String status,
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(meetingService.getMyMeeting(userId, status));
    }






}