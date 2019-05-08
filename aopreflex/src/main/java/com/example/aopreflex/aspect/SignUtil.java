package com.example.aopreflex.aspect;

import lombok.extern.jbosslog.JBossLog;
import org.apache.logging.log4j.util.Strings;

import java.util.SortedMap;

/**
 * @file: SignUtil.class
 * @author: Dusk
 * @since: 2019/5/7 22:51
 * @desc:
 */
@JBossLog
public class SignUtil {

    public static String createSign(String utf8, SortedMap<String, String> map) {
        StringBuffer sb = new StringBuffer();
        map.entrySet().stream().filter(e -> Strings.isNotBlank(e.getValue()) && !"arg2".equals(e.getKey()) && !"key".equals(e.getKey())).forEach(e -> sb.append(e.getKey() + "=" + e.getValue() + "&"));
        String s = sb.toString();
        // log.info("加密-----" + s);
        System.out.println("加密-----" + s);
        return s;
    }
}
