package org.knowm.xchange.bitflyer.service;

import javax.crypto.Mac;
import org.knowm.xchange.bitflyer.Bitflyer;
import org.knowm.xchange.service.BaseParamsDigest;
import org.knowm.xchange.utils.DigestUtils;
import si.mazi.rescu.RestInvocation;

public class BitflyerDigest extends BaseParamsDigest {

  private String apiKey;

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @param apiKey @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or
   *     the decoded key is invalid).
   */
  private BitflyerDigest(String secretKeyBase64, String apiKey) {
    super(secretKeyBase64, HMAC_SHA_256);
    this.apiKey = apiKey;
  }

  public static BitflyerDigest createInstance(String secretKeyBase64, String apiKey) {
    return secretKeyBase64 == null ? null : new BitflyerDigest(secretKeyBase64, apiKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String queryString = restInvocation.getQueryString();
    //    System.out.println("queryString = " + queryString);

    String httpMethod = restInvocation.getHttpMethod();

    String uri = restInvocation.getPath();
    if (queryString != null && queryString.length() > 0) {
      uri += "?" + restInvocation.getQueryString();
    }
    //    System.out.println("uri = " + uri);

    String requestBody = restInvocation.getRequestBody();
    String nonce = restInvocation.getHttpHeadersFromParams().get(Bitflyer.ACCESS_TIMESTAMP);

    // ACCESS-SIGN is the resulting HMAC-SHA256 signature done with the API secret path using the
    // ACCESS-TIMESTAMP, HTTP method, request path, and
    // request body linked together as a character string.
    String hmac_data = String.format("%s%s%s%s", nonce, httpMethod, uri, requestBody);
    Mac mac256 = getMac();
    mac256.update(hmac_data.getBytes());

    return DigestUtils.bytesToHex(mac256.doFinal()).trim();
  }
}
