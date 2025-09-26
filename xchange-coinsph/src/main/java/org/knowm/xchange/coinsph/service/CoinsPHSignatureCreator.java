package org.knowm.xchange.coinsph.service;

import jakarta.ws.rs.QueryParam;
import javax.crypto.Mac;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.Params;
import si.mazi.rescu.RestInvocation;

/**
 * Custom signature creator for Coins.ph API that directly extracts the timestamp and recvWindow
 * parameters from the RestInvocation query string.
 */
public class CoinsPHSignatureCreator extends BaseParamsDigest {

  private CoinsPHSignatureCreator(String secretKey) {
    super(secretKey, HMAC_SHA_256);
  }

  public static CoinsPHSignatureCreator createInstance(String secretKey) {
    return secretKey == null ? null : new CoinsPHSignatureCreator(secretKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    Mac mac = getMac();
    mac.update(getQuery(restInvocation).getBytes());
    String body = restInvocation.getRequestBody();
    if (body != null) {
      mac.update(body.getBytes());
    }
    byte[] signature = mac.doFinal();
    String result = bytesToHex(signature);
    return result;
  }

  private static String getQuery(RestInvocation restInvocation) {
    final Params p = Params.of();
    restInvocation.getParamsMap().get(QueryParam.class).asHttpHeaders().entrySet().stream()
        .filter(e -> !"signature".equals(e.getKey())) // Use literal "signature"
        .forEach(e -> p.add(e.getKey(), e.getValue()));
    return p.asQueryString();
  }

  /**
   * Convert a byte array to a hex string
   *
   * @param bytes the byte array
   * @return the hex string
   */
  private static String bytesToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString();
  }
}
