package org.knowm.xchange.bl3p.service;

import java.util.Base64;
import javax.crypto.Mac;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class Bl3pDigest extends BaseParamsDigest {

  private static Base64.Encoder encoder = Base64.getEncoder();

  private Bl3pDigest(byte[] secretKey) {
    super(secretKey, HMAC_SHA_512);
  }

  public static Bl3pDigest createInstance(String secretKeyBase64) {
    if (secretKeyBase64 != null) {
      return new Bl3pDigest(decodeBase64(secretKeyBase64));
    }

    return null;
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    String path = restInvocation.getMethodPath().replaceFirst("/", "");
    String requestBody = restInvocation.getRequestBody();
    String stringToSign = (path + "\0" + requestBody);

    Mac mac = getMac();
    mac.update(stringToSign.getBytes());

    byte[] rawSignature = mac.doFinal();
    byte[] base64encoded = encoder.encode(rawSignature);

    return new String(base64encoded);
  }
}
