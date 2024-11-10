package com.swyp.glint.user.application.service;

import com.swyp.glint.user.domain.User;
import com.swyp.glint.user.domain.UserInfo;

import java.util.Optional;

public interface UserService {

    User getUserBy(Long id);

    UserInfo getUserInfoBy(Long id);

    User getUserEntity(Long id);

    User getUserBy(String email);

    User save(User user);

    Optional<User> findBy(String email);

}
