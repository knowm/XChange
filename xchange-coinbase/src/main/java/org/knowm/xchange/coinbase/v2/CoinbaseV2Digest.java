package org.knowm.xchange.coinbase.v2;

import static org.knowm.xchange.coinbase.v2.CoinbaseAuthenticated.CB_ACCESS_TIMESTAMP;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.MediaType;
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
    String timestamp =
        restInvocation.getParamValue(HeaderParam.class, CB_ACCESS_TIMESTAMP).toString();
    String methodPath =
        "/v2" + restInvocation.getPath(); // todo  - move the /v2 bit of the path to the interface
    String message = timestamp + restInvocation.getHttpMethod() + methodPath;
    String body = null; // todo - deal with POST requests

    String sign = DigestUtils.bytesToHex(getMac().doFinal(message.getBytes()));

    //      String apiKey = restInvocation.getParamValue(HeaderParam.class,
    // CB_ACCESS_KEY).toString();
    //      showCurl(restInvocation.getHttpMethod(), apiKey, timestamp, sign, methodPath, body);

    return sign;
  }

  protected void showCurl(
      String method, String apiKey, String timestamp, String signature, String path, String json) {
    String headers =
        String.format(
            "-H 'CB-VERSION: 2017-11-26' -H 'CB-ACCESS-KEY: %s' -H 'CB-ACCESS-SIGN: %s' -H 'CB-ACCESS-TIMESTAMP: %s'",
            apiKey, signature, timestamp);
    if (method.equalsIgnoreCase("get")) {
      Coinbase.LOG.debug(String.format("curl %s https://api.coinbase.com%s", headers, path));
    } else if (method.equalsIgnoreCase("POST")) {
      String payload = "-d '" + json + "'";
      Coinbase.LOG.debug(
          String.format(
              "curl -X %s -H 'Content-Type: %s' %s %s https://api.coinbase.com%s",
              method, MediaType.APPLICATION_JSON, headers, payload, path));
    }
  }
}
