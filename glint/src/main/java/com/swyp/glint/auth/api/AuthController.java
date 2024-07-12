package com.swyp.glint.auth.api;

import com.swyp.glint.auth.api.response.KakaoUserInfoResponse;
import com.swyp.glint.auth.api.response.OauthTokenResponse;
import com.swyp.glint.auth.application.social.KakaoOauth;
import com.swyp.glint.auth.application.social.SocialOauthProvider;
import com.swyp.glint.auth.application.social.SocialType;
import com.swyp.glint.common.authority.AuthorityHelper;
import com.swyp.glint.common.util.RedisUtil;
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

    private final AuthorityHelper authorityHelper;

    private final RedisUtil redisUtil;

    @Operation(hidden = true)
    @GetMapping(value = "/auth/{socialType}")
    @ResponseStatus(HttpStatus.OK)
    public void socialAuth(@PathVariable SocialType socialType, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        KakaoOauth socialOauth = (KakaoOauth) socialOauthProvider.getSocialOauth(socialType);

        try {
            httpServletResponse.sendRedirect(socialOauth.getOauthRedirectURL());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }


    @GetMapping("/auth/{socialType}/callback")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserLoginResponse> socialAuthCallBack(@PathVariable SocialType socialType, @RequestParam(name = "code") String code, HttpServletRequest request, HttpServletResponse response) {
        KakaoOauth socialOauth = (KakaoOauth) socialOauthProvider.getSocialOauth(socialType);

        OauthTokenResponse oauthTokenResponse = socialOauth.requestAccessToken(code);
        KakaoUserInfoResponse kakaoUserInfoResponse = socialOauth.getUserInfo(oauthTokenResponse.getAccess_token());

        UserLoginResponse userLoginResponse = userService.oauthLoginUser(
                UserRequest.of(kakaoUserInfoResponse.getKakao_account().getEmail(),"OAUTH_USER", SocialType.KAKAO.name())
        );

        refreshTokenHeader(response, userLoginResponse.email());

        return ResponseEntity.ok(userLoginResponse);

    }

    private void refreshTokenHeader(HttpServletResponse response, String email) {
        String generateAccessToken = authorityHelper.generateToken(email);
        String generateRefreshToken = authorityHelper.generateToken(email);

        response.setHeader("Authorization", "bearer "  + generateAccessToken);
        response.setHeader("refreshToken", "bearer "  + generateRefreshToken);

        redisUtil.setDataExpire(email, generateAccessToken, AuthorityHelper.ACCESS_TOKEN_VALIDATION_SECOND);
        redisUtil.setDataExpire(email, generateRefreshToken, AuthorityHelper.REFRESH_TOKEN_VALIDATION_SECOND);
    }

}
