package org.knowm.xchange.coinone.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.knowm.xchange.utils.DigestUtils;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

public class CoinoneHmacDigest implements ParamsDigest {

  private static final String HMAC_SHA_512 = "HmacSHA512";
  private final Mac mac;

  private CoinoneHmacDigest(String secretKeyBase) throws IllegalArgumentException {

    try {
      SecretKey secretKey = new SecretKeySpec(secretKeyBase.getBytes(), HMAC_SHA_512);
      mac = Mac.getInstance(HMAC_SHA_512);
      mac.init(secretKey);
    } catch (InvalidKeyException e) {
      throw new IllegalArgumentException("Invalid key for hmac initialization.", e);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(
          "Illegal algorithm for post body digest. Check the implementation.");
    }
  }

  public static CoinoneHmacDigest createInstance(String secretKeyBase) {

    return new CoinoneHmacDigest(secretKeyBase);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    String postBody = restInvocation.getRequestBody();
    String payload = Base64.getEncoder().encodeToString(postBody.getBytes());
    mac.update(payload.getBytes());
    String rtn = DigestUtils.bytesToHex(mac.doFinal()).toLowerCase();
    return rtn;
  }
}
