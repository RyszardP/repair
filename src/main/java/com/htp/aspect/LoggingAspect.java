package com.htp.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger LOGGER = Logger.getLogger(LoggingAspect.class);

    @Before("execution(public * *(..))")
    public void logBefore(JoinPoint joinPoint) {

        System.out.println("logBefore() is running!");
        System.out.println("Method " + joinPoint.getSignature().getName() + " start");
    }

    @AfterReturning(pointcut = "execution(public * *(..))")
    public void doAccessCheck(JoinPoint joinPoint) {


        System.out.println("logAfterReturning() is running!");
        System.out.println("Method " + joinPoint.getSignature().getName() + " finished");
    }


    @Pointcut("execution(* com.htp.domain.*.*(..))")
    public void domainEntitiesMethods() {
    }

    @Around("domainEntitiesMethods()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch sw = new StopWatch();
        sw.start();
        LOGGER.info("Going to call the method.");
        Object output = pjp.proceed();
        LOGGER.info("Method execution completed.");
        sw.stop();
        LOGGER.info("Method execution time: " + sw.getTotalTimeMillis() + " milliseconds.");
        return output;
    }


}