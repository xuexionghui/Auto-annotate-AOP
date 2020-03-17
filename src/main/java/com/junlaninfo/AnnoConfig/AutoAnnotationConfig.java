package com.junlaninfo.AnnoConfig;

import com.alibaba.fastjson.JSON;
import com.junlaninfo.annotation.LogAutoannotation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * Created by 辉 on 2020/3/17.
 */
@Aspect
@Component
public class AutoAnnotationConfig {
    private Log log = LogFactory.getLog(AutoAnnotationConfig.class);

    /*
      定义切点
      切到标记注解的方法
     */
    @Pointcut("@annotation(com.junlaninfo.annotation.LogAutoannotation)")
    public void LogAnnotation() {
    }

    @Around("LogAnnotation()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //定义开始的时间
        long begin = System.currentTimeMillis();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        Signature signature = point.getSignature();
        MethodSignature signature1 = (MethodSignature) signature;
        Method method = signature1.getMethod();
        LogAutoannotation annotation = method.getAnnotation(LogAutoannotation.class);
        //接口描述信息
        String desc = annotation.desc();
        log.info("------------请求开始---------");
        log.info("接口描述信息"+desc);
        log.info("请求ip： "+request.getRemoteAddr());
        log.info("请求参数是:"+JSON.toJSONString(point.getArgs()));
        log.info("请求的方法类型："+request.getMethod());
        Object proceed = point.proceed(); //得到方法的返回结果，结果是json的
        long end=System.currentTimeMillis();
        log.info("请求耗时："+(end-begin));
        log.info("请求的返回结果："+ JSON.toJSONString(proceed));
        return proceed;
    }
}
