package org.knowm.xchange.gatecoin.service;

import java.util.Base64;
import javax.crypto.Mac;
import javax.ws.rs.HeaderParam;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class GatecoinDigest extends BaseParamsDigest {

  private GatecoinDigest(String secretKeyBase64) {
    super(secretKeyBase64, HMAC_SHA_256);
  }

  public static GatecoinDigest createInstance(String secretKeyBase64) {
    return secretKeyBase64 == null ? null : new GatecoinDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    return digest(
        restInvocation.getHttpMethod(),
        restInvocation.getInvocationUrl(),
        restInvocation.getReqContentType(),
        restInvocation.getParamValue(HeaderParam.class, "API_REQUEST_DATE").toString());
  }

  String digest(String httpMethod, String invocationUrl, String reqContentType, String now) {
    Mac mac256 = getMac();
    mac256.update(httpMethod.toLowerCase().getBytes());
    mac256.update(invocationUrl.toLowerCase().getBytes());
    if (!"GET".equals(httpMethod)) {
      mac256.update(reqContentType.toLowerCase().getBytes());
    }
    mac256.update(now.toLowerCase().getBytes());
    return Base64.getEncoder().encodeToString(mac256.doFinal());
  }
}
