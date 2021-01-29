package info.bitrich.xchangestream.coinmate.v2.dto.auth;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.knowm.xchange.coinmate.CoinmateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class AuthParams {

  private static final Logger log = LoggerFactory.getLogger(AuthParams.class);

  private final String secret;
  private final String apiKey;
  private final String userId;
  private SynchronizedValueFactory<Long> nonce;

  public AuthParams(
      String secret, String apiKey, String userId, SynchronizedValueFactory<Long> nonce) {
    this.secret = secret;
    this.apiKey = apiKey;
    this.userId = userId;
    this.nonce = nonce;
  }

  public Map<String, String> getParams() {
    Map<String, String> params = new HashMap<>();
    Long nonce1 = nonce.createValue();
    params.put("clientId", userId);
    params.put("nonce", String.valueOf(nonce1));
    params.put("signature", signature(nonce1, userId, apiKey, secret));
    params.put("publicKey", apiKey);

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
      log.error(ex.getMessage(), ex);
      throw new CoinmateException(ex.getMessage());
    }
  }

  public String getUserId() {
    return userId;
  }
}
