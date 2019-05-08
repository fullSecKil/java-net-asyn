package com.example.aopreflex.aspect;

import lombok.extern.jbosslog.JBossLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.weaver.SignatureUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @file: CheckSignAspect.class
 * @author: Dusk
 * @since: 2019/5/7 1:04
 * @desc:
 */
@Aspect
@Component
@JBossLog
public class CheckSignAspect {
    /**
     * 定义切点
     */
    @Pointcut("execution(* com.example.aopreflex.controller..*.*bbCheckSign(..))")
    private void excudeService() {
    }

    /**
     * @param pjp
     * @return
     */
    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        // 拦截类名
        String class_name = pjp.getTarget().getClass().getName();
        // 拦截签名
        String method_name = pjp.getSignature().getName();
        // 参数名
        List<String> paramNames = getFieldsName(class_name, method_name);
        // 参数具体值
        Object[] method_args = pjp.getArgs();
        SortedMap<String, String> map = logParam(paramNames, method_args);
        Object result = Optional.ofNullable(map).map(m -> m.get("arg2").toUpperCase()).filter(real -> {
            map.remove("arg2");
            String realSign = SignUtil.createSign("utf8", map);
            return realSign.equals(real);
        }).map(a -> null).orElse("签名校验错误");
        if (!Optional.ofNullable(result).isPresent()) {
            result = pjp.proceed();
        }
        return result;
    }


    /**
     * 反射通过取出类中参数名
     *
     * @param class_name
     * @param method_name
     * @return
     */
    private List<String> getFieldsName(String class_name, String method_name) {
        Class<?> clazz = null;
        List<String> fields = null;
        try {
            clazz = Class.forName(class_name);
            String clazz_name = clazz.getName();
            Method method = clazz.getMethod(method_name, String.class, String.class, String.class);
            fields = Arrays.stream(method.getParameters()).map(Parameter::getName).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fields;
    }

    private SortedMap<String, String> logParam(List<String> paramNames, Object[] method_args) {
        List<String> param = paramNames;
        Object[] args = method_args;
        Map<String, String> map = param.stream().collect(Collectors.toMap(p -> p, p -> isPrimite(args[param.indexOf(p)])));
        return new TreeMap<>(map);
    }

    /**
     * 判断是否是基本类型
     *
     * @param arg
     * @return
     */
    private String isPrimite(Object arg) {
        Class<?> c = arg.getClass();
        // c == String.class
        if (c.isPrimitive() || arg instanceof String) {
            return String.valueOf(arg);
        }
        return "cuowu";
    }
}
