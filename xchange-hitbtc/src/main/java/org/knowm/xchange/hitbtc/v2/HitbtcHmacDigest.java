package org.knowm.xchange.hitbtc.v2;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.knowm.xchange.utils.DigestUtils;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

public class HitbtcHmacDigest implements ParamsDigest {

  private static final String HMAC_SHA_512 = "HmacSHA512";
  private final Mac mac;

  private HitbtcHmacDigest(String secretKeyBase) throws IllegalArgumentException {

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

  public static HitbtcHmacDigest createInstance(String secretKeyBase) {

    return new HitbtcHmacDigest(secretKeyBase);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String postBody = restInvocation.getRequestBody();
    if (postBody == null) {
      postBody = "";
    }

    String uri = restInvocation.getPath() + "?" + restInvocation.getQueryString();
    String message = uri + postBody;

    mac.update(message.getBytes());

    return DigestUtils.bytesToHex(mac.doFinal()).toLowerCase();
  }
}
