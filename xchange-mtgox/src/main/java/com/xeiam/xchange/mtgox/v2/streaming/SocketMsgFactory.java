package com.xeiam.xchange.mtgox.v2.streaming;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.utils.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SocketMsgFactory {

    private final String apiKey;
    private final String apiSecret;

    public SocketMsgFactory(String apiKey, String apiSecret) {
        if ( apiKey == null || apiSecret == null ||
                apiKey.length() == 0 || apiSecret.length() == 0 ) {
            throw new IllegalArgumentException("mtgox api key and/or secret is missing");
        }

        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    public String subscribeToChannel(String channel) throws JsonProcessingException {
        HashMap<String, String> map = new HashMap<String, String>(2);
        map.put("op", "mtgox.subscribe");
        map.put("key", channel);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);

    }

    public String idKey() throws JsonProcessingException, UnsupportedEncodingException {
        return signedCall("private/idkey", new HashMap<String, String>(), "idkey");

    }

    private String signedCall(String endPoint, Map<String, String> params, String reqId)
            throws JsonProcessingException, UnsupportedEncodingException {

        String nonce = String.valueOf(System.currentTimeMillis()) + "000";

        HashMap<String, Object> call = new HashMap<String, Object>(6);
        call.put("id", reqId);
        call.put("call", endPoint);
        call.put("nonce", nonce);
        call.put("params", params);
        call.put("currency", "USD");
        call.put("item", "BTC");

        ObjectMapper mapper = new ObjectMapper();
        String callString = mapper.writeValueAsString(call);
        String signedCall = null;

        try {
            byte[] bsecret = Base64.decode(this.apiSecret);
            SecretKeySpec spec = new SecretKeySpec(bsecret, "HmacSHA512");
            Mac mac = Mac.getInstance("HmacSHA512");
            mac.init(spec);

            byte[] bsig = mac.doFinal(callString.getBytes());
            byte[] keyB = fromHexString(this.apiKey.replaceAll("-", ""));
            byte[] callB = callString.getBytes();

            byte[] c = new byte[bsig.length + keyB.length + callB.length];
            System.arraycopy(keyB, 0, c, 0, keyB.length);
            System.arraycopy(bsig, 0, c, keyB.length, bsig.length);
            System.arraycopy(callB, 0, c, keyB.length + bsig.length, callB.length);

            signedCall = Base64.encodeBytes(c);

        } catch (Exception e) {
            System.out.println("e!: " + e);

        }

        HashMap<String, String> msg = new HashMap<String, String>(4);
        msg.put("op", "call");
        msg.put("call", signedCall);
        msg.put("id", reqId);
        msg.put("context", "mtgox.com");

        mapper = new ObjectMapper();
        return mapper.writeValueAsString(msg);
    }

    private static byte[] fromHexString(String hex) {
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        for (int i = 0; i < hex.length(); i += 2) {
            int b = Integer.parseInt(hex.substring(i, i + 2), 16);
            bas.write(b);
        }

        return bas.toByteArray();
    }
}
