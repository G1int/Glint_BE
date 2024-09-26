package com.swyp.glint.user.application.impl;

import com.swyp.glint.core.common.exception.ErrorCode;
import com.swyp.glint.core.common.exception.InvalidValueException;
import com.swyp.glint.core.common.exception.NotFoundEntityException;
import com.swyp.glint.image.application.ImageService;
import com.swyp.glint.image.application.dto.ImageResponse;
import com.swyp.glint.user.application.dto.UserDetailRequest;
import com.swyp.glint.user.application.dto.UserDetailResponse;
import com.swyp.glint.user.application.dto.UserNickNameValidationResponse;
import com.swyp.glint.user.domain.NickNameValidator;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.infra.UserDetailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailService {

    private final UserDetailRepository userDetailRepository;
    private final UserProfileService userProfileService;

    private final ImageService imageService;

    public UserDetailResponse createUserDetail(Long userId, UserDetailRequest userDetailRequest) {
        UserDetail userDetail = userDetailRequest.toEntity(userId);
        userProfileService.createEmptyUserProfile(userId);
        return UserDetailResponse.from(userDetailRepository.save(userDetail));
    }

    public UserDetailResponse getUserDetailById(Long userId) {
        return UserDetailResponse.from(getUserEntityOrElseThrow(userId));
    }

    public UserDetail getUserDetail(Long userId) {
        return getUserEntityOrElseThrow(userId);
    }

    public Optional<UserDetail> findUserDetail(Long userId) {
        return userDetailRepository.findByUserId(userId);
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

        UserDetail userDetail = userDetailRepository.findByUserId(userId)
                .orElseGet(() -> {
                    return userDetailRepository.save(UserDetail.createTempUserDetailByNickName(userId));
                });

        userDetail.updateNickname(nickname);

        return UserDetailResponse.from(userDetail);
    }


    @Transactional
    public UserDetailResponse updateUserDetail(Long userId, UserDetailRequest userDetailRequest) {

        UserDetail userDetail = userDetailRepository.findByUserId(userId).orElse(userDetailRequest.toEntity(userId));
        userProfileService.createEmptyUserProfile(userId);

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
        return userDetailRepository.findAllByUserId(userIds);
    }


    public UserDetailResponse updateUserProfileImage(Long userId, MultipartFile userProfileImageFile) {
        UserDetail userDetail = userDetailRepository.findByUserId(userId).orElseThrow(() -> new NotFoundEntityException("UserDetail with userId: " + userId + " not found"));
        ImageResponse imageResponse = imageService.uploadProfileImageFile(userProfileImageFile);
        userDetail.updateProfileUrl(imageResponse.url());
        return UserDetailResponse.from(userDetailRepository.save(userDetail));
    }
}
