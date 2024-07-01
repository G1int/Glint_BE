package com.swyp.glint.auth.application.social;

import com.swyp.glint.auth.api.response.OauthTokenResponse;
import org.springframework.stereotype.Component;

@Component
public class GoogleOath implements SocialOauth {
    @Override
    public String getOauthRedirectURL() {
        return "";
    }

    @Override
    public OauthTokenResponse requestAccessToken(String code) {
        return null;
    }
}
