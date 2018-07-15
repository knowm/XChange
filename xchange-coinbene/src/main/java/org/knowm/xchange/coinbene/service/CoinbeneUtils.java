package org.knowm.xchange.coinbene.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import javax.xml.bind.DatatypeConverter;

public class CoinbeneUtils {

  /**
   * Sign Coinbene private API request parameters.
   *
   * <p>Docs: <a href="https://github.com/Coinbene/API-Documents/wiki/0.1.0-Sign-Verification">
   *
   * @param params parameters
   * @return the sign
   */
  public static void signParams(Map<String, String> params) {

    String requestString =
        params
            .entrySet()
            .stream()
            .sorted(Comparator.comparing(Map.Entry::getKey))
            .map(e -> e.getKey() + "=" + e.getValue())
            .map(String::toUpperCase)
            .collect(Collectors.joining("&"));

    String sign = md5(requestString).toLowerCase();

    params.put("sign", sign);
    params.remove("secret");
  }

  private static String md5(String requestString) {
    try {
      return DatatypeConverter.printHexBinary(
          MessageDigest.getInstance("MD5").digest(requestString.getBytes()));
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }
}
