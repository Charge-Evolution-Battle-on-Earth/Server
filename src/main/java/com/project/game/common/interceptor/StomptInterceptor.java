package com.project.game.common.interceptor;

import static com.project.game.common.util.JwtUtil.isExpired;

import com.project.game.user.service.UserService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;

//TODO 추후 STOMP 관련 코드 제거

@Configuration
@RequiredArgsConstructor
public class StomptInterceptor implements ChannelInterceptor {

    private final UserService userService;

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        // 메시지의 구독 명령이 CONNECT인 경우에만 실행
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

            List<String> authenticationHeaders = headerAccessor.getNativeHeader("Authorization");

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

                Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
                sessionAttributes.put("characterId", characterId);
                headerAccessor.setSessionAttributes(sessionAttributes);
            }
        }

        message = MessageBuilder.createMessage(message.getPayload(), accessor.toMessageHeaders());
        ;

        return message;
    }
}
