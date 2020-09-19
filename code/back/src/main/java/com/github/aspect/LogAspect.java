package com.github.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

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
    @Pointcut(value = "@annotation(log)")//切入点描述 这个是controller包的切入点
    public void pointcut(){}//签名，可以理解成这个切入点的一个名称

    @Before("pointcut()") //在切入点的方法run之前要干的
    public void logBeforeController(JoinPoint joinPoint) {


        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();//这个RequestContextHolder是Springmvc提供来获得请求的东西
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        // 记录下请求内容
        logger.info("################URL : " + request.getRequestURL().toString());
        logger.info("################HTTP_METHOD : " + request.getMethod());
        logger.info("################IP : " + request.getRemoteAddr());
        logger.info("################THE ARGS OF THE CONTROLLER : " + Arrays.toString(joinPoint.getArgs()));

        //下面这个getSignature().getDeclaringTypeName()是获取包+类名的   然后后面的joinPoint.getSignature.getName()获取了方法名
        logger.info("################CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //logger.info("################TARGET: " + joinPoint.getTarget());//返回的是需要加强的目标类的对象
        //logger.info("################THIS: " + joinPoint.getThis());//返回的是经过加强后的代理类的对象
    }
}
