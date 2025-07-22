package org.finmate.security.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Log4j2
@Component
public class JwtProcessor {

    //토큰 유효 시간 (7일)
    private static final long TOKEN_VALID_MILLISECONDS = 1000L * 60L * 60L * 24L & 7L;

    // 테스트용 임시 secret key
    @Value("${jwt.secret}")
    private String secret;
    private final Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    // private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 운영시 사용

    //JWT 생성
    public String generateToken(String subject){
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALID_MILLISECONDS))
                .signWith(key)
                .compact();
    }

    // JWT에서 username(subject) 추출
    public String getUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // JWT 유효기간 검증 (유효성)
    public boolean validateToken(String token){
        try{
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }
        catch(ExpiredJwtException e){
            log.warn("JWT 토큰이 만료되었습니다.");
        }
        catch(UnsupportedJwtException e){
            log.warn("지원하지 않는 JWT 형식입니다.");
        }
        catch(MalformedJwtException e){
            log.warn("잘못된 JWT 형식입니다.");
        }
        catch(SignatureException e){
            log.warn("JWT 서명이 유효하지 않습니다.");
        }
        catch(IllegalArgumentException e){
            log.warn("JWT 토큰이 비어있습니다.");
        }
        return false;
    }

}
