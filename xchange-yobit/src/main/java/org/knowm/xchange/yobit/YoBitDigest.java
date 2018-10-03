package org.knowm.xchange.yobit;

import java.io.IOException;
import java.math.BigInteger;
import javax.crypto.Mac;
import javax.ws.rs.FormParam;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.Params;
import si.mazi.rescu.RestInvocation;

public class YoBitDigest extends BaseParamsDigest {

  private YoBitDigest(String secretKeyBase64, String apiKey) throws IOException {
    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static YoBitDigest createInstance(String secretKeyBase64, String apiKey) {
    try {
      return new YoBitDigest(secretKeyBase64, apiKey);
    } catch (Exception e) {
      throw new IllegalStateException("cannot create digest", e);
    }
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    Params params = restInvocation.getParamsMap().get(FormParam.class);

    StringBuilder queryString = new StringBuilder(params.asQueryString());

    Mac mac = getMac();

    byte[] source = mac.doFinal(queryString.toString().getBytes());

    return String.format("%0128x", new BigInteger(1, source));
  }
}
