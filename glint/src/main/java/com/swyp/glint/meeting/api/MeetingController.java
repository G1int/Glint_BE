package com.swyp.glint.meeting.api;

import com.swyp.glint.meeting.application.dto.response.MeetingResponse;
import com.swyp.glint.meeting.application.MeetingService;
import com.swyp.glint.meeting.application.dto.request.MeetingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping(value = "/meeting", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MeetingResponse createMeeting(@RequestBody MeetingRequest meetingRequest) {

        return meetingService.createMeeting(meetingRequest);
    }

    @GetMapping(value = "/meetings/{meetingId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MeetingResponse getMeeting(@PathVariable Long meetingId) {

        return meetingService.createMeeting(meetingRequest);
    }



}
