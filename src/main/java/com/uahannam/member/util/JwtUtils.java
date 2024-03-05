package com.uahannam.member.util;

import com.uahannam.member.config.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtProperties jwtProperties;

    public String createJwtToken(Long userId) {

        long period = 1000L * 60L * 60L * 24 * 365;
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + period))
                .signWith(new SecretKeySpec(jwtProperties.getSecretKey().getBytes(), SignatureAlgorithm.HS256.getJcaName()))
                .compact();
    }
}
