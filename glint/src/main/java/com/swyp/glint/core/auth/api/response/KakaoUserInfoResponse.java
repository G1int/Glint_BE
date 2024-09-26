package com.swyp.glint.core.auth.api.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class KakaoUserInfoResponse {
    private Long id;
    private String connected_at;
    private KaKaoAccountResponse kakao_account;
}
