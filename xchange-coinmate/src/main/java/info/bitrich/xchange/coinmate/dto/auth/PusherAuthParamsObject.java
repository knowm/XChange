package info.bitrich.xchange.coinmate.dto.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class PusherAuthParamsObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(PusherAuthParamsObject.class);
    private final Map<String, String> params = new HashMap<>();

    public PusherAuthParamsObject(String secret, String apiKey, String userId, Long nonce) {
        this.params.put("clientId", userId);
        this.params.put("nonce", String.valueOf(nonce));
        this.params.put("signature", signature(nonce, userId, apiKey, secret));
        this.params.put("publicKey", apiKey);
    }

    public Map<String, String> getParams() {
        return params;
    }

    private String signature(Long nonce, String userId, String apiKey, String apiSecret) {
        try {
            Mac mac256 = Mac.getInstance("HmacSHA256");
            SecretKey secretKey = new SecretKeySpec(apiSecret.getBytes("UTF-8"), "HmacSHA256");
            mac256.init(secretKey);
            mac256.update(String.valueOf(nonce).getBytes());
            mac256.update(userId.getBytes());
            mac256.update(apiKey.getBytes());
            return String.format("%064x", new BigInteger(1, mac256.doFinal())).toUpperCase();
        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return null;
        }
    }

    @Override
    public String toString() {
        return "PusherAuthParamsObject{" +
                "params=" + params +
                '}';
    }
}
