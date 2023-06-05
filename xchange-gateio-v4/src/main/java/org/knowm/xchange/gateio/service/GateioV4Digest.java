package org.knowm.xchange.gateio.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import javax.crypto.Mac;
import lombok.SneakyThrows;
import org.knowm.xchange.service.BaseParamsDigest;
import org.knowm.xchange.utils.DigestUtils;
import si.mazi.rescu.RestInvocation;

public final class GateioV4Digest extends BaseParamsDigest {

  private GateioV4Digest(String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static GateioV4Digest createInstance(String secretKeyBase64) {
    return secretKeyBase64 == null ? null : new GateioV4Digest(secretKeyBase64);
  }

  @SneakyThrows
  @Override
  public String digestParams(RestInvocation restInvocation) {
    String method = restInvocation.getHttpMethod();
    String path = restInvocation.getPath();

    String query = restInvocation.getQueryString();
    String body = restInvocation.getRequestBody();
    MessageDigest md = MessageDigest.getInstance("SHA-512");
    String hexedHashedBody = DigestUtils.bytesToHex(md.digest(body.getBytes(StandardCharsets.UTF_8)));

    String timestamp = restInvocation.getHttpHeadersFromParams().get("Timestamp");

    String payloadToSign = String.format("%s\n/%s\n%s\n%s\n%s", method, path, query, hexedHashedBody, timestamp);

    Mac mac = getMac();
    mac.update(payloadToSign.getBytes(StandardCharsets.UTF_8));

    return DigestUtils.bytesToHex(mac.doFinal());
  }
}
