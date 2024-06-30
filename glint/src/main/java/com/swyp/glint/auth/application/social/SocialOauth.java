package com.swyp.glint.auth.application.social;


import com.swyp.glint.auth.api.SocialType;
import com.swyp.glint.auth.api.response.OauthTokenResponse;

public interface SocialOauth {

    String getOauthRedirectURL();

    OauthTokenResponse requestAccessToken(String code);

    default SocialType type(){
        if (this instanceof GoogleOath) {
            return SocialType.GOOGLE;
        }if (this instanceof KakaoOauth) {
            return SocialType.KAKAO;
        } else {
            return null;
        }
    }

}
