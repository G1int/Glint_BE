package com.swyp.glint.core.auth.api;

import com.swyp.glint.core.auth.api.response.KakaoUserInfoResponse;
import com.swyp.glint.core.auth.api.response.OauthTokenResponse;
import com.swyp.glint.core.auth.application.social.KakaoOauth;
import com.swyp.glint.core.auth.application.social.SocialOauthProvider;
import com.swyp.glint.core.auth.application.social.SocialType;
import com.swyp.glint.core.common.authority.AuthorityHelper;
import com.swyp.glint.core.common.util.CookieUtil;
import com.swyp.glint.core.common.util.RedisUtil;
import com.swyp.glint.user.application.impl.UserServiceImpl;
import com.swyp.glint.user.application.dto.UserLoginResponse;
import com.swyp.glint.user.application.dto.UserRequest;
import jakarta.servlet.http.Cookie;
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

    private final UserServiceImpl userServiceImpl;

    private final SocialOauthProvider socialOauthProvider;

    private final AuthorityHelper authorityHelper;

    private final RedisUtil redisUtil;

    private final CookieUtil cookieUtil;

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

        UserLoginResponse userLoginResponse = userServiceImpl.oauthLoginUser(
                UserRequest.of(kakaoUserInfoResponse.getKakao_account().getEmail(),"ROLE_OAUTH_USER", SocialType.KAKAO.name())
        );

        refreshTokenHeader(response, userLoginResponse.email());
        return ResponseEntity.ok(userLoginResponse);

    }

    @PutMapping("/auth/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = request.getHeader("Authorization");
        String refreshToken = request.getHeader("RefreshToken");

        response.setHeader("Authorization", "");
        response.setHeader("RefreshToken", "");

        String email = authorityHelper.getEmail(refreshToken.replace("Bearer ", ""));
        redisUtil.deleteData(email);
        return ResponseEntity.ok().build();
    }



    private void refreshTokenHeader(HttpServletResponse response, String email) {
        String generateAccessToken = authorityHelper.generateToken(email);
        String generateRefreshToken = authorityHelper.generateToken(email);

        response.setHeader("Authorization", "Bearer "  + generateAccessToken);
        response.setHeader("RefreshToken", "Bearer "  + generateRefreshToken);

        redisUtil.setDataExpire(email, generateRefreshToken, AuthorityHelper.REFRESH_TOKEN_VALIDATION_SECOND);
    }

    private void refreshTokenCookie(HttpServletResponse response, String email) {
        String generateAccessToken = authorityHelper.generateToken(email);
        String generateRefreshToken = authorityHelper.generateToken(email);

        Cookie tokenCookie = cookieUtil.createAccessTokenCookie(generateAccessToken);
        Cookie refreshTokenCookie = cookieUtil.createRefreshTokenCookie(generateRefreshToken);

        response.addCookie(tokenCookie);
        response.addCookie(refreshTokenCookie);

        redisUtil.setDataExpire(email, generateRefreshToken, AuthorityHelper.REFRESH_TOKEN_VALIDATION_SECOND);
    }

}
