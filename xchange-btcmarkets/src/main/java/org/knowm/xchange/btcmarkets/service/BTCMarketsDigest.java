package org.knowm.xchange.btcmarkets.service;

import java.util.Base64;
import javax.crypto.Mac;
import javax.ws.rs.HeaderParam;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class BTCMarketsDigest extends BaseParamsDigest {
  /** True for V2 endpoints, false for V1 */
  private boolean includeQueryString;

  public BTCMarketsDigest(String secretKey, Boolean includeQueryString) {
    super(decodeBase64(secretKey), HMAC_SHA_512);
    this.includeQueryString = includeQueryString;
  }

  @Override
  public String digestParams(RestInvocation inv) {
    final String nonce = inv.getParamValue(HeaderParam.class, "timestamp").toString();
    return digest(inv.getMethodPath(), nonce, inv.getRequestBody(), inv.getQueryString());
  }

  String digest(String url, String nonce, String requestBody, String queryString) {
    Mac mac = getMac();
    if (!url.startsWith("/")) {
      url = "/" + url;
    }
    mac.update(url.getBytes());
    mac.update("\n".getBytes());
    if (includeQueryString && queryString != null && !queryString.isEmpty()) {
      mac.update(queryString.getBytes());
      mac.update("\n".getBytes());
    }
    mac.update(nonce.getBytes());
    mac.update("\n".getBytes());
    if (requestBody != null && !requestBody.isEmpty()) {
      mac.update(requestBody.getBytes());
    }

    return Base64.getEncoder().encodeToString(mac.doFinal());
  }
}
