package com.swyp.glint.chatting.domain;

import com.swyp.glint.common.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Table(name = "chat_room")
@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    Long id;

    @Column(name = "meeting_id")
    Long meetingId;

    @ElementCollection
    @Column(name = "user_id")
    List<Long> userIds;

    @Column(name = "name")
    String name;

    @Column(name = "is_activated")
    Boolean isActivated;

    @Column(name = "is_archived")
    Boolean isArchived;

    @Builder(access = AccessLevel.PRIVATE)
    private ChatRoom(Long id, Long meetingId, List<Long> userIds, String name, boolean isActivated, boolean isArchived) {
        this.id = id;
        this.meetingId = meetingId;
        this.userIds = userIds;
        this.name = name;
        this.isActivated = isActivated;
        this.isArchived = isArchived;
    }

    @Builder(access = AccessLevel.PRIVATE)
    public static ChatRoom createByMeeting(Long meetingId, List<Long> userIds) {
        return ChatRoom.builder()
                .userIds(userIds)
                .meetingId(meetingId)
                .isActivated(false)
                .isArchived(false)
                .build();
    }

    public void archive() {
        this.isArchived = true;
    }


    public void active() {
        this.isActivated = true;
    }

    public boolean isActivated() {
        return this.isActivated;
    }

    public boolean isJoinUser(Long sendUserId) {

        return false;
    }
}


