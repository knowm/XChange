package org.knowm.xchange.bitrue.service;

import org.knowm.xchange.bitrue.BitrueAuthenticated;
import org.knowm.xchange.service.BaseParamsDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.Params;
import si.mazi.rescu.RestInvocation;

import javax.crypto.Mac;
import javax.ws.rs.QueryParam;
import java.nio.charset.StandardCharsets;

import static org.knowm.xchange.utils.DigestUtils.bytesToHex;

public class BitrueHmacDigest extends BaseParamsDigest {

  private static final Logger LOG = LoggerFactory.getLogger(BitrueHmacDigest.class);

  private BitrueHmacDigest(String secretKeyBase64) {
    super(secretKeyBase64, HMAC_SHA_256);
  }

  public static BitrueHmacDigest createInstance(String secretKeyBase64) {
    return secretKeyBase64 == null ? null : new BitrueHmacDigest(secretKeyBase64);
  }

  /** @return the query string except of the "signature" parameter */
  private static String getQuery(RestInvocation restInvocation) {
    final Params p = Params.of();
    restInvocation.getParamsMap().get(QueryParam.class).asHttpHeaders().entrySet().stream()
        .filter(e -> !BitrueAuthenticated.SIGNATURE.equals(e.getKey()))
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
