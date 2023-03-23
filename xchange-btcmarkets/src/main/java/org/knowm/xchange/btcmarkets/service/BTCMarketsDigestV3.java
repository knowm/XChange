package org.knowm.xchange.btcmarkets.service;

import java.util.Base64;
import javax.crypto.Mac;
import jakarta.ws.rs.HeaderParam;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class BTCMarketsDigestV3 extends BaseParamsDigest {

  public BTCMarketsDigestV3(String secretKey) {
    super(decodeBase64(secretKey), HMAC_SHA_512);
  }

  @Override
  public String digestParams(RestInvocation inv) {
    final String timestamp = inv.getParamValue(HeaderParam.class, "BM-AUTH-TIMESTAMP").toString();
    final String method = inv.getHttpMethod();
    final String path = inv.getPath();
    final String requestBody = inv.getRequestBody();

    final String stringToSign = method + path + timestamp + requestBody;
    return sign(stringToSign);
  }

  public String sign(String stringToSign) {
    Mac mac = getMac();
    mac.update(stringToSign.getBytes());
    return Base64.getEncoder().encodeToString(mac.doFinal());
  }
}
