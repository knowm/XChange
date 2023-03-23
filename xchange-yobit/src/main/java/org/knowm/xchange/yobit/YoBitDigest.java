package org.knowm.xchange.yobit;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Random;
import javax.crypto.Mac;
import jakarta.ws.rs.FormParam;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.Params;
import si.mazi.rescu.RestInvocation;

public class YoBitDigest extends BaseParamsDigest {

  private YoBitDigest(String secretKeyBase64, String apiKey) throws IOException {
    super(secretKeyBase64, HMAC_SHA_512);
  }

  public static YoBitDigest createInstance(String secretKeyBase64, String apiKey) {
    try {
      String checkedSecretKeyBase64 =
          secretKeyBase64 != null ? secretKeyBase64 : getRandomSecretKey();
      return new YoBitDigest(checkedSecretKeyBase64, apiKey);
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

  private static String getRandomSecretKey() {
    byte[] array = new byte[7];
    new Random().nextBytes(array);
    return new String(array, Charset.forName("UTF-8"));
  }
}
