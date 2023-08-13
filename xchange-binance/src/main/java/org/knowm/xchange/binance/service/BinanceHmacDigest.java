package org.knowm.xchange.binance.service;

import static org.knowm.xchange.utils.DigestUtils.bytesToHex;

import java.nio.charset.StandardCharsets;
import javax.crypto.Mac;
import jakarta.ws.rs.QueryParam;
import org.knowm.xchange.binance.BinanceAuthenticated;
import org.knowm.xchange.service.BaseParamsDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.Params;
import si.mazi.rescu.RestInvocation;

public class BinanceHmacDigest extends BaseParamsDigest {

  private static final Logger LOG = LoggerFactory.getLogger(BinanceHmacDigest.class);

  private BinanceHmacDigest(String secretKeyBase64) {
    super(secretKeyBase64, HMAC_SHA_256);
  }

  public static BinanceHmacDigest createInstance(String secretKeyBase64) {
    return secretKeyBase64 == null ? null : new BinanceHmacDigest(secretKeyBase64);
  }

  /** @return the query string except of the "signature" parameter */
  private static String getQuery(RestInvocation restInvocation) {
    final Params p = Params.of();
    restInvocation.getParamsMap().get(QueryParam.class).asHttpHeaders().entrySet().stream()
        .filter(e -> !BinanceAuthenticated.SIGNATURE.equals(e.getKey()))
        .forEach(e -> p.add(e.getKey(), e.getValue()));
    return p.asQueryString();
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    final String input;

    if (restInvocation.getPath().startsWith("wapi/")) {
      // little dirty hack for /wapi methods
      input = getQuery(restInvocation);
    } else {
      switch (restInvocation.getHttpMethod()) {
        case "GET":
        case "DELETE":
          input = getQuery(restInvocation);
          break;
        case "POST":
          input = restInvocation.getRequestBody();
          break;
        default:
          throw new RuntimeException("Not support http method: " + restInvocation.getHttpMethod());
      }
    }

    Mac mac = getMac();
    mac.update(input.getBytes(StandardCharsets.UTF_8));
    String printBase64Binary = bytesToHex(mac.doFinal());
    return printBase64Binary;
  }
}