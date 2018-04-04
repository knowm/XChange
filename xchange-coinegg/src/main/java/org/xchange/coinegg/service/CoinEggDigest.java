package org.xchange.coinegg.service;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ws.rs.FormParam;
import javax.xml.bind.DatatypeConverter;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.Params;
import si.mazi.rescu.RestInvocation;

public final class CoinEggDigest extends BaseParamsDigest {

  private static Charset UTF8;

  private CoinEggDigest(String md5PrivateKey) {
    super(md5PrivateKey, HMAC_SHA_256);
  }

  public static CoinEggDigest createInstance(String privateKey) {
    try {
      CoinEggDigest.UTF8 = Charset.forName("UTF-8");
      MessageDigest md5 = MessageDigest.getInstance("MD5");

      return new CoinEggDigest(hex(md5.digest(privateKey.getBytes(UTF8))));
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(
          "Illegal algorithm for post body digest. Check the implementation.");
    }
  }

  private static String hex(byte[] b) {
    return DatatypeConverter.printHexBinary(b).toLowerCase();
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    // Create Query String From Form Parameters
    Params params = Params.of();
    restInvocation
        .getParamsMap()
        .get(FormParam.class)
        .asHttpHeaders()
        .entrySet()
        .stream()
        .filter(e -> !e.getKey().equalsIgnoreCase("signature"))
        .forEach(e -> params.add(e.getKey(), e.getValue()));

    // Parse Query String
    byte[] queryString = params.asQueryString().trim().getBytes(UTF8);

    // Create And Return Signature
    return hex(getMac().doFinal(queryString));
  }
}
