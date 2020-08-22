package com.pay.typay.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pay.typay.common.utils.jsondiff.GsonDiff;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     *
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData json数据
     * @param beanType 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     *
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 多对象转json字符串
     *
     * @param map
     * @return
     */
    public static String objectsToJson(Map<String, Object> map) {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        map.forEach((s, o) -> {
            String str = objectToJson(o);
            sb.append("\"").append(s).append("\":").append(str).append(",");
        });
        return sb.substring(0, sb.length() - 1) + "}";
    }

    /**
     * 返回两个json不同
     * @param before
     * @param after
     * @return 不同
     */
    public static String diff(String before, String after){
        return new GsonDiff().diff(before,after);
    }

}
