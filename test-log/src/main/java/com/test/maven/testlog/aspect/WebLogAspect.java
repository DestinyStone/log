package com.test.maven.testlog.aspect;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.test.maven.testlog.api.WebLog;
import com.test.maven.testlog.util.RequestUtil;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author: zhouxiaofeng
 * @create: 2021-04-12 17:17
 * @description:
 **/
@Aspect
@Component
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * com.test.maven.*.controller.*Controller.*(..))")
    public void pointcut(){}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        WebLog webLog = new WebLog();
        try {
            beforeSetParam(webLog, joinPoint);
            result = joinPoint.proceed();
            afterSetParam(webLog, result, null);
        } catch (Throwable throwable) {
            afterSetParam(webLog, null, throwable);
            throw throwable;
        } finally {
            logger.info(JSONObject.toJSONString(webLog));
        }
        return result;
    }
    /**
     * 获取当前请求
     * @return
     */
    public HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }

    /**
     * 连接点触发之前调用
     * @param webLog
     */
    public void beforeSetParam(WebLog webLog, ProceedingJoinPoint joinPoint) {
        HttpServletRequest currentRequest = this.getCurrentRequest();
        webLog.setUrl(currentRequest.getRequestURL().toString());
        webLog.setBasePath(currentRequest.getServletPath());
        webLog.setUri(currentRequest.getRequestURI());
        webLog.setType(currentRequest.getMethod());
        webLog.setParams(this.getParams(joinPoint));
        webLog.setDescription(this.getDescription(joinPoint));
        webLog.setStartTime(new Date());
        webLog.setIp(RequestUtil.getRequestIp(currentRequest));
    }

    /**
     * 获取参数
     * @return
     */
    public List<Map<String, Object>> getParams(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        if (args.length == 0) return null;

        Class[] parameterTypes = methodSignature.getParameterTypes();
        List<Map<String, Object>> params = new ArrayList<>();
        for (int i = 0; i<args.length; i++) {
            HashMap<String, Object> param = new HashMap<>(2);
            param.put(parameterTypes[i].getName(), args[i]);
            params.add(param);
        }
        return params;
    }

    /**
     * 连接点触发之后调用
     * @param webLog
     */
    public void afterSetParam(WebLog webLog, Object result, Throwable e) {
        Date date = new Date();
        webLog.setEndTime(date);
        webLog.setProcessTime(new Date(date.getTime() - webLog.getStartTime().getTime()));

        if (result != null) {
            webLog.setResult(result);
            webLog.setSuccessResult(true);
            return;
        }

        if (e != null) {
            HashMap<String, Object> eMap = new HashMap<>();
            eMap.put(e.getClass().getName(), e);
            webLog.setException(eMap);
            webLog.setSuccessResult(false);
        } else {
            webLog.setSuccessResult(true);
        }
    }

    /**
     * 获取controller描述信息
     * @param joinPoint
     * @return
     */
    public String getDescription(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);
        if (annotation == null) return "";
        return StrUtil.isNotBlank(annotation.value()) ? annotation.value() : "";
    }
}
    