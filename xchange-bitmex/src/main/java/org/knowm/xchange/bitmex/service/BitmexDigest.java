package org.knowm.xchange.bitmex.service;

import java.util.Base64;

import javax.ws.rs.HeaderParam;

import org.apache.commons.codec.binary.Hex;
import org.knowm.xchange.service.BaseParamsDigest;

import si.mazi.rescu.RestInvocation;

public class BitmexDigest extends BaseParamsDigest {

  private String apiKey;

  /**
   * Constructor
   *
   * @param secretKeyBase64 the secret key to sign requests
   */

  private BitmexDigest(byte[] secretKeyBase64) {

    super(Base64.getUrlEncoder().withoutPadding().encodeToString(secretKeyBase64), HMAC_SHA_256);
  }

  public static BitmexDigest createInstance(String secretKeyBase64) {

    if (secretKeyBase64 != null) {
      return new BitmexDigest(Base64.getUrlDecoder().decode(secretKeyBase64.getBytes()));
    }
    return null;
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String nonce = restInvocation.getParamValue(HeaderParam.class, "api-nonce").toString();
    String path = restInvocation.getInvocationUrl().split(restInvocation.getBaseUrl())[1];
    String payload = restInvocation.getHttpMethod() + "/" + path + nonce + restInvocation.getRequestBody();

    return new String(Hex.encodeHex(getMac().doFinal(payload.getBytes())));
  }

  private BitmexDigest(String secretKeyBase64, String apiKey) {

    super(secretKeyBase64, HMAC_SHA_256);
    this.apiKey = apiKey;
  }

  public static BitmexDigest createInstance(String secretKeyBase64, String apiKey) {

    return secretKeyBase64 == null ? null : new BitmexDigest(secretKeyBase64, apiKey);
  }

}
