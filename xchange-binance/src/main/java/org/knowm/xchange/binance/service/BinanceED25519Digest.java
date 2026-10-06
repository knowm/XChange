package org.knowm.xchange.binance.service;

import jakarta.ws.rs.QueryParam;
import java.security.PrivateKey;
import org.knowm.xchange.binance.BinanceAuthenticated;
import si.mazi.rescu.Params;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

public class BinanceED25519Digest implements ParamsDigest {

  private final PrivateKey privateKey;

  private BinanceED25519Digest(String secretKeyBase64) {
    this.privateKey = BinanceEd25519Signer.parsePrivateKey(secretKeyBase64);
  }

  public static BinanceED25519Digest createInstance(String secretKeyBase64) {
    return secretKeyBase64 == null ? null : new BinanceED25519Digest(secretKeyBase64);
  }

  /**
   * @return the query string except of the "signature" parameter
   */
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

    switch (restInvocation.getHttpMethod()) {
      case "GET":
      case "DELETE":
        input = getQuery(restInvocation);
        break;
      case "POST":
      case "PUT":
        input = getQuery(restInvocation) + restInvocation.getRequestBody();
        break;
      default:
        throw new RuntimeException("Not support http method: " + restInvocation.getHttpMethod());
    }
    return BinanceEd25519Signer.signBase64(privateKey, input);
  }
}
