package com.okcoin.commons.okex.open.api.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * sign test
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/7/9 16:03
 */
public class SignatureTest {

    @Test
    public void test() {
        try {
            Assert.assertEquals("TO6uwdqz+31SIPkd4I+9NiZGmVH74dXi+Fd5X0EzzSQ=",
                    signature("2018-03-08T10:59:25.789Z",
                            "POST",
                            "/orders?before=2&limit=30",
                            "{\"product_id\":\"BTC-USD-0309\",\"order_id\":\"377454671037440\"}",
                            "E65791902180E9EF4510DB6A77F6EBAE"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String signature(String timestamp, String method, String requestPath, String body, String secretKey)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String signStr = null;
        if (StringUtils.isNotEmpty(secretKey)) {
            body = body == null ? "" : body;
            method = method.toUpperCase();
            String preHash = timestamp + method + requestPath + body;
            byte[] secretKeyBytes = secretKey.getBytes("UTF-8");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKeySpec);
            signStr = Base64.getEncoder().encodeToString(mac.doFinal(preHash.getBytes("UTF-8")));
        }
        return signStr;
    }
}

