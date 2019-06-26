package org.knowm.xchange.bithumb.service;

import static org.knowm.xchange.utils.DigestUtils.bytesToHex;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;
import javax.crypto.Mac;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class BithumbDigest extends BaseParamsDigest {

  private BithumbDigest(String secretKeyBase64) {

    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static BithumbDigest createInstance(String secretKeyBase64) {

    return secretKeyBase64 == null ? null : new BithumbDigest(secretKeyBase64);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    final String path = restInvocation.getPath();
    final Map<String, String> httpHeadersFromParams = restInvocation.getHttpHeadersFromParams();
    final String requestBody = restInvocation.getRequestBody();

    final String input =
        String.join(";", path, requestBody, httpHeadersFromParams.get("Api-Nonce"));

    final Mac mac = getMac();
    try {
      mac.update(input.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    final String printBase64Binary = bytesToHex(mac.doFinal());
    return Base64.getEncoder().encodeToString(printBase64Binary.getBytes());
  }
}
