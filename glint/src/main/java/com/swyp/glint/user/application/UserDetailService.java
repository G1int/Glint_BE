package com.swyp.glint.user.application;

import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.user.application.dto.UserDetailRequest;
import com.swyp.glint.user.application.dto.UserDetailResponse;
import com.swyp.glint.user.application.dto.UserResponse;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.repository.UserDetailRepository;
import com.swyp.glint.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService {

    private final UserDetailRepository userDetailRepository;
    private final UserService userService;

    public UserDetailResponse createUserDetail(UserDetailRequest userDetailRequest) {
        UserDetail userDetail = userDetailRequest.toEntity();
        return UserDetailResponse.from(userDetailRepository.save(userDetail));
    }

    public UserDetailResponse getUserDetailByUserId(Long userId) {
        UserResponse userResponse = userService.getUserById(userId);
        return UserDetailResponse.from(userDetailRepository.findByUserId(userResponse.id())
                .orElseThrow(() -> new NotFoundEntityException("UserDetail with id: " + userId + " not found")));
    }

    public boolean isNicknameTaken(String nickname) {
        return userDetailRepository.findByNickname(nickname).isPresent();
    }

    @Transactional
    public UserDetailResponse updateUserDetail(Long userId, UserDetailRequest userDetailRequest) {
        UserResponse userResponse = userService.getUserById(userId);
        UserDetail userDetail = userDetailRepository.findByUserId(userResponse.id())
                .orElseThrow(() -> new NotFoundEntityException("UserDetail with id: " + userId + " not found"));

        userDetail.updateUserDetail(userDetailRequest.nickname(), userDetailRequest.gender(), userDetailRequest.birthdate(), userDetailRequest.height(), userDetailRequest.profileImage());

        return UserDetailResponse.from(userDetailRepository.save(userDetail));
    }

    public void deleteUserDetail(Long userId) {
        userDetailRepository.deleteById(userId);
    }

}
