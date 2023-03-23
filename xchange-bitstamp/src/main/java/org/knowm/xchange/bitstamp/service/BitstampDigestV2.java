package org.knowm.xchange.bitstamp.service;

import java.math.BigInteger;
import javax.crypto.Mac;
import jakarta.ws.rs.HeaderParam;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

/** @author Benedikt Bünz */
public class BitstampDigestV2 extends BaseParamsDigest {

  private final String apiKey;
  private final String baseUrlHost = "www.bitstamp.net";

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @param apiKey @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or
   *     the decoded key is invalid).
   */
  private BitstampDigestV2(String secretKeyBase64, String apiKey) {

    super(secretKeyBase64, HMAC_SHA_256);
    this.apiKey = "BITSTAMP " + apiKey;
  }

  public static BitstampDigestV2 createInstance(String secretKeyBase64, String apiKey) {

    return secretKeyBase64 == null ? null : new BitstampDigestV2(secretKeyBase64, apiKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    Mac mac256 = getMac();
    String okPath = "/" + restInvocation.getPath();
    String contentType = restInvocation.getReqContentType();

    mac256.update(apiKey.getBytes());

    mac256.update(restInvocation.getHttpMethod().getBytes()); // HTTP VERB
    mac256.update(baseUrlHost.getBytes()); // url Host
    mac256.update(okPath.getBytes()); // url Path
    mac256.update(restInvocation.getQueryString().getBytes()); // url Query

    if (contentType != null) {
      mac256.update(contentType.getBytes()); // content type
    }

    mac256.update(
        restInvocation.getParamValue(HeaderParam.class, "X-Auth-Nonce").toString().getBytes());
    mac256.update(
        restInvocation.getParamValue(HeaderParam.class, "X-Auth-Timestamp").toString().getBytes());
    mac256.update(
        restInvocation.getParamValue(HeaderParam.class, "X-Auth-Version").toString().getBytes());
    mac256.update(restInvocation.getRequestBody().getBytes());

    return String.format("%064x", new BigInteger(1, mac256.doFinal())).toUpperCase();
  }
}
