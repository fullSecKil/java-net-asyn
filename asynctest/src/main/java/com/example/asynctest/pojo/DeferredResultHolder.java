package com.example.asynctest.pojo;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * 延时结果持有者
 * 中介类来存放订单号和处理结果
 */

@Component
public class DeferredResultHolder {
    /**
     * String： 订单号
     * DeferredResult: 处理结果
     */
    private Map<String, DeferredResult> map = new HashMap<>();

    public Map<String, DeferredResult> getMap() {
        return map;
    }

    public void setMap(Map<String, DeferredResult> map) {
        this.map = map;
    }
}
