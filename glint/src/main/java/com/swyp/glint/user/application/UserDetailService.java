package com.swyp.glint.user.application;

import com.swyp.glint.common.exception.ErrorCode;
import com.swyp.glint.common.exception.InvalidValueException;
import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.user.application.dto.UserDetailRequest;
import com.swyp.glint.user.application.dto.UserDetailResponse;
import com.swyp.glint.user.application.dto.UserNickNameValidationResponse;
import com.swyp.glint.user.application.dto.UserResponse;
import com.swyp.glint.user.domain.NickNameValidator;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.repository.UserDetailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailService {

    private final UserDetailRepository userDetailRepository;

    private final UserService userService;

    public UserDetailResponse createUserDetail(Long userId, UserDetailRequest userDetailRequest) {
        UserDetail userDetail = userDetailRequest.toEntity(userId);
        return UserDetailResponse.from(userDetailRepository.save(userDetail));
    }

    public UserDetailResponse getUserDetailById(Long userId) {
        return UserDetailResponse.from(getUserEntityOrElseThrow(userId));
    }

    public UserDetail getUserDetail(Long userId) {
        return getUserEntityOrElseThrow(userId);
    }

    public UserDetail getUserEntityOrElseThrow(Long userId) {
        return userDetailRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundEntityException("UserDetail with userId: " + userId + " not found"));
    }


    public UserNickNameValidationResponse isNicknameTaken(String nickname) {
        if (!NickNameValidator.isValid(nickname)) {
            throw new InvalidValueException(ErrorCode.NICKNAME_INVALID);
        }

        Optional<UserDetail> userDetailOptional = userDetailRepository.findByNickname(nickname);
        if(userDetailOptional.isPresent()) {
            throw new InvalidValueException(ErrorCode.NICKNAME_DUPLICATED);
        }

        return UserNickNameValidationResponse.from(true, nickname);
    }

    @Transactional
    public UserDetailResponse createTempUserDetail(Long userId, String nickname) {
        if (!NickNameValidator.isValid(nickname)) {
            throw new InvalidValueException(ErrorCode.NICKNAME_INVALID);
        }

        Optional<UserDetail> userDetailOptional = userDetailRepository.findByNickname(nickname);

        if(userDetailOptional.isPresent() && ! userDetailOptional.get().getUserId().equals(userId)) {
            throw new InvalidValueException(ErrorCode.NICKNAME_DUPLICATED);
        }

        UserDetail userDetail = userDetailRepository.findByUserId(userId).orElseGet(() -> {
            return userDetailRepository.save(UserDetail.createTempUserDetailByNickName(userId));
        });

        userDetail.updateNickname(nickname);

        return UserDetailResponse.from(userDetail);
    }


    @Transactional
    public UserDetailResponse updateUserDetail(Long userId, UserDetailRequest userDetailRequest) {
        UserResponse user = userService.getUserById(userId);

        UserDetail userDetail = userDetailRepository.findByUserId(user.id()).orElse(userDetailRequest.toEntity(userId));

        userDetail.updateUserDetail(
                userDetailRequest.nickname(),
                userDetailRequest.gender(),
                LocalDate.parse(userDetailRequest.birthdate()),
                Integer.parseInt(userDetailRequest.height()),
                userDetailRequest.profileImage()
        );

        return UserDetailResponse.from(userDetailRepository.save(userDetail));
    }

    public void deleteUserDetail(Long id) { //추가 정보 하나하나 삭제로 바꿔줘야 함..
        userDetailRepository.deleteById(id);
    }

    public List<UserDetail> getUserDetails(List<Long> userIds) {
        return userDetailRepository.findAllById(userIds);
    }
}
