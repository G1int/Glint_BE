package com.swyp.glint.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyp.glint.core.system.error.ServiceExceptionHandler;
import com.swyp.glint.image.application.ImageService;
import com.swyp.glint.image.application.dto.ImageResponse;
import com.swyp.glint.user.application.dto.UserDetailRequest;
import com.swyp.glint.user.application.dto.UserNickNameRequest;
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

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SqlGroup({
        @Sql(value = "/sql/user/user-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/user/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
})
@SpringBootTest
public class UserDetailControllerTest {

    @Autowired
    UserDetailController userDetailController;

    @MockBean
    ImageService imageService;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;


    @BeforeEach
    public void beforeEach() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(userDetailController)
                .setControllerAdvice(ServiceExceptionHandler.class)
                .build();

    }

    @Test
    @DisplayName("userDetail을 생성 할 수 있다.")
    public void createUserDetail() throws Exception {
        // given
        UserDetailRequest userDetailRequest = UserDetailRequest.of(
                "nickname",
                "MALE",
                "1990-01-01",
                180,
                "profileImage"
        );


        //when
        // then
        mockMvc.perform(post("/users/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDetailRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.nickname").value("nickname"))
                .andExpect(jsonPath("$.birthdate").value("1990-01-01"))
                .andExpect(jsonPath("$.profileImage").value("profileImage"));
    }




    @Test
    @DisplayName("유저 nickname유효성 검사를 할 수 있다.")
    public void userNickNameValidate() throws Exception {
        // given
        String nickname = "nickname";

        //when
        // then
        mockMvc.perform(get("/users/nickname")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("nickname", nickname)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isAvailable").value(true))
                .andExpect(jsonPath("$.nickname").value("nickname"));
    }

    @Test
    @DisplayName("유저 nickname유효성 검사를 할 수 있다. 성공시 임시 UserDetail을 생성하여 반환한다.")
    public void userNickNameValidateTempUserDetail() throws Exception {
        // given

        String nickname = "nickname";
        UserNickNameRequest userNickNameRequest = new UserNickNameRequest(nickname);

        //when
        // then
        mockMvc.perform(post("/users/{userId}/nickname", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userNickNameRequest))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value("nickname"))
                .andExpect(jsonPath("$.gender").doesNotExist())
                .andExpect(jsonPath("$.birthdate").doesNotExist())
                .andExpect(jsonPath("$.age").doesNotExist())
                .andExpect(jsonPath("$.height").doesNotExist())
                .andExpect(jsonPath("$.profileImage").doesNotExist());
    }



    @Test
    @DisplayName("유저 id를 통해 userDetail을 조회 할 수 있다.")
    public void getUserDetail() throws Exception {
        // given
        Long userId = 2L;

        //when
        // then
        mockMvc.perform(get("/users/{userId}/detail", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.userId").value(2L))
                .andExpect(jsonPath("$.nickname").value("test"))
                .andExpect(jsonPath("$.birthdate").value("1990-01-01"))
                .andExpect(jsonPath("$.profileImage").value("test.jpg"))
                .andExpect(jsonPath("$.gender").value("MALE"))
                .andExpect(jsonPath("$.age").value(34))
                .andExpect(jsonPath("$.height").value(180));
    }


    @Test
    @DisplayName("유저 id의 userDetail가 존재하지 않을경우 404를 반환한다.")
    public void getUserDetailFail() throws Exception {
        // given
        Long userId = 3L;

        //when
        // then
        mockMvc.perform(get("/users/{userId}/detail", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("UserDetail with userId: 3 not found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.code").value("C003"))
        ;
    }



    @Test
    @DisplayName("유저프로필 이미지를 수정할 수 있다.")
    public void updateUserProfileImage() throws Exception {
        // given
        Long userId = 2L;
        MultipartFile profileImageFile = new MockMultipartFile("updatedProfileImage", "updatedProfileImage.jpg", "image/jpeg", "test".getBytes());
        when(imageService.uploadProfileImageFile(any())).thenReturn(new ImageResponse("updatedProfileImage", "updatedProfileImage.jpg"));

        //when
        // then
        mockMvc.perform(multipart(HttpMethod.PUT,"/users/{userId}/profile-image", userId)
                        .file("imageFile", profileImageFile.getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.userId").value(2L))
                .andExpect(jsonPath("$.nickname").value("test"))
                .andExpect(jsonPath("$.birthdate").value("1990-01-01"))
                .andExpect(jsonPath("$.profileImage").value("updatedProfileImage.jpg"))
                .andExpect(jsonPath("$.gender").value("MALE"))
                .andExpect(jsonPath("$.age").value(34))
                .andExpect(jsonPath("$.height").value(180));
    }


}
