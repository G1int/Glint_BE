package com.swyp.glint.core.common.util;

import com.swyp.glint.core.common.authority.AuthorityHelper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    public Cookie createAccessTokenCookie( String value){
        Cookie cookie = new Cookie(AuthorityHelper.ACCESS_TOKEN_NAME, value);
        cookie.setHttpOnly(false);
        cookie.setMaxAge((int) AuthorityHelper.ACCESS_TOKEN_VALIDATION_SECOND);
        cookie.setPath("/");

        return cookie;
    }

    public Cookie createRefreshTokenCookie(String value){
        Cookie cookie = new Cookie(AuthorityHelper.REFRESH_TOKEN_NAME, value);
        cookie.setHttpOnly(false);
        cookie.setMaxAge((int) AuthorityHelper.REFRESH_TOKEN_VALIDATION_SECOND);
        cookie.setPath("/");

        return cookie;
    }

    public Cookie getCookie(HttpServletRequest req, String cookieName){
        Cookie[] cookies = req.getCookies();

        if(cookies == null) return null;

        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieName)){
                return cookie;
            }
        }

        return null;
    }

}
