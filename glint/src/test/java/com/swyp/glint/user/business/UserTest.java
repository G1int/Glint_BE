package com.swyp.glint.user.business;

import com.swyp.glint.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {


    @Test
    @DisplayName("유저 탈퇴 여부를 true로 변경할 수 있다.")
    public void archiveUser() {
        //given
        User user = User.createNewUser(
                "test@kakao.com",
                "ROLE_OAUTH_USER",
                "KAKAO"
        );
        //when
        user.archive();

        //then

        Assertions.assertThat(user.isArchived()).isTrue();
    }


}
