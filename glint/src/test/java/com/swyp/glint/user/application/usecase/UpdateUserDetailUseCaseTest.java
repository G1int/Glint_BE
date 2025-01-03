package com.swyp.glint.user.application.usecase;

import com.swyp.glint.image.application.ImageService;
import com.swyp.glint.image.application.dto.ImageResponse;
import com.swyp.glint.user.application.dto.UserDetailRequest;
import com.swyp.glint.user.application.dto.UserDetailResponse;
import com.swyp.glint.user.domain.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase;


@SpringBootTest
@Transactional
@SqlGroup({
        @Sql(value = "/sql/user/update-user-detail-use-case-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
})
class UpdateUserDetailUseCaseTest {

    @Autowired
    private UpdateUserDetailUseCase updateUserDetailUseCase;

    @MockBean
    ImageService imageService;


    @Test
    @DisplayName("userDetail 정보를 업데이트 할 수 있다.")
    public void updateUserDetail() {

        // given

        Long userId = 1L;
        UserDetailRequest userDetailRequest = UserDetailRequest.of(
                "updateNickName",
                Gender.MALE.name(),
                "2000-01-01",
                175,
                "updateProfileImage"
        );

        // when
        UserDetailResponse userDetailResponse = updateUserDetailUseCase.updateUserDetail(userId, userDetailRequest);

        // then
        assertThat(userDetailResponse.userId()).isEqualTo(1L);
        assertThat(userDetailResponse.nickname()).isEqualTo("updateNickName");
        assertThat(userDetailResponse.gender()).isEqualTo("MALE");
        assertThat(userDetailResponse.birthdate()).isEqualTo("2000-01-01");
        assertThat(userDetailResponse.height()).isEqualTo(175);
        assertThat(userDetailResponse.profileImage()).isEqualTo("updateProfileImage");
    }


    @Test
    @DisplayName("userDetail profileImage 정보를 업데이트 할 수 있다.")
    public void updateUserDetailProfileImage() {

        // given
        Long userId = 1L;
        MultipartFile profileImageFile = new MockMultipartFile("updatedProfileImage", "updatedProfileImage.jpg", "image/jpeg", "test".getBytes());
        when(imageService.uploadProfileImageFile(profileImageFile)).thenReturn(new ImageResponse("updatedProfileImage", "updatedProfileImage.jpg"));

        // when
        UserDetailResponse userDetailResponse = updateUserDetailUseCase.updateUserProfileImage(userId, profileImageFile);

        // then
        assertThat(userDetailResponse.userId()).isEqualTo(1L);
        assertThat(userDetailResponse.nickname()).isEqualTo("test");
        assertThat(userDetailResponse.gender()).isEqualTo("MALE");
        assertThat(userDetailResponse.birthdate()).isEqualTo("1990-01-01");
        assertThat(userDetailResponse.height()).isEqualTo(180);
        assertThat(userDetailResponse.profileImage()).isEqualTo("updatedProfileImage.jpg");
    }







}