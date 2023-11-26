package com.project.game.common.config;

import static com.project.game.common.util.JwtUtil.isExpired;

import com.google.common.net.HttpHeaders;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Value("${jwt.secret")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }

        String token = authorization.split(" ")[1];

        if(isExpired(token, secretKey)){
            filterChain.doFilter(request,response);
            return;
        }
        
        //TODO Request 동안 유지되는 userId 세팅 로직
    }
}
