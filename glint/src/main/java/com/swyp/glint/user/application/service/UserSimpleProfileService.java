package com.swyp.glint.user.application.service;

import com.swyp.glint.user.domain.UserSimpleProfile;

import java.util.List;

public interface UserSimpleProfileService {

    List<UserSimpleProfile> getUserSimpleProfileList(List<Long> userIds);
}
