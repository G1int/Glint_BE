package com.swyp.glint.chatting.application.dto.response;

import com.swyp.glint.user.domain.User;
import lombok.Builder;

public class ChatUserResponse {

    private Long userId;
    private String name;
    private String profileImageUrl;

    private ChatUserResponse(Long userId, String name, String profileImageUrl) {
        this.userId = userId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }



}
