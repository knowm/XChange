package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.utils.DigestUtils.bytesToHex;

import jakarta.ws.rs.HeaderParam;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import javax.crypto.Mac;
import lombok.SneakyThrows;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

public class BybitDigest extends BaseParamsDigest {

  public static final String X_BAPI_API_KEY = "X-BAPI-API-KEY";
  public static final String X_BAPI_SIGN = "X-BAPI-SIGN";
  public static final String X_BAPI_TIMESTAMP = "X-BAPI-TIMESTAMP";
  public static final String X_BAPI_RECV_WINDOW = "X-BAPI-RECV-WINDOW";

  public BybitDigest(String secretKeyBase64) {
    super(secretKeyBase64, HMAC_SHA_256);
  }

  public static ParamsDigest createInstance(String secretKeyBase64) {
    return secretKeyBase64 == null ? null : new BybitDigest(secretKeyBase64);
  }

  @SneakyThrows
  @Override
  public String digestParams(RestInvocation restInvocation) {
    Map<String, String> headers = restInvocation.getParamsMap().get(HeaderParam.class).asHttpHeaders();

    // timestamp + API key + (recv_window) + (queryString | jsonBodyString)
    String plainText = getPlainText(restInvocation);

    String input =
        headers.get(X_BAPI_TIMESTAMP)
            + headers.get(X_BAPI_API_KEY)
            + headers.getOrDefault(X_BAPI_RECV_WINDOW, "")
            + plainText;
    Mac mac = getMac();
    mac.update(input.getBytes(StandardCharsets.UTF_8));
    return bytesToHex(mac.doFinal());
  }

  private static String getPlainText(RestInvocation restInvocation) {
    if ("GET".equals(restInvocation.getHttpMethod())) {
      return restInvocation.getQueryString();
    }
    if ("POST".equals(restInvocation.getHttpMethod())) {
      return restInvocation.getRequestBody();
    }
    throw new NotYetImplementedForExchangeException(
        "Only GET and POST are supported for plain text");
  }
}
