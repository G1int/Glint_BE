package com.swyp.glint.user.application.impl;

import com.swyp.glint.core.common.exception.NotFoundEntityException;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.infra.UserDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailService {

    private final UserDetailRepository userDetailRepository;

    public UserDetail getUserDetailBy(Long userId) {
        return userDetailRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundEntityException("UserDetail with userId: " + userId + " not found"));
    }

    public UserDetail save(UserDetail userDetail) {
        return userDetailRepository.save(userDetail);
    }

    public Optional<UserDetail> findBy(String nickName) {
        return userDetailRepository.findByNickname(nickName);
    }

    public Optional<UserDetail> findBy(Long userId) {
        return userDetailRepository.findByUserId(userId);
    }


    public void deleteUserDetail(Long id) { //추가 정보 하나하나 삭제로 바꿔줘야 함..
        userDetailRepository.deleteById(id);
    }

    public List<UserDetail> getUserDetails(List<Long> userIds) {
        return userDetailRepository.findAllByUserId(userIds);
    }


}
