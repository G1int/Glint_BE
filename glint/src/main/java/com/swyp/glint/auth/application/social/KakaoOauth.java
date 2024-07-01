package com.swyp.glint.auth.application.social;

import com.swyp.glint.auth.api.response.KakaoUserInfoResponse;
import com.swyp.glint.auth.api.response.OauthTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class KakaoOauth implements SocialOauth {


    private String REQUEST_URL = "https://kauth.kakao.com/oauth/authorize";
    private String USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
    private final String TOKEN_BASE_URL = "https://kauth.kakao.com/oauth/token";

    @Value("${spring.oauth.kakao.client-id}")
    private String CLIENT_ID;

    @Value("${spring.oauth.kakao.callback-url}")
    private String CALLBACK_URL = "http://localhost:8080/glint/auth/kakao/callback";


    @Override
    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("client_id", CLIENT_ID);
        params.put("redirect_uri", CALLBACK_URL);
        params.put("cide", "");

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return REQUEST_URL + "?" + parameterString;
    }

    @Override
    public OauthTokenResponse requestAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", CLIENT_ID);
        params.add("redirect_uri", CALLBACK_URL);
        params.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept", "application/json");


        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<OauthTokenResponse> responseEntity = restTemplate.postForEntity(TOKEN_BASE_URL, request, OauthTokenResponse.class);


        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        }
        return new OauthTokenResponse();
    }

    public KakaoUserInfoResponse getUserInfo(String token){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity request = new HttpEntity<>(headers);

        ResponseEntity<KakaoUserInfoResponse> responseEntity = restTemplate.exchange(USER_INFO_URL, HttpMethod.GET, request, KakaoUserInfoResponse.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            return responseEntity.getBody();
        }

        return null;
    }
}
