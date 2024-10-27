package org.knowm.xchange.bitget.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Locale;
import javax.crypto.Mac;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public final class BitgetDigest extends BaseParamsDigest {

  private BitgetDigest(String secretKeyBase64) {
    super(secretKeyBase64, HMAC_SHA_256);
  }

  public static BitgetDigest createInstance(String secretKeyBase64) {
    return secretKeyBase64 == null ? null : new BitgetDigest(secretKeyBase64);
  }

  @SneakyThrows
  @Override
  public String digestParams(RestInvocation restInvocation) {
    String method = restInvocation.getHttpMethod().toUpperCase(Locale.ROOT);
    String path = restInvocation.getPath();

    String query = StringUtils.defaultIfEmpty(restInvocation.getQueryString(), "");
    if (StringUtils.isNotEmpty(query)) {
      query = "?" + query;
    }
    String body = StringUtils.defaultIfEmpty(restInvocation.getRequestBody(), "");

    String timestamp = restInvocation.getHttpHeadersFromParams().get("ACCESS-TIMESTAMP");

    String payloadToSign = String.format("%s%s/%s%s%s", timestamp, method, path, query, body);

    Mac mac = getMac();
    mac.update(payloadToSign.getBytes(StandardCharsets.UTF_8));

    return Base64.getEncoder().encodeToString(mac.doFinal());
  }
}
