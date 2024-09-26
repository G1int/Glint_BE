package com.swyp.glint.meeting.domain;

import com.swyp.glint.core.common.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinMeeting extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "join_meeting_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "meeting_id")
    private Long meetingId;

    @Column(name = "status")
    private String status;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "join_date_time")
    private LocalDateTime joinDateTime;

    @Builder(access = AccessLevel.PRIVATE)
    private JoinMeeting(Long id, Long userId, Long meetingId, String status, LocalDateTime createAt, LocalDateTime joinDateTime) {
        this.id = id;
        this.userId = userId;
        this.meetingId = meetingId;
        this.status = status;
        this.createAt = createAt;
        this.joinDateTime = joinDateTime;
    }
    public static JoinMeeting createByMeetingInit(Long userId, Long meetingId) {
        return JoinMeeting.builder()
                .userId(userId)
                .meetingId(meetingId)
                .status(JoinStatus.ACCEPTED.getName())
                .createAt(LocalDateTime.now())
                .joinDateTime(LocalDateTime.now())
                .build();
    }


    public static JoinMeeting createByRequest(Long userId, Long meetingId) {
        return JoinMeeting.builder()
                .userId(userId)
                .meetingId(meetingId)
                .status(JoinStatus.WAITING.getName())
                .createAt(LocalDateTime.now())
                .build();
    }

    public void accept() {
        status = JoinStatus.ACCEPTED.getName();
        joinDateTime = LocalDateTime.now();
    }

    public void reject() {
        status = JoinStatus.REJECTED.getName();
    }
}
