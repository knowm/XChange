package org.knowm.xchange.coinsuper.utils;

import com.alibaba.fastjson.JSONObject;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import org.knowm.xchange.coinsuper.utils.RestRequestParam;

/**
 *
 */
public class RestSignUtil {

    /**
     * 生成API传参签名
     * @param param
     * @param secretkey
     * @return
     */
    public static <T> String generateSign(RestRequestParam<T> param, String secretkey){
        TreeMap<String,String> paramsMap = new TreeMap<String, String>();
        JSONObject paramJSON = JSONObject.parseObject(JSONObject.toJSONString(param));
        for(Map.Entry<String, Object> entry:paramJSON.entrySet()){
            if(entry.getValue() instanceof JSONObject){
                for(Map.Entry<String, Object> childEntry:((JSONObject)entry.getValue()).entrySet()){
                    if(!"sign".equals(childEntry.getKey())&&childEntry.getValue()!=null){
                        paramsMap.put(childEntry.getKey(),childEntry.getValue().toString());
                    }
                }
            }else if(entry.getValue()!=null){
                paramsMap.put(entry.getKey(),entry.getValue().toString());
            }
        }
        paramsMap.put("secretkey",secretkey);
        return generateSignByRule(paramsMap);
    }
    
    /**
     * 
     * @param md5Str
     * @return
     */
    private static String getMD5(String md5Str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(md5Str.getBytes("utf-8"));
            StringBuffer sb = new StringBuffer();
            String temp = "";
            for (byte b : bytes) {
                temp = Integer.toHexString(b & 0XFF);
                sb.append(temp.length() == 1 ? "0" + temp : temp);
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 把需要验签的参数封装到TreeMap中,生成加密sign串
     * @param map
     * @return
     */
    private static String generateSignByRule(TreeMap<String, String> map) {
        String sign = "";
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            if (it.hasNext()) {
                if (entry.getValue() != null && !"".equals(entry.getValue().toString().trim())) {
                    sign += entry.getKey() + "=" + entry.getValue() + "&";
                }
            }
            else {
                if (entry.getValue() != null && !"".equals(entry.getValue().toString().trim())) {
                    sign += entry.getKey() + "=" + entry.getValue();
                }
                else {
                    sign = sign.substring(0, sign.length() - 1);
                }
            }
        }
        return getMD5(sign);
    }
}

