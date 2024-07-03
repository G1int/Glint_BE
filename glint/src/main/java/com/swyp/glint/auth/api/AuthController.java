package com.swyp.glint.auth.api;

import com.swyp.glint.auth.api.response.KakaoUserInfoResponse;
import com.swyp.glint.auth.api.response.OauthTokenResponse;
import com.swyp.glint.auth.application.social.KakaoOauth;
import com.swyp.glint.auth.application.social.SocialOauth;
import com.swyp.glint.auth.application.social.SocialOauthProvider;
import com.swyp.glint.auth.application.social.SocialType;
import com.swyp.glint.user.application.UserService;
import com.swyp.glint.user.application.dto.UserLoginResponse;
import com.swyp.glint.user.application.dto.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final SocialOauthProvider socialOauthProvider;

    @Operation(hidden = true)
    @GetMapping(value = "/auth/{socialType}")
    @ResponseStatus(HttpStatus.OK)
    public void socialAuth(@PathVariable SocialType socialType, String code, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        SocialOauth socialOauth = socialOauthProvider.getSocialOauth(socialType);

        try {
            httpServletResponse.sendRedirect(socialOauth.getOauthRedirectURL());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    @Operation(hidden = true)
    @GetMapping("/auth/{socialType}/callback")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserLoginResponse> socialAuthCallBack(@PathVariable SocialType socialType, @RequestParam(name = "code") String code, HttpServletRequest request, HttpServletResponse response) {
        KakaoOauth socialOauth = (KakaoOauth) socialOauthProvider.getSocialOauth(socialType);
        OauthTokenResponse oauthTokenResponse = socialOauth.requestAccessToken(code);
        KakaoUserInfoResponse kakaoUserInfoResponse = socialOauth.getUserInfo(oauthTokenResponse.getAccess_token());

        UserLoginResponse userLoginResponse = userService.oauthLoginUser(
                UserRequest.of(kakaoUserInfoResponse.getKakao_account().getEmail(),"ROLE_OAUTH_KAKAO", SocialType.KAKAO.name())
        );

        return ResponseEntity.ok(userLoginResponse);

    }

}
