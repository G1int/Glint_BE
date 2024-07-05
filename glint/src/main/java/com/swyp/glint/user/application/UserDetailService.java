package com.swyp.glint.user.application;

import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.user.application.dto.UserDetailRequest;
import com.swyp.glint.user.application.dto.UserDetailResponse;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.repository.UserDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService {

    private final UserDetailRepository userDetailRepository;

    public UserDetailResponse createUserDetail(UserDetailRequest userDetailRequest) {
        UserDetail userDetail = userDetailRequest.toEntity();
        return UserDetailResponse.from(userDetailRepository.save(userDetail));
    }

    public UserDetailResponse getUserDetailById(Long id) {
        return UserDetailResponse.from(userDetailRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("UserDetail with id: " + id + " not found")));
    }

    public boolean isNicknameTaken(String nickname) {
        return userDetailRepository.findByNickname(nickname).isPresent();
    }

    public UserDetailResponse updateUserDetail(Long id, UserDetailRequest userDetailRequest) {
        UserDetail userDetail = userDetailRepository.findById(id).orElseThrow(() -> new IllegalStateException("Detail not found"));

        userDetail.updateUserDetail(userDetailRequest.nickname(), userDetailRequest.gender(), userDetailRequest.birthdate(), userDetailRequest.height(), userDetailRequest.profileImage());

        return UserDetailResponse.from(userDetailRepository.save(userDetail));
    }

    public void deleteUserDetail(Long id) { //추가 정보 하나하나 삭제로 바꿔줘야 함..
        userDetailRepository.deleteById(id);
    }

}
