package com.swyp.glint.meeting.domain;

import com.swyp.glint.common.baseentity.BaseTimeEntity;
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

    @Column
    private Long userId;

    @Column
    private Long meetingId;

    @Column
    private String status;

    @Column
    private LocalDateTime createAt;

    @Builder(access = AccessLevel.PRIVATE)
    private JoinMeeting(Long id, Long userId, Long meetingId, String status, LocalDateTime createAt) {
        this.id = id;
        this.userId = userId;
        this.meetingId = meetingId;
        this.status = status;
        this.createAt = createAt;
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
    }

    public void reject() {
        status = JoinStatus.REJECTED.getName();
    }
}
