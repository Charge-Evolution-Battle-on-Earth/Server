package com.project.game.common.interceptor;

import static com.project.game.common.util.JwtUtil.isExpired;

import com.project.game.user.service.UserService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
@RequiredArgsConstructor
public class WebSocketInterceptor implements HandshakeInterceptor {

    private final UserService userService;

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
        WebSocketHandler wsHandler, Map<String, Object> attributes) throws RuntimeException {
        List<String> authenticationHeaders = request.getHeaders().get("Authorization");

        if (authenticationHeaders != null && !authenticationHeaders.isEmpty()) {
            String authorization = authenticationHeaders.get(0);

            if (authorization == null || !authorization.startsWith("Bearer ")) {
                throw new IllegalStateException();
            }

            String token = authorization.split(" ")[1];

            if (isExpired(token, secretKey)) {
                throw new IllegalStateException();
            }

            Long characterId = userService.getCharacterIdByToken(token, secretKey);
            attributes.put("characterId", characterId);
            return true;
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
        WebSocketHandler wsHandler, Exception exception) {
    }
}