package com.swyp.glint.core.system.logtrace;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Slf4j
@RequiredArgsConstructor
public class LoggingAspect {

    private final LogTrace logTrace;

    @Around("com.swyp.glint.core.system.logtrace.PointCuts.all()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus traceStatus = null;

        try {
            String message = joinPoint.getSignature().toShortString();

            traceStatus = logTrace.begin(message);

            Object result = joinPoint.proceed();

            logTrace.end(traceStatus);

            return result;

        } catch(Exception e) {
            logTrace.exception(traceStatus, e);
            throw e;
        }
    }


}
