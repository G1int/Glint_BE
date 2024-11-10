package com.swyp.glint.core.auth.application.social;

import com.swyp.glint.core.common.exception.ErrorCode;
import com.swyp.glint.core.common.exception.InvalidValueException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SocialOauthProvider {

    private final GoogleOath googleOath;

    private final KakaoOauth kakaoOauth;

    public SocialOauth getSocialOauth(SocialType socialType){

        switch (socialType){
            case GOOGLE : {
                return googleOath;
            }
            case KAKAO : {
                return kakaoOauth;
            }
            default : {
                throw new InvalidValueException(ErrorCode.INVALID_SOCIAL_TYPE);
            }
        }

    }

}
