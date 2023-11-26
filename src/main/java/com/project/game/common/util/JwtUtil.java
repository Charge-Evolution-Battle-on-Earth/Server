package com.project.game.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class JwtUtil {

    public static String createToken(Claims claims, String secretKey, Long expiredMs){
        long now = new Date().getTime();
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(now + expiredMs))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .setHeaderParam("typ", "JWT")
            .compact();
    }

    public static Boolean isExpired(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            .getBody().getExpiration().before(new Date());
    }

    public static Claims parse(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey)
            .parseClaimsJws(token).getBody();
    }
}
