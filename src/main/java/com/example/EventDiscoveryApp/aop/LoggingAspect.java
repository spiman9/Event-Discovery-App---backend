package com.example.EventDiscoveryApp.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.example.EventDiscoveryApp.service.*.*(..))") // for all the method sin the service class
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        //runs before the method is executed
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.info("{} | {} - started", className, methodName);
        // executes the method
        Object result = joinPoint.proceed();

        //after executing the method
        log.info("{} | {} - completed", className, methodName);

        return result;
    }
}
