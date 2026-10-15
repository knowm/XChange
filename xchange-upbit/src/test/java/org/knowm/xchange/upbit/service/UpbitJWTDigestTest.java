package org.knowm.xchange.upbit.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.junit.jupiter.api.Test;

class UpbitJWTDigestTest {

  private static final String ACCESS_KEY = "my-access-key";
  private static final String SECRET_KEY = "my-secret-key";

  @Test
  void createsValidHs256Token() throws Exception {
    UpbitJWTDigest digest = UpbitJWTDigest.createInstance(ACCESS_KEY, SECRET_KEY);
    Map<String, String> claims = new LinkedHashMap<>();
    claims.put("access_key", ACCESS_KEY);
    claims.put("nonce", "nonce-1");
    claims.put("query", "market=KRW-BTC&side=bid");

    String token = digest.createToken(claims);
    String[] parts = token.split("\\.");
    assertThat(parts).hasSize(3);

    ObjectMapper mapper = new ObjectMapper();
    Base64.Decoder decoder = Base64.getUrlDecoder();
    JsonNode header = mapper.readTree(decoder.decode(parts[0]));
    assertThat(header.get("alg").asText()).isEqualTo("HS256");
    assertThat(header.get("typ").asText()).isEqualTo("JWT");

    JsonNode payload = mapper.readTree(decoder.decode(parts[1]));
    assertThat(payload.get("access_key").asText()).isEqualTo(ACCESS_KEY);
    assertThat(payload.get("nonce").asText()).isEqualTo("nonce-1");
    assertThat(payload.get("query").asText()).isEqualTo("market=KRW-BTC&side=bid");

    Mac mac = Mac.getInstance("HmacSHA256");
    mac.init(new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
    byte[] expected = mac.doFinal((parts[0] + "." + parts[1]).getBytes(StandardCharsets.UTF_8));
    assertThat(decoder.decode(parts[2])).isEqualTo(expected);
    assertThat(token).doesNotContain("=");
  }
}
