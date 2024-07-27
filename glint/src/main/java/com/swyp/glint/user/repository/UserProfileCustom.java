package com.swyp.glint.user.repository;

import com.swyp.glint.user.application.dto.UserInfoResponse;
import com.swyp.glint.user.domain.UserSimpleProfile;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserProfileCustom {
    Optional<UserInfoResponse> findUserInfoBy(Long userId);


}
