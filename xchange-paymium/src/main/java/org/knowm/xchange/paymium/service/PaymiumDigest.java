package org.knowm.xchange.paymium.service;

import java.math.BigInteger;
import javax.crypto.Mac;
import javax.ws.rs.HeaderParam;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class PaymiumDigest extends BaseParamsDigest {

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded
   *     key is invalid).
   */
  private PaymiumDigest(String secretKeyBase64) {
    super(secretKeyBase64, HMAC_SHA_256);
  }

  public static PaymiumDigest createInstance(String secretKeyBase64) {
    return secretKeyBase64 == null ? null : new PaymiumDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    String invocationUrl =
        restInvocation.getParamValue(HeaderParam.class, "Api-Nonce").toString()
            + restInvocation.getInvocationUrl();
    Mac mac = getMac();
    mac.update(invocationUrl.getBytes());
    return String.format("%064x", new BigInteger(1, mac.doFinal()));
  }
}
