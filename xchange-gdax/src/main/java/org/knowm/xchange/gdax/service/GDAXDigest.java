package org.knowm.xchange.gdax.service;

import java.util.Base64;
import javax.crypto.Mac;
import javax.ws.rs.HeaderParam;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class GDAXDigest extends BaseParamsDigest {

  private String signature = "";

  private GDAXDigest(byte[] secretKey) {

    super(secretKey, HMAC_SHA_256);
  }

  public static GDAXDigest createInstance(String secretKey) {

    return secretKey == null ? null : new GDAXDigest(Base64.getDecoder().decode(secretKey));
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String pathWithQueryString =
        restInvocation.getInvocationUrl().replace(restInvocation.getBaseUrl(), "");
    String message =
        restInvocation.getParamValue(HeaderParam.class, "CB-ACCESS-TIMESTAMP").toString()
            + restInvocation.getHttpMethod()
            + pathWithQueryString
            + (restInvocation.getRequestBody() != null ? restInvocation.getRequestBody() : "");

    Mac mac256 = getMac();

    try {
      mac256.update(message.getBytes("UTF-8"));
    } catch (Exception e) {
      throw new ExchangeException("Digest encoding exception", e);
    }

    signature = Base64.getEncoder().encodeToString(mac256.doFinal());
    return signature;
  }

  public String getSignature() {
    return signature;
  }
}
