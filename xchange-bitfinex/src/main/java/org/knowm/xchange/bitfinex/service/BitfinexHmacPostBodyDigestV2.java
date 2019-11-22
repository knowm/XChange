package org.knowm.xchange.bitfinex.service;

import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

import javax.crypto.Mac;
import javax.ws.rs.HeaderParam;
import java.math.BigInteger;

public class BitfinexHmacPostBodyDigestV2 extends BaseParamsDigest {

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded
   *     key is invalid).
   */
  private BitfinexHmacPostBodyDigestV2(String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_384);
  }

  public static BitfinexHmacPostBodyDigestV2 createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new BitfinexHmacPostBodyDigestV2(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    String nonce = restInvocation.getParamValue(HeaderParam.class, "bfx-nonce").toString();
    String path = restInvocation.getPath();
    String payload = "/api/" + path + nonce + restInvocation.getRequestBody();

    Mac mac = getMac();
    mac.update(payload.getBytes());
    return String.format("%096x", new BigInteger(1, mac.doFinal()));
  }

}
