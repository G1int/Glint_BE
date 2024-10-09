package com.swyp.glint.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyp.glint.core.system.error.ServiceExceptionHandler;
import com.swyp.glint.user.application.dto.*;
import com.swyp.glint.user.application.usecase.GetUserUseCase;
import com.swyp.glint.user.application.usecase.UserInfoUseCase;
import com.swyp.glint.user.domain.UserDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserInfoUseCase userInfoUseCase;

    @Mock
    GetUserUseCase getUserUseCase;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;


    @BeforeEach
    public void beforeEach() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(ServiceExceptionHandler.class)
                .build();

    }


    @Test
    @DisplayName("id를 통한 유저 정보 조회")
    public void getUser() throws Exception {
        UserResponse userResponse = UserResponse.builder()
                .id(1L)
                .email("test@kakao.com")
                .role("ROLE_OAUTH_USER")
                .provider("KAKAO")
                .build();


        Mockito.when(getUserUseCase.getUserBy(1L)).thenReturn(userResponse);

        // 위 UserResponse에 따른 테스트 검증 로직
        mockMvc.perform(get("/users/{userId}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("test@kakao.com"))
                .andExpect(jsonPath("$.role").value("ROLE_OAUTH_USER"))
                .andExpect(jsonPath("$.provider").value("KAKAO"));
    }



    @Test
    @DisplayName("id를 통한 user, userDetail, userProfile 모든 정보 조회")
    public void getUserInfo() throws Exception {
        UserDetailResponse userDetailResponse = UserDetailResponse.builder()
                .id(1L)
                .userId(1L)
                .nickname("nickname")
                .gender("MALE")
                .birthdate("1990-01-01")
                .age(34)
                .height(185)
                .profileImage("profileImage")
                .build();

        UserProfileResponse userProfileResponse = UserProfileResponse.builder()
                .id(1L)
                .work(
                        WorkResponse.builder()
                                .workId(1L)
                                .workName("구글")
                                .workCategory(
                                        WorkCategoryResponse.builder()
                                                .workCategoryId(1L)
                                                .workCategoryName("IT")
                                                .build()
                                )
                                .build()
                )
                .university(
                        UniversityResponse.builder()
                                .universityId(1L)
                                .universityName("서울대학교")
                                .universityDepartment("컴퓨터공학")
                                .universityCategory(
                                        UniversityCategoryResponse.builder()
                                                .universityCategoryId(1L)
                                                .universityCategoryName("공대")
                                                .build()
                                )
                                .build()
                )
                .location(
                        LocationResponse.builder()
                                .locationId(1L)
                                .locationCity("서울")
                                .locationState("서초구")
                                .build()
                )
                .religion(
                        ReligionResponse.builder()
                                .religionId(1L)
                                .religionName("기독교")
                                .build()
                )
                .smoking(
                        SmokingResponse.builder()
                                .smokingId(1L)
                                .smokingName("NO")
                                .build()
                )
                .drinking(
                        DrinkingResponse.builder()
                                .drinkingId(1L)
                                .drinkingName("가끔")
                                .build()
                )
                .build();

        UserInfoResponse userInfoResponse = UserInfoResponse.builder()
                .userId(1L)
                .userDetail(userDetailResponse)
                .userProfile(userProfileResponse)
                .build()
                ;




        Mockito.when(userInfoUseCase.getUserInfoBy(1L)).thenReturn(userInfoResponse);

        mockMvc.perform(get("/users/{userId}/info", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.userDetail.id").value(1L))
                .andExpect(jsonPath("$.userDetail.userId").value(1L))
                .andExpect(jsonPath("$.userDetail.nickname").value("nickname"))
                .andExpect(jsonPath("$.userDetail.gender").value("MALE"))
                .andExpect(jsonPath("$.userDetail.birthdate").value("1990-01-01"))
                .andExpect(jsonPath("$.userDetail.age").value(34))
                .andExpect(jsonPath("$.userDetail.height").value(185))
                .andExpect(jsonPath("$.userDetail.profileImage").value("profileImage"))
                .andExpect(jsonPath("$.userProfile.id").value(1L))
                .andExpect(jsonPath("$.userProfile.work.workId").value(1L))
                .andExpect(jsonPath("$.userProfile.work.workName").value("구글"))
                .andExpect(jsonPath("$.userProfile.work.workCategory.workCategoryId").value(1L))
                .andExpect(jsonPath("$.userProfile.work.workCategory.workCategoryName").value("IT"))
                .andExpect(jsonPath("$.userProfile.university.universityId").value(1L))
                .andExpect(jsonPath("$.userProfile.university.universityName").value("서울대학교"))
                .andExpect(jsonPath("$.userProfile.university.universityDepartment").value("컴퓨터공학"))
                .andExpect(jsonPath("$.userProfile.university.universityCategory.universityCategoryId").value(1L))
                .andExpect(jsonPath("$.userProfile.university.universityCategory.universityCategoryName").value("공대"))
                .andExpect(jsonPath("$.userProfile.location.locationId").value(1L))
                .andExpect(jsonPath("$.userProfile.location.locationCity").value("서울"))
                .andExpect(jsonPath("$.userProfile.location.locationState").value("서초구"))
                .andExpect(jsonPath("$.userProfile.religion.religionId").value(1L))
                .andExpect(jsonPath("$.userProfile.religion.religionName").value("기독교"))
                .andExpect(jsonPath("$.userProfile.smoking.smokingId").value(1L))
                .andExpect(jsonPath("$.userProfile.smoking.smokingName").value("NO"))
                .andExpect(jsonPath("$.userProfile.drinking.drinkingId").value(1L))
                .andExpect(jsonPath("$.userProfile.drinking.drinkingName").value("가끔"));

    }

}
