package org.knowm.xchange.cryptocom.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.cryptocom.CryptoCom;
import org.knowm.xchange.cryptocom.CryptoComDigest;
import org.knowm.xchange.cryptocom.CryptoComExchange;
import org.knowm.xchange.cryptocom.dto.CryptoComRequest;
import org.knowm.xchange.cryptocom.dto.CryptoComResponse;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.service.BaseResilientExchangeService;

public class CryptoComBaseService extends BaseResilientExchangeService<CryptoComExchange> {

  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final Map<Class<?>, JavaType> LIST_TYPES = new ConcurrentHashMap<>();

  protected final CryptoCom cryptoCom;

  protected CryptoComBaseService(
      CryptoComExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
    this.cryptoCom = exchange.getCryptoCom();
  }

  /** Builds and signs the request envelope for a private Crypto.com Exchange v1 call. */
  protected CryptoComRequest buildRequest(String method, Map<String, Object> params) {
    long id = exchange.nextRequestId();
    long nonce = System.currentTimeMillis();
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    String apiSecret = exchange.getExchangeSpecification().getSecretKey();
    if (isBlank(apiKey) || isBlank(apiSecret)) {
      throw new ExchangeSecurityException(
          "Crypto.com API key/secret must be configured to call private endpoint '"
              + method
              + "'");
    }
    return CryptoComDigest.sign(
        method, id, nonce, apiKey, apiSecret, params == null ? Collections.emptyMap() : params);
  }

  private static boolean isBlank(String value) {
    return value == null || value.trim().isEmpty();
  }

  protected <T> List<T> toList(JsonNode node, Class<T> elementType) {
    if (node == null || node.isNull() || node.isMissingNode()) {
      return Collections.emptyList();
    }
    JavaType listType =
        LIST_TYPES.computeIfAbsent(
            elementType, type -> MAPPER.getTypeFactory().constructCollectionType(List.class, type));
    return MAPPER.convertValue(node, listType);
  }

  protected <T> T toObject(JsonNode node, Class<T> type) {
    if (node == null || node.isNull() || node.isMissingNode()) {
      return null;
    }
    return MAPPER.convertValue(node, type);
  }

  /** Converts the {@code result.data} array of a response envelope, tolerating a missing result. */
  protected <T> List<T> getDataList(CryptoComResponse response, Class<T> elementType) {
    JsonNode result = response.getResult();
    return toList(result == null ? null : result.get("data"), elementType);
  }

  protected <T> List<T> orEmpty(List<T> list) {
    return list == null ? Collections.emptyList() : list;
  }
}
