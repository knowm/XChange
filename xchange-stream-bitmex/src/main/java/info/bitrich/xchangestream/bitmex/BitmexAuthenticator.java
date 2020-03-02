package info.bitrich.xchangestream.bitmex;

import java.nio.charset.Charset;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/** Created by heath on 2018/3/1. */
public class BitmexAuthenticator {

  public static String getSHA256String(String str, String key) {

    try {
      Charset asciiCs = Charset.forName("US-ASCII");
      SecretKeySpec signingKey = new SecretKeySpec(asciiCs.encode(key).array(), "HmacSHA256");
      Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
      sha256_HMAC.init(signingKey);
      byte[] mac_data = sha256_HMAC.doFinal(asciiCs.encode(str).array());
      StringBuilder result = new StringBuilder();
      for (final byte element : mac_data) {
        result.append(Integer.toString((element & 0xff) + 0x100, 16).substring(1));
      }
      // System.out.println("SHA256String Result:[" + result + "]");
      return result.toString().toUpperCase();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String generateSignature(
      String secret, String verb, String url, String nonce, String data) {
    String message = verb + url + nonce + data;
    // System.out.println(message);
    return getSHA256String(message, secret);
  }
}
