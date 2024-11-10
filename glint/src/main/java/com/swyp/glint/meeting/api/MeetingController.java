package com.swyp.glint.meeting.api;

import com.swyp.glint.meeting.application.dto.MeetingSearchCondition;
import com.swyp.glint.meeting.application.dto.request.MeetingRequest;
import com.swyp.glint.meeting.application.dto.response.MeetingDetailResponse;
import com.swyp.glint.meeting.application.dto.response.MeetingInfoCountResponses;
import com.swyp.glint.meeting.application.dto.response.MeetingInfoResponses;
import com.swyp.glint.meeting.application.usecase.impl.CreateMeetingUseCaseImpl;
import com.swyp.glint.meeting.application.usecase.impl.GetMeetingUseCaseImpl;
import com.swyp.glint.meeting.application.usecase.impl.UpdateMeetingUseCaseImpl;
import com.swyp.glint.meeting.application.usecase.impl.UserMeetingUseCaseImpl;
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


    private final UpdateMeetingUseCaseImpl updateMeetingUseCase;
    private final CreateMeetingUseCaseImpl createMeetingUseCase;
    private final GetMeetingUseCaseImpl getMeetingUseCase;
    private final UserMeetingUseCaseImpl userMeetingUseCase;

    @Operation(summary = "미팅 생성", description = "미팅 생성")
    @PostMapping(path = "/meeting", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MeetingDetailResponse> createMeeting(@RequestBody @Valid MeetingRequest meetingRequest) {

        return new ResponseEntity<>(createMeetingUseCase.createMeeting(meetingRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "미팅 수정", description = "미팅 수정")
    @PutMapping(path = "/meetings/{meetingId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MeetingDetailResponse> updateMeeting(
            @PathVariable Long meetingId,
            @RequestBody @Valid MeetingRequest meetingRequest
    ) {
        return ResponseEntity.ok(updateMeetingUseCase.updateMeeting(meetingId, meetingRequest));
    }


    @Operation(summary = "미팅 조회", description = "미팅 id를 통한 조회")
    @GetMapping(path = "/meetings/{meetingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MeetingDetailResponse> getMeeting(@PathVariable Long meetingId) {

        return ResponseEntity.ok(getMeetingUseCase.getMeeting(meetingId));
    }

    @Operation(summary = "New 미팅 조회", description = "메인화면 New 미팅 조회 ")
    @GetMapping(path = "/meetings/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MeetingInfoResponses> getNewMeeting(
            @RequestParam(required = false) Long lastMeetingId,
            @RequestParam(required = false) Integer limit
    ) {

        return ResponseEntity.ok(getMeetingUseCase.getNewMeeting(lastMeetingId, limit));
    }


    @Operation(summary = "내가 속한 미팅 조회", description = "메인화면 New 미팅 조회, status : WAITING(대기중미팅), ACCEPTED (참가미팅)")
    @GetMapping(path = "/meetings/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MeetingInfoResponses> getMyMeeting(
            @RequestParam @Pattern(regexp = "ACCEPTED|WAITING") @Valid String status,
            @PathVariable Long userId,
            @RequestParam(required = false) Long lastMeetingId,
            @RequestParam(required = false) Integer limit

    ) {

        return ResponseEntity.ok(getMeetingUseCase.getMyMeeting(userId, status, lastMeetingId, limit));
    }

    @Operation(summary = "미팅 나가기", description = "미팅 나가기")
    @PutMapping(path = "/meetings/{meetingId}/users/{userId}/out", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MeetingDetailResponse> userOutMeeting(
            @PathVariable Long meetingId,
            @PathVariable Long userId) {

        return ResponseEntity.ok(userMeetingUseCase.userMeetingOut(meetingId, userId));
    }


    @Operation(summary = "미팅 검색", description = "keyword 본문, 제목 매칭 조회")
    @GetMapping(path = "/meetings/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MeetingInfoCountResponses> searchMeeting(
            MeetingSearchCondition searchCondition,
            @RequestParam(required = false) Long userId
    ) {

        return ResponseEntity.ok(getMeetingUseCase.searchMeeting(searchCondition, userId));
    }





}
