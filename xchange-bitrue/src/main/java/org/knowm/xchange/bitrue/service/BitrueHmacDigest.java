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

    Mac mac = getMac();
    mac.update(input.getBytes(StandardCharsets.UTF_8));
    String printBase64Binary = bytesToHex(mac.doFinal());
    return printBase64Binary;
  }

  public static void main(String[] args){
    String apiKey = "vmPUZE6mv9SD5VNHk4HlWFsOr6aKE2zvsw0MuIgwCIPy6utIco14y7Ju91duEh8A";
    String secKey = "NhqPtmdSJYdKjVHjA7PZj4Mge3R5YNiP1e3UZjInClVN65XAbvqqM6A7H5fATj0j";
    String input = "symbol=LTCBTC&side=BUY&type=LIMIT&timeInForce=GTC&quantity=1&price=0.1&recvWindow=5000&timestamp=1499827319559";
    BitrueHmacDigest bd = new BitrueHmacDigest(secKey);
    Mac mac = bd.getMac();
    mac.update(input.getBytes(StandardCharsets.UTF_8));
    String printBase64Binary = bytesToHex(mac.doFinal());
    System.out.println("c8db56825ae71d6d79447849e617115f4a920fa2acdcab2b053c4b2838bd6b71".equalsIgnoreCase(printBase64Binary));
  }
}
