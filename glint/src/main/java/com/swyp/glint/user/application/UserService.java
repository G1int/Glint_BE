package com.swyp.glint.user.application;

import com.swyp.glint.user.application.dto.UserInfoResponse;
import com.swyp.glint.user.application.dto.UserLoginResponse;
import com.swyp.glint.user.application.dto.UserRequest;
import com.swyp.glint.user.application.dto.UserResponse;
import com.swyp.glint.user.domain.User;
import com.swyp.glint.user.domain.UserSimpleProfile;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponse getUserById(Long id);

    UserInfoResponse getUserInfoBy(Long id);

    User getUserEntity(Long id);

    User getUserByEmail(String email);

    UserLoginResponse oauthLoginUser(UserRequest userRequest);

    List<UserSimpleProfile> getUserSimpleProfileList(List<Long> userIds);

    User save(User user);

    Optional<User> findBy(String email);
}
