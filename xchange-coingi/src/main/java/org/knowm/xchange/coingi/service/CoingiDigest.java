package org.knowm.xchange.coingi.service;

import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class CoingiDigest extends BaseParamsDigest {
  private final String clientId;
  private final String apiKey;
  private final DefaultIdentityProvider provider;

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @param clientId
   * @param apiKey @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or
   *     the decoded key is invalid).
   */
  private CoingiDigest(String secretKeyBase64, String clientId, String apiKey) {
    super(secretKeyBase64, HMAC_SHA_256);
    provider = new DefaultIdentityProvider(apiKey.getBytes(), secretKeyBase64.getBytes());
    this.clientId = clientId;
    this.apiKey = apiKey;
  }

  public static CoingiDigest createInstance(
      String secretKeyBase64, String clientId, String apiKey) {
    return secretKeyBase64 == null ? null : new CoingiDigest(secretKeyBase64, clientId, apiKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    throw new UnsupportedOperationException();
  }

  public String sign(Long nonce) {
    return provider.getSignature(nonce);
    /*
    // TODO: this implementation is wrong
    Mac mac256 = getMac();
    mac256.update(restInvocation.getParamValue(FormParam.class, "nonce").toString().getBytes());
    mac256.update(clientId.getBytes());
    mac256.update(apiKey.getBytes());

    return String.format("%064x", new BigInteger(1, mac256.doFinal())).toUpperCase();*/
  }
}
