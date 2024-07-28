package com.swyp.glint.user.application;

import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.meeting.application.MeetingFacade;
import com.swyp.glint.meeting.application.MeetingService;
import com.swyp.glint.user.application.dto.*;
import com.swyp.glint.user.domain.User;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserSimpleProfile;
import com.swyp.glint.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserDetailService userDetailService;

    public UserResponse createUser(UserRequest userRequest) {
        User user = userRequest.toEntity();
        return UserResponse.from(userRepository.save(user));
    }


    public UserResponse getUserById(Long id) {
        return UserResponse.from(userRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("id : " + id + " not found")));

    }

    public UserInfoResponse getUserInfoBy(Long id) {
        return userRepository.findUserInfoBy(id).orElseThrow(() -> new NotFoundEntityException("id : " + id + " not found"));

    }

    public User getUserEntity(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("id : " + id + " not found"));

    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundEntityException("email : " + email + " not found"));
    }

    @Transactional
    public UserLoginResponse oauthLoginUser(UserRequest userRequest) {
        User user = userRequest.toEntity();

        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        Optional<UserDetail> userDetailOptional = userDetailService.getUserDetailOptional(user.getId());

        // 회원가입한경우
        if(userOptional.isEmpty()) {
            return UserLoginResponse.from(userRepository.save(userRequest.toEntity()), false);
        }

        // 이미 회원가입은 했지만 detail 없는경우
        if(userDetailOptional.isEmpty()) {
            return UserLoginResponse.from(userOptional.get(), false);
        }
        // 이미 회원가입은 했지만 detail 있지만 완료하지 않은경우
        if(! userDetailOptional.get().isComplete()) {
            return UserLoginResponse.from(userOptional.get(), false);
        }

        // 이미 회원가입 했고 detail까지 완료한경우
        return UserLoginResponse.from(userOptional.get(), true);
    }


    public List<User> getUsers(List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }


    public List<UserMeetingResponse> getUserMeetingResponseList(List<Long> userIds) {
        return userRepository.findByUserIds(userIds).stream().map(UserMeetingResponse::from).toList();
    }

    public List<UserSimpleProfile> getUserSimpleProfileList(List<Long> userIds) {
        return userRepository.findByUserIds(userIds);
    }


    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
