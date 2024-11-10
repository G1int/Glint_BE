package com.swyp.glint.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyp.glint.core.system.error.ServiceExceptionHandler;
import com.swyp.glint.image.application.ImageService;
import com.swyp.glint.image.application.dto.ImageResponse;
import com.swyp.glint.user.application.dto.UserDetailRequest;
import com.swyp.glint.user.application.dto.UserNickNameRequest;
import com.swyp.glint.user.application.dto.UserProfileRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SqlGroup({
        @Sql(value = "/sql/user/user-profile-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/user/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
})
@SpringBootTest
public class UserProfileControllerTest {

    @Autowired
    UserProfileController userProfileController;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;


    @BeforeEach
    public void beforeEach() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(userProfileController)
                .setControllerAdvice(ServiceExceptionHandler.class)
                .build();

    }

    @Test
    @DisplayName("userProfile을 입력 할 수 있다.")
    public void createUserDetail() throws Exception {
        // given
        UserProfileRequest userProfileRequest = new UserProfileRequest(
                "개발자",
                "서울대학교",
                "컴퓨터공학과",
                "서울",
                "종로구",
                1L,
                1L,
                1L,
                "안녕하세요 반갑습니다.",
                List.of("INTJ")
        );

        // when
        // then
        mockMvc.perform(put("/users/{userId}/profile", 2L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userProfileRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(2L))
                .andExpect(jsonPath("$.userDetail.id").value(2L))
                .andExpect(jsonPath("$.userDetail.userId").value(2L))
                .andExpect(jsonPath("$.userDetail.nickname").value("test2"))
                .andExpect(jsonPath("$.userDetail.gender").value("MALE"))
                .andExpect(jsonPath("$.userDetail.birthdate").value("1990-01-01"))
                .andExpect(jsonPath("$.userDetail.age").value(34))
                .andExpect(jsonPath("$.userDetail.height").value(180))
                .andExpect(jsonPath("$.userDetail.profileImage").value("test.jpg"))
                .andExpect(jsonPath("$.userProfile.id").exists())
                .andExpect(jsonPath("$.userProfile.work.workId").value(1L))
                .andExpect(jsonPath("$.userProfile.work.workName").value("개발자"))
                .andExpect(jsonPath("$.userProfile.work.workCategory").doesNotExist())
                .andExpect(jsonPath("$.userProfile.university.universityId").value(1L))
                .andExpect(jsonPath("$.userProfile.university.universityName").value("서울대학교"))
                .andExpect(jsonPath("$.userProfile.university.universityDepartment").value("컴퓨터공학과"))
                .andExpect(jsonPath("$.userProfile.university.universityCategory.universityCategoryId").value(1L))
                .andExpect(jsonPath("$.userProfile.university.universityCategory.universityCategoryName").value("명문대"))
                .andExpect(jsonPath("$.userProfile.location.locationId").value(1L))
                .andExpect(jsonPath("$.userProfile.location.locationCity").value("종로구"))
                .andExpect(jsonPath("$.userProfile.location.locationState").value("서울"))
                .andExpect(jsonPath("$.userProfile.religion.religionId").value(1L))
                .andExpect(jsonPath("$.userProfile.religion.religionName").value("무교"))
                .andExpect(jsonPath("$.userProfile.smoking.smokingId").value(1L))
                .andExpect(jsonPath("$.userProfile.smoking.smokingName").value("비흡연"))
                .andExpect(jsonPath("$.userProfile.drinking.drinkingId").value(1L))
                .andExpect(jsonPath("$.userProfile.drinking.drinkingName").value("마시지 않음"))
                .andExpect(jsonPath("$.userProfile.selfIntroduction").value("안녕하세요 반갑습니다."))
                .andExpect(jsonPath("$.userProfile.hashtags[0]").value("INTJ"));
        ;
    }


    @Test
    @DisplayName("userProfile 정보를 조회 할 수 있다.")
    public void getUserDetail() throws Exception {
        // given
        Long userId = 1L;

        // when
        // then
        mockMvc.perform(get("/users/{userId}/profile", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.work.workId").value(1L))
                .andExpect(jsonPath("$.work.workName").value("개발자"))
                .andExpect(jsonPath("$.work.workCategory").doesNotExist())
                .andExpect(jsonPath("$.university.universityId").value(1L))
                .andExpect(jsonPath("$.university.universityName").value("서울대학교"))
                .andExpect(jsonPath("$.university.universityDepartment").value("컴퓨터공학과"))
                .andExpect(jsonPath("$.university.universityCategory.universityCategoryId").value(1L))
                .andExpect(jsonPath("$.university.universityCategory.universityCategoryName").value("명문대"))
                .andExpect(jsonPath("$.location.locationId").value(1L))
                .andExpect(jsonPath("$.location.locationCity").value("종로구"))
                .andExpect(jsonPath("$.location.locationState").value("서울"))
                .andExpect(jsonPath("$.religion.religionId").value(1L))
                .andExpect(jsonPath("$.religion.religionName").value("무교"))
                .andExpect(jsonPath("$.smoking.smokingId").value(1L))
                .andExpect(jsonPath("$.smoking.smokingName").value("비흡연"))
                .andExpect(jsonPath("$.drinking.drinkingId").value(1L))
                .andExpect(jsonPath("$.drinking.drinkingName").value("마시지 않음"))
                .andExpect(jsonPath("$.selfIntroduction").value("안녕하세요 자기소개입니다."))
                .andExpect(jsonPath("$.hashtags[0]").value("INTJ"));
        ;
    }


}
