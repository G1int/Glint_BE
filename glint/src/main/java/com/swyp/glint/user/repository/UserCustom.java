package com.swyp.glint.user.repository;

import com.swyp.glint.user.domain.UserInfo;

import java.util.Optional;

public interface UserCustom {
    Optional<UserInfo> findUserInfoBy(Long userId);

}
