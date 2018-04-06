package org.knowm.xchange.bx.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ws.rs.FormParam;
import javax.xml.bind.DatatypeConverter;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class BxDigest extends BaseParamsDigest {

  private final String secretKey;

  private BxDigest(String secretKey) {
    super(secretKey, HMAC_SHA_256);
    this.secretKey = secretKey;
  }

  public static BxDigest createInstance(String secretKey) {
    return secretKey == null ? null : new BxDigest(secretKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    String apiKey = restInvocation.getParamValue(FormParam.class, "key").toString();
    String nonce = restInvocation.getParamValue(FormParam.class, "nonce").toString();
    String signature = apiKey + nonce + secretKey;
    MessageDigest sha256;
    try {
      sha256 = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(
          "Illegal algorithm for post body digest. Check the implementation.");
    }
    sha256.update(signature.getBytes());
    signature = DatatypeConverter.printHexBinary(sha256.digest()).toLowerCase();
    return signature;
  }
}
