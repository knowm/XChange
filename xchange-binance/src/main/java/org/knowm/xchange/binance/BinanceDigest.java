package org.knowm.xchange.binance;

import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.Params;
import si.mazi.rescu.RestInvocation;

import javax.crypto.Mac;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

public class BinanceDigest extends BaseParamsDigest {

  private BinanceDigest(String secretKeyBase64) throws UnsupportedEncodingException {
    super(secretKeyBase64.getBytes(StandardCharsets.UTF_8), HMAC_SHA_256);
  }

  public static BinanceDigest createInstance(String secretKeyBase64, String apiKey) {
    try {
      return secretKeyBase64 == null ? null : new BinanceDigest(secretKeyBase64);
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException("Should not happen", e);
    }
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    Params params;
    if (restInvocation.getHttpMethod().equals("GET"))
      params = restInvocation.getParamsMap().get(QueryParam.class);
    else
      params = restInvocation.getParamsMap().get(FormParam.class);

    Map<String, String> sorted = new TreeMap<>(params.asHttpHeaders());
    String queryString = buildQueryString(sorted);

    Mac mac = getMac();
    mac.update(queryString.getBytes(StandardCharsets.UTF_8));

    return String.format("%064x", new BigInteger(1, mac.doFinal())).toUpperCase();
  }

  private static String buildQueryString(Map<String, String> args) {
    try {
      StringBuilder result = new StringBuilder();
      for (String hashKey : args.keySet()) {
        if (result.length() > 0)
          result.append('&');
        result.append(hashKey).append("=").append(URLEncoder.encode(args.get(hashKey), "UTF-8"));
      }
      return result.toString();
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException("Should not happen", e);
    }
  }
}
