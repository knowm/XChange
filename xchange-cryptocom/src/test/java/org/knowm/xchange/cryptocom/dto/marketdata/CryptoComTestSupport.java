package org.knowm.xchange.cryptocom.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.knowm.xchange.cryptocom.dto.CryptoComResponse;

/** Shared fixture-loading helpers for the {@code CryptoComResponse} envelope used by DTO tests. */
final class CryptoComTestSupport {

  private CryptoComTestSupport() {}

  static CryptoComResponse readResponse(
      Class<?> testClass, String resourceName, ObjectMapper objectMapper) throws IOException {
    InputStream is = testClass.getResourceAsStream(resourceName);
    return objectMapper.readValue(is, CryptoComResponse.class);
  }

  static <T> List<T> readDataList(
      CryptoComResponse response, ObjectMapper objectMapper, Class<T> elementType) {
    return objectMapper.convertValue(
        response.getResult().get("data"),
        objectMapper.getTypeFactory().constructCollectionType(List.class, elementType));
  }
}
