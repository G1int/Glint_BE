package com.swyp.glint.user.infra;

import com.swyp.glint.user.domain.UserInfo;

import java.util.Optional;

public interface UserCustom {
    Optional<UserInfo> findUserInfoBy(Long userId);

}
