package com.swyp.glint.meeting.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Join {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "join_id")
    private Long id;

    @Column
    private Long userId;

    @Column
    private Long meetingId;

    @Column
    private String status;

    @Builder(access = AccessLevel.PRIVATE)
    private Join(Long id, Long userId, Long meetingId, String status) {
        this.id = id;
        this.userId = userId;
        this.meetingId = meetingId;
        this.status = status;
    }

    public static Join createByRequest(Long userId, Long meetingId) {
        return Join.builder()
                .userId(userId)
                .meetingId(meetingId)
                .status(JoinStatus.WAITING.getName())
                .build();
    }
}
