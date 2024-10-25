package com.mumu.util;

import com.alibaba.fastjson.JSONObject;

/**
 * JSON工具类
 * 像工具类这种，建议一定要二次封装，避免出现漏洞时可以快速替换
 *
 * @author tianxincode@163.com
 * @since 2022/6/16
 */
public class JsonUtil {
    private JsonUtil() {}

    public static String toJson(Object value) {
        return JSONObject.toJSONString(value);
    }

    public static <T> T toObject(String jsonStr, Class<T> clazz) {
        return JSONObject.parseObject(jsonStr, clazz);
    }
}
