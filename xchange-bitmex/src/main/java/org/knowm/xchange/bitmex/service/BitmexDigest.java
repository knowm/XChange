package org.knowm.xchange.bitmex.service;

import javax.crypto.Mac;

import org.knowm.xchange.service.BaseParamsDigest;
import org.knowm.xchange.utils.DigestUtils;

import si.mazi.rescu.RestInvocation;

public class BitmexDigest extends BaseParamsDigest {

  private String apiKey;

  /**
   * Constructor
   *
   * @param secretKeyBase64
   * @param apiKey @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  private BitmexDigest(String secretKeyBase64, String apiKey) {
    super(secretKeyBase64, HMAC_SHA_256);
    this.apiKey = apiKey;
  }

  public static BitmexDigest createInstance(String secretKeyBase64, String apiKey) {
    return secretKeyBase64 == null ? null : new BitmexDigest(secretKeyBase64, apiKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String queryString = restInvocation.getQueryString();

    String httpMethod = restInvocation.getHttpMethod();

    String uri = restInvocation.getPath();
    if (queryString != null && queryString.length() > 0) {
      uri += "?" + restInvocation.getQueryString();
    }

    String requestBody = restInvocation.getRequestBody();
    String nonce = restInvocation.getHttpHeadersFromParams().get("API-NONCE");

    // verb + path + str(nonce) + data
    //ex: 'GET/api/v1/instrument?filter=%7B%22symbol%22%3A+%22XBTM15%22%7D1429631577690'
    String hmac_data = String.format("%s%s%s%s", httpMethod, uri, nonce, requestBody);
    Mac mac256 = getMac();
    mac256.update(hmac_data.getBytes());

    return DigestUtils.bytesToHex(mac256.doFinal()).trim();

  }
}
