package org.knowm.xchange.coinbasepro.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import jakarta.ws.rs.HeaderParam;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class CoinbaseProDigest extends BaseParamsDigest {

  private CoinbaseProDigest(byte[] secretKey) {

    super(secretKey, HMAC_SHA_256);
  }

  public static CoinbaseProDigest createInstance(String secretKey) {

    return secretKey == null ? null : new CoinbaseProDigest(Base64.getDecoder().decode(secretKey));
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
      mac256.update(message.getBytes(StandardCharsets.UTF_8));
    } catch (Exception e) {
      throw new ExchangeException("Digest encoding exception", e);
    }

    return Base64.getEncoder().encodeToString(mac256.doFinal());
  }
}
