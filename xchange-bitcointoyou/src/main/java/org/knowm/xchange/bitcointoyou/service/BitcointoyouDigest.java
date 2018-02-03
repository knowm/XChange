package org.knowm.xchange.bitcointoyou.service;

import org.knowm.xchange.service.BaseParamsDigest;

import net.iharder.Base64;
import si.mazi.rescu.RestInvocation;

/**
 * @author Jonathas Carrijo
 * @author Danilo Guimaraes
 */
public class BitcointoyouDigest extends BaseParamsDigest {

  private final String apiKey;

  /**
   * Constructor
   * 
   * @param secretKeyBase64 the Secret Key
   * @param apiKey the API Key
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  private BitcointoyouDigest(String secretKeyBase64, String apiKey) {

    super(secretKeyBase64, HMAC_SHA_256);
    this.apiKey = apiKey;
  }

  public static BitcointoyouDigest createInstance(String secretKeyBase64, String apiKey) {

    return secretKeyBase64 == null ? null : new BitcointoyouDigest(secretKeyBase64, apiKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    // The Bitcointoyou API specifies that signature field is a concat between nonce and API Key.
    String signature = restInvocation.getHttpHeadersFromParams().get("nonce") + apiKey;

    return Base64.encodeBytes(getMac().doFinal(signature.getBytes())).toUpperCase();
  }
}
