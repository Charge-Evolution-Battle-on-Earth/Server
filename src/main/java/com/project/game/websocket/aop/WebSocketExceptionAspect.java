package com.project.game.websocket.aop;

import com.project.game.common.util.JsonUtil;
import com.project.game.websocket.dto.ErrorMassageResponse;
import com.project.game.websocket.exception.WebSocketException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Spring Framework HttpRequest 생명주기가 적용되지 않는 WebSocket 연결 중 예외 발생 시 WebSocket 연결이 종료되는 것을 방지하기 위한
 * AOP 기반의 예외 처리
 */
@Aspect
@Component
@RequiredArgsConstructor
public class WebSocketExceptionAspect {

    private final JsonUtil jsonUtil;

    @Around("execution(* com.project.game.play.controller.PlayController.*(..))")
    public Object handleExceptionAndLog(ProceedingJoinPoint joinPoint) throws IOException {
        try {
            return joinPoint.proceed();
        } catch (WebSocketException we) {
            ErrorMassageResponse response = new ErrorMassageResponse(we.getMessage());
            return jsonUtil.toJson(response);
        } catch (Throwable e) {
            ErrorMassageResponse response = new ErrorMassageResponse(e.getMessage());
            return jsonUtil.toJson(response);
        }
    }
}