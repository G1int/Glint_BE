package com.swyp.glint.auth.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OauthTokenResponse {

    private String access_token;
    private String expires_in;
    private String scope;
    private String token_type;
    private String id_token;
    private String refresh_token;
    private String refresh_token_expires_in;

}
