package com.xeiam.xchange.cexio.service;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.FormParam;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Author: brox
 * Since: 2/7/14 9:42 PM
 */
public class CexIODigest  implements ParamsDigest {

  private static final String HMAC_SHA_256 = "HmacSHA256";

  private final Mac mac256;
  private final String clientId;
  private final String apiKey;

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @param clientId Account user name
   * @param apiKey @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  private CexIODigest(String secretKeyBase64, String clientId, String apiKey) throws IllegalArgumentException {

    this.clientId = clientId;
    this.apiKey = apiKey;
    try {
      mac256 = Mac.getInstance(HMAC_SHA_256);
      mac256.init(new SecretKeySpec(secretKeyBase64.getBytes(), HMAC_SHA_256));
    } catch (InvalidKeyException e) {
      throw new IllegalArgumentException("Invalid key for hmac initialization.", e);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Illegal algorithm for post body digest. Check the implementation.");
    }
  }

  public static CexIODigest createInstance(String secretKeyBase64, String clientId, String apiKey) throws IllegalArgumentException {

    return secretKeyBase64 == null ? null : new CexIODigest(secretKeyBase64, clientId, apiKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    mac256.update(restInvocation.getParamValue(FormParam.class, "nonce").toString().getBytes());
    mac256.update(clientId.getBytes());
    mac256.update(apiKey.getBytes());

    return String.format("%064x", new BigInteger(1, mac256.doFinal())).toUpperCase();
  }

}
