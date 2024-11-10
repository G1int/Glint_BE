package com.swyp.glint.core.auth.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OauthUserInfoResponse {
    private BigInteger id;
    private String email;
    private Boolean verified_email;
    private String picture;
}
