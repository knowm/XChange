package org.knowm.xchange.coinbase.v2;

import org.knowm.xchange.service.BaseParamsDigest;
import org.knowm.xchange.utils.DigestUtils;
import si.mazi.rescu.RestInvocation;

import javax.ws.rs.HeaderParam;

import static org.knowm.xchange.coinbase.v2.CoinbaseAuthenticated.CB_ACCESS_TIMESTAMP;

public class CoinbaseV2Digest extends BaseParamsDigest {

  public static final String ADVANCED_TRADING_V3 = "api/v3/brokerage/";

  private CoinbaseV2Digest(String secretKey) {
    super(secretKey, HMAC_SHA_256);
  }

  public static CoinbaseV2Digest createInstance(String secretKey) {
    return secretKey == null ? null : new CoinbaseV2Digest(secretKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    String path = restInvocation.getInvocationUrl();
    final String timestamp = restInvocation.getParamValue(HeaderParam.class, CB_ACCESS_TIMESTAMP).toString();
    if (path.contains(ADVANCED_TRADING_V3)) {
      path = "/" + restInvocation.getPath();
    } else {
      path = path.replace(restInvocation.getBaseUrl(), "");
    }
    String message = timestamp + restInvocation.getHttpMethod() + path;
    return DigestUtils.bytesToHex(getMac().doFinal(message.getBytes()));
  }
}
