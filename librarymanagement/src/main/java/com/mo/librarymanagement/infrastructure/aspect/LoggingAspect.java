package com.mo.librarymanagement.infrastructure.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Pointcut for all service layer methods
    @Pointcut("within(com.example.library.services..*)")
    public void serviceLayer() {}

    // Pointcut for all controller layer methods
    @Pointcut("within(com.example.library.controllers..*)")
    public void controllerLayer() {}

    // Before advice for controller methods
    @Before("controllerLayer()")
    public void logBeforeController(JoinPoint joinPoint) {
        logger.info("Entering Controller: {}.{}() with arguments = {}", 
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(),
            joinPoint.getArgs());
    }

    // After Returning advice for controller methods
    @AfterReturning(pointcut = "controllerLayer()", returning = "result")
    public void logAfterReturningController(JoinPoint joinPoint, Object result) {
        logger.info("Exiting Controller: {}.{}() with result = {}", 
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(),
            result);
    }

    // After Throwing advice for controller methods
    @AfterThrowing(pointcut = "controllerLayer()", throwing = "exception")
    public void logAfterThrowingController(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in Controller: {}.{}() with cause = {}", 
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(),
            exception.getCause() != null ? exception.getCause() : "NULL");
    }

    // Around advice for service methods to log execution time
    @Around("serviceLayer()")
    public Object logAroundService(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        try {
            logger.info("Entering Service: {}.{}() with arguments = {}", 
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());

            Object result = joinPoint.proceed();

            long elapsedTime = System.currentTimeMillis() - start;
            logger.info("Exiting Service: {}.{}() with result = {}. Execution time: {} ms", 
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                result,
                elapsedTime);

            return result;
        } catch (Throwable e) {
            logger.error("Exception in Service: {}.{}() with cause = {}", 
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                e.getCause() != null ? e.getCause() : "NULL");
            throw e;
        }
    }
}