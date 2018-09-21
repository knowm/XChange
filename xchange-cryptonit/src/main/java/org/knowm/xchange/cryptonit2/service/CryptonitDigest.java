package org.knowm.xchange.cryptonit2.service;

import java.math.BigInteger;
import javax.crypto.Mac;
import javax.ws.rs.FormParam;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

/** @author Benedikt Bünz */
public class CryptonitDigest extends BaseParamsDigest {

  private final String clientId;
  private final String apiKey;

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @param clientId
   * @param apiKey @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or
   *     the decoded key is invalid).
   */
  private CryptonitDigest(String secretKeyBase64, String clientId, String apiKey) {

    super(secretKeyBase64, HMAC_SHA_256);
    this.clientId = clientId;
    this.apiKey = apiKey;
  }

  public static CryptonitDigest createInstance(
      String secretKeyBase64, String clientId, String apiKey) {

    return secretKeyBase64 == null ? null : new CryptonitDigest(secretKeyBase64, clientId, apiKey);
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
