package com.okcoin.commons.okex.open.api.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.constant.APIConstants;
import com.okcoin.commons.okex.open.api.exception.APIException;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Date Utils  <br/>
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/2/9 18:54
 */

public class JsonUtils {

    /**
     * The java object is converted to a JSONObject，ensure precision (as strings). <br/>
     *
     * @param t   java object
     * @param tC  generic class
     * @param <T> generic flag
     * @return If null, return {} ，Otherwise the normal
     */
    public static <T> JSONObject convertObject(T t, Class<T> tC) {
        if (t == null) {
            return APIConstants.NOTHING;
        }
        Field[] fields = tC.getDeclaredFields();
        JSONObject object = new JSONObject();
        try {
            for (Field field : fields) {
                String name = field.getName();
                String type = field.getType().toString();
                String methodName = getMethodName(type, name);
                Method[] methods = tC.getMethods();
                for (Method method : methods) {
                    if (methodName.equals(method.getName())) {
                        Object result = method.invoke(t);
                        if (APIConstants.toStringTypeArray.contains(type)) {
                            object.put(field.getName(), toString(result));
                        } else if (APIConstants.toStringTypeDoubleArray.contains(type) && result != null) {
                            object.put(field.getName(), NumberUtils.doubleToString((Double) result));
                        } else {
                            object.put(field.getName(), result);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new APIException("Java biz bean change JSONObject is exception.", e);
        }
        return object;
    }

    /**
     * The java object list is converted to a JSONArray，ensure precision (as strings)
     *
     * @param list java object list
     * @param tC   generic class
     * @param <T>  generic flag
     * @return If null, return [] ，Otherwise the normal
     */
    public static final <T> JSONArray convertList(List<T> list, Class<T> tC) {
        JSONArray array = new JSONArray();
        if (CollectionUtils.isEmpty(list)) {
            return array;
        }

        for (T t : list) {
            array.add(convertObject(t, tC));
        }
        return array;
    }

    public static final String getMethodName(String type, String field) {
        StringBuilder methodName = new StringBuilder();
        if (type.equals(APIConstants.BOOLEAN)) {
            methodName.append(APIConstants.IS);
        } else {
            methodName.append(APIConstants.get);
        }
        return methodName.append(startUpperCase(field)).toString();
    }

    public static final String startUpperCase(String name) {
        char[] cs = name.toCharArray();
        if (cs[0] >= APIConstants.a && cs[0] <= APIConstants.z) {
            cs[0] -= 32;
        }
        return String.valueOf(cs);
    }

    public static final String toString(Object object) {
        return object == null ? APIConstants.ZERO_STRING : object.toString();
    }

}
