package org.knowm.xchange.coinex.service;

import java.nio.charset.StandardCharsets;
import javax.crypto.Mac;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.service.BaseParamsDigest;
import org.knowm.xchange.utils.DigestUtils;
import si.mazi.rescu.RestInvocation;

public final class CoinexV2Digest extends BaseParamsDigest {

  private CoinexV2Digest(String secretKeyBase64) {
    super(secretKeyBase64, HMAC_SHA_256);
  }


  public static CoinexV2Digest createInstance(String secretKeyBase64) {
    return secretKeyBase64 == null ? null : new CoinexV2Digest(secretKeyBase64);
  }


  @SneakyThrows
  @Override
  public String digestParams(RestInvocation restInvocation) {
    String method = restInvocation.getHttpMethod();
    String path = restInvocation.getPath();

    String query = StringUtils.defaultIfEmpty(restInvocation.getQueryString(), "");
    String body = StringUtils.defaultIfEmpty(restInvocation.getRequestBody(), "");

    String timestamp = restInvocation.getHttpHeadersFromParams().get("X-COINEX-TIMESTAMP");

    String payloadToSign =
        String.format("%s/%s%s%s%s", method, path, query, body, timestamp);

    Mac mac = getMac();
    mac.update(payloadToSign.getBytes(StandardCharsets.UTF_8));

    return DigestUtils.bytesToHex(mac.doFinal());
  }
}
