package com.github.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * before(前置通知)： 在方法开始执行前执行
 * after(后置通知)： 在方法执行后执行
 * afterReturning(返回后通知)： 在方法返回后执行
 * afterThrowing(异常通知)： 在抛出异常时执行
 * around(环绕通知)： 在方法执行前和执行后都会执行
 */
@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    //1.定义切点
    @Pointcut(value = "@annotation(com.github.annotation.Log)")//切入点描述 这个是controller包的切入点
    public void pointcut(){}//签名，可以理解成这个切入点的一个名称

    @Before("pointcut()")
    public void beginTransaction() {
        System.out.println("before beginTransaction");
    }

    @After("pointcut()")
    public void commit() {
        System.out.println("after commit");
    }

    @AfterReturning(value = "pointcut()", returning = "returnObject")
    public void afterReturning(JoinPoint joinPoint, Object returnObject) {
        System.out.println("afterReturning");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        System.out.println("afterThrowing afterThrowing  rollback");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            System.out.println("around");
            return joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        } finally {
            System.out.println("around");
        }
    }
}
