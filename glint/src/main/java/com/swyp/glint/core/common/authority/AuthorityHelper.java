package com.swyp.glint.core.common.authority;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class AuthorityHelper {

    // 30분
    public final static long ACCESS_TOKEN_VALIDATION_SECOND = 1000L * 60 * 30 ;
    // 14일
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 60 * 24 * 14;

    final static public String ACCESS_TOKEN_NAME = "Authorization";
    final static public String REFRESH_TOKEN_NAME = "RefreshToken";

    @Value("glintlingtlingtglintlingtlingtglintlingtlingtglintlingtlingtglintlingtlingtglintlingtlingtglintlingtlingt")
    private String SECRET_KEY;


    private SecretKey getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parser()
                .verifyWith(getSigningKey(SECRET_KEY))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsername(String token) {
        return extractAllClaims(token).get("username", String.class);
    }

    public String getEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    public Boolean isTokenExpired (String token) {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public String generateToken(String email) {
        return generateToken(email, ACCESS_TOKEN_VALIDATION_SECOND);
    }

    public String generateRefreshToken(String email) {
        return generateToken(email, REFRESH_TOKEN_VALIDATION_SECOND);
    }

    public String generateToken(String email, long expireTime) {
        Claims claims = Jwts.claims()
                .add("email", email)
                .build();

        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(SECRET_KEY))
                .compact();
    }


}
