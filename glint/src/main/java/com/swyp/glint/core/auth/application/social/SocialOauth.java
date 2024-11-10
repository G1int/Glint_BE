package com.swyp.glint.core.auth.application.social;


import com.swyp.glint.core.auth.api.response.OauthTokenResponse;

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
