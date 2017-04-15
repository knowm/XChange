package org.knowm.xchange.cexio.service;

import java.math.BigInteger;

import javax.crypto.Mac;
import javax.ws.rs.FormParam;

import org.knowm.xchange.service.BaseParamsDigest;

import si.mazi.rescu.RestInvocation;

/**
 * Author: brox Since: 2/7/14 9:42 PM
 */
public class CexIODigest extends BaseParamsDigest {

  private final String clientId;
  private final String apiKey;

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @param clientId Account user name
   * @param apiKey @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  private CexIODigest(String secretKeyBase64, String clientId, String apiKey) {

    super(secretKeyBase64, HMAC_SHA_256);
    this.clientId = clientId;
    this.apiKey = apiKey;
  }

  public static CexIODigest createInstance(String secretKeyBase64, String clientId, String apiKey) {

    return secretKeyBase64 == null ? null : new CexIODigest(secretKeyBase64, clientId, apiKey);
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
