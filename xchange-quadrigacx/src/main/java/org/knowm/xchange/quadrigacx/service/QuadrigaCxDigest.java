package org.knowm.xchange.quadrigacx.service;

import java.math.BigInteger;
import javax.crypto.Mac;
import javax.ws.rs.FormParam;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class QuadrigaCxDigest extends BaseParamsDigest {

  private final String clientId;

  private final String apiKey;

  private QuadrigaCxDigest(String secretKeyHex, String clientId, String apiKey) {
    super(secretKeyHex, HMAC_SHA_256);
    this.clientId = clientId;
    this.apiKey = apiKey;
  }

  public static QuadrigaCxDigest createInstance(String secretKey, String userName, String apiKey) {
    return secretKey == null ? null : new QuadrigaCxDigest(secretKey, userName, apiKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    Mac mac256 = getMac();
    mac256.update(restInvocation.getParamValue(FormParam.class, "nonce").toString().getBytes());
    mac256.update(clientId.getBytes());
    mac256.update(apiKey.getBytes());

    return String.format("%064x", new BigInteger(1, mac256.doFinal())).toUpperCase();
  }
}
