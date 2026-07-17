package com.backendspring.aspect;

import com.backendspring.lib.LoggingServiceUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    @Pointcut("execution(* com.backendspring.service.impl.ManagerServiceImpl.createManager(..))")
    public void createManagerOp() {
    }

    @Pointcut("execution(* com.backendspring.service.impl.ManagerServiceImpl.*(..))")
    public void anyManagerOp() {
    }
    @Before("createManagerOp()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("1. @Before         -> about to run " + joinPoint.getSignature().getName());
    }

    @AfterReturning(value = "createManagerOp()", returning = "result")
    public void logAfterReturning(Object result) {
        System.out.println("2. @AfterReturning  -> success, returned: " + result);
    }

    @AfterThrowing(value = "createManagerOp()", throwing = "ex")
    public void logAfterThrowing(Exception ex) {
        System.out.println("3. @AfterThrowing   -> failed: " + ex.getClass().getSimpleName() + " - " + ex.getMessage());
    }

    @After("createManagerOp()")
    public void logAfter() {
        System.out.println("4. @After          -> method finished (success or failure)");
    }

    @Around("createManagerOp()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("5. @Around (start)  -> " + joinPoint.getSignature().getName());
        try {
            Object result = joinPoint.proceed();
            System.out.println("5. @Around (end)    -> completed successfully");
            return result;
        } catch (Throwable ex) {
            System.out.println("5. @Around (end)    -> threw " + ex.getMessage());
            throw ex;
        }
    }

    @Around("anyManagerOp()")
    public Object logAroundManagerService(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        LoggingServiceUtil.logStart(className, methodName);
        try {
            Object result = joinPoint.proceed();
            LoggingServiceUtil.logEnd(className, methodName);
            return result;
        } catch (Throwable ex) {
            System.out.println("Failed -> " + className + " : " + methodName + " : " + ex.getMessage());
            throw ex;
        }
    }
}
