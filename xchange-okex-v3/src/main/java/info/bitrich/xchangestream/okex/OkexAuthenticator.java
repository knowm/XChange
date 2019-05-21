package info.bitrich.xchangestream.okex;

import info.bitrich.xchangestream.okex.dto.RequestMessage;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class OkexAuthenticator {

    public static RequestMessage authenticateMessage(String apiKey, String apiSecret, String passphrase) throws NoSuchAlgorithmException, InvalidKeyException {
        String timestamp = String.format("%.3f", (double)System.currentTimeMillis() / 1000);
        String message = timestamp + "GET/users/self/verify";
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secretKey);
        String sign = Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(message.getBytes()));
        return new RequestMessage(RequestMessage.Operation.LOGIN, apiKey, passphrase, timestamp, sign);
    }

}
