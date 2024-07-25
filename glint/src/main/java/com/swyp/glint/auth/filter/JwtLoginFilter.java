package com.swyp.glint.auth.filter;

import com.swyp.glint.common.authority.AuthorityHelper;
import com.swyp.glint.common.cache.RemoteCache;
import com.swyp.glint.common.util.CookieUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtLoginFilter extends OncePerRequestFilter {

    private final AuthorityHelper authorityHelper;

    private final RemoteCache redisUtil;

    private final UserDetailsService userPrincipalDetailsService;

    private final CookieUtil cookieUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        Cookie accessToken = cookieUtil.getCookie(request, AuthorityHelper.ACCESS_TOKEN_NAME);
        String accessToken = request.getHeader("Authorization");
        String refreshToken = null;

        String email = null;

        try{
            if(accessToken != null){
                email = authorityHelper.getEmail(accessToken.replace("Bearer ", ""));
            }
            if(email != null) {
                UserDetails userDetails = userPrincipalDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =  new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

//                refreshTokenCookie(response, email);
                refreshTokenHeader(response, email);

            }

        } catch (ExpiredJwtException e){
//            Cookie refreshTokenCookie = cookieUtil.getCookie(request,AuthorityHelper.REFRESH_TOKEN_NAME);
            refreshToken = request.getHeader("RefreshToken");
//            if(refreshTokenCookie != null){
//                refreshToken = refreshTokenCookie.getValue();
//            }
        } catch (Exception e){
            logger.error("error" + e.getMessage());
        }

        try{
            if(refreshToken != null) {
                email = authorityHelper.getEmail(refreshToken.replace("Bearer ", ""));

                UserDetails userDetails = userPrincipalDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =  new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

//                refreshTokenCookie(response, email);
                refreshTokenHeader(response, email);
            }
        } catch (ExpiredJwtException ignored){
            logger.error("error");
        } catch (Exception e){
            logger.error("error" + e.getMessage());
        }


        filterChain.doFilter(request,response);

    }


    private void refreshTokenHeader(HttpServletResponse response, String email) {
        String generateAccessToken = authorityHelper.generateToken(email);
        String generateRefreshToken = authorityHelper.generateToken(email);

        response.setHeader("Authorization", "Bearer "  + generateAccessToken);
        response.setHeader("RefreshToken", "Bearer "  + generateRefreshToken);

        redisUtil.setDataExpire(email, generateAccessToken, AuthorityHelper.ACCESS_TOKEN_VALIDATION_SECOND);
        redisUtil.setDataExpire(email, generateRefreshToken, AuthorityHelper.REFRESH_TOKEN_VALIDATION_SECOND);
    }

    private void refreshTokenCookie(HttpServletResponse response, String email) {
        String generateAccessToken = authorityHelper.generateToken(email);
        String generateRefreshToken = authorityHelper.generateToken(email);

        Cookie tokenCookie = cookieUtil.createAccessTokenCookie(generateAccessToken);
        Cookie refreshTokenCookie = cookieUtil.createRefreshTokenCookie(generateRefreshToken);

        response.addCookie(tokenCookie);
        response.addCookie(refreshTokenCookie);

        redisUtil.setDataExpire(email, generateAccessToken, AuthorityHelper.ACCESS_TOKEN_VALIDATION_SECOND);
        redisUtil.setDataExpire(email, generateRefreshToken, AuthorityHelper.REFRESH_TOKEN_VALIDATION_SECOND);
    }


}
