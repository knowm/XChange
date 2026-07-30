package org.knowm.xchange.cryptocom;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.junit.jupiter.api.Test;

public class CryptoComDigestTest {

  @Test
  public void nullParamValue_isRenderedAsLiteralNullInSignaturePayload() throws Exception {
    Map<String, Object> params = new LinkedHashMap<>();
    params.put("client_oid", null);

    String actual =
        CryptoComDigest.signature(
            "private/create-order", 1L, "key", 42L, params, "secret");

    String expectedPayload = "private/create-order" + 1L + "key" + "client_oidnull" + 42L;
    String expected = hmacSha256Hex(expectedPayload, "secret");

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void noParams_matchesPlainConcatenation() throws Exception {
    String actual =
        CryptoComDigest.signature(
            "public/get-instruments", 1L, "key", 42L, Collections.emptyMap(), "secret");

    String expected = hmacSha256Hex("public/get-instruments" + 1L + "key" + 42L, "secret");

    assertThat(actual).isEqualTo(expected);
  }

  private static String hmacSha256Hex(String data, String secret)
      throws NoSuchAlgorithmException, InvalidKeyException {
    Mac mac = Mac.getInstance("HmacSHA256");
    mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
    byte[] bytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString();
  }
}
