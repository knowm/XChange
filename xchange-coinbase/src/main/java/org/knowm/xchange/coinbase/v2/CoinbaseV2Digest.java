package org.knowm.xchange.coinbase.v2;

import static org.knowm.xchange.coinbase.v2.CoinbaseAuthenticated.CB_ACCESS_TIMESTAMP;

import jakarta.ws.rs.HeaderParam;
import org.knowm.xchange.service.BaseParamsDigest;
import org.knowm.xchange.utils.DigestUtils;
import si.mazi.rescu.RestInvocation;

public class CoinbaseV2Digest extends BaseParamsDigest {

  private CoinbaseV2Digest(String secretKey) {
    super(secretKey, HMAC_SHA_256);
  }

  public static CoinbaseV2Digest createInstance(String secretKey) {
    return secretKey == null ? null : new CoinbaseV2Digest(secretKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    final String pathWithQueryString =
        restInvocation.getInvocationUrl().replace(restInvocation.getBaseUrl(), "");
    final String timestamp =
        restInvocation.getParamValue(HeaderParam.class, CB_ACCESS_TIMESTAMP).toString();
    final String message = timestamp + restInvocation.getHttpMethod() + pathWithQueryString;

    return DigestUtils.bytesToHex(getMac().doFinal(message.getBytes()));
  }
}
