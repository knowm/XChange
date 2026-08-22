package info.bitrich.xchangestream.cryptocom.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;

/** Shared fixture-loading helper for the Crypto.com push-message envelope used by DTO tests. */
final class CryptoComStreamingTestSupport {

  private CryptoComStreamingTestSupport() {}

  static JsonNode readEnvelope(Class<?> testClass, String resourceName, ObjectMapper objectMapper)
      throws IOException {
    InputStream is = testClass.getResourceAsStream(resourceName);
    return objectMapper.readTree(is);
  }
}
