package com.wisdom.classroom.util;

import org.apache.poi.ss.formula.functions.T;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerReturnApi {

    public static Map<String, Object> returnJson(int code, String msg) {
        Map map = new HashMap();
        map.put("code", code);
        map.put("msg", msg);
        return map;
    }

    public static Map<String, Object> returnJson(int code, String msg, List<Map> data) {
        Map map = new HashMap();
        map.put("code", code);
        map.put("msg", msg);
        map.put("data", data);
        return map;
    }

    public static Map<String, Object> returnJson(int code, String msg, Map data) {
        Map map = new HashMap();
        map.put("code", code);
        map.put("msg", msg);
        map.put("data", data);
        return map;
    }

    public static Map<String, Object> returnJson(int code, String msg, int count, List<T> data) {
        Map map = new HashMap();
        map.put("code", code);
        map.put("msg", msg);
        map.put("count", count);
        map.put("data", data);
        return map;
    }

}
