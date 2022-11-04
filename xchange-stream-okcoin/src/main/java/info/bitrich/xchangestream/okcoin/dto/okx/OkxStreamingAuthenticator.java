package info.bitrich.xchangestream.okcoin.dto.okx;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class OkxStreamingAuthenticator {
  public static String getSHA256String(String str, String key) {

    SecretKeySpec signingKey =
        new SecretKeySpec(key.getBytes(StandardCharsets.US_ASCII), "HmacSHA256");
    try {
      Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
      sha256_HMAC.init(signingKey);
      sha256_HMAC.update(str.getBytes(StandardCharsets.US_ASCII));
      return Base64.getEncoder().encodeToString(sha256_HMAC.doFinal());
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String generateSignature(String secret, Object... args) {
    return getSHA256String(
        Arrays.stream(args).map(Object::toString).collect(Collectors.joining()), secret);
  }
}
