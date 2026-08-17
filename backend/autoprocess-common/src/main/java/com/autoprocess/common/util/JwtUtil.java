package com.autoprocess.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.time.Instant;


/**
 * JWT 生成与解析工具，登录后签发凭证、每次请求校验签名和过期时间。
 */
@Component
public class JwtUtil {
    private final SecretKey key;
    private final long expireSeconds;

    public JwtUtil(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.expire-seconds:7200}") long expireSeconds){
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expireSeconds = expireSeconds;
    }

    /** 生成 JWT，subject 为用户 ID，附带 username 和 role 两个 claim。 */
    public String generate(String userId,String username,String role){
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(userId)
                .claim("username",username)
                .claim("role",role)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expireSeconds)))
                .signWith(key)
                .compact();
    }
    /** 解析 JWT，校验签名和过期时间，非法或过期抛 JwtException。 */
    public Claims parse(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
