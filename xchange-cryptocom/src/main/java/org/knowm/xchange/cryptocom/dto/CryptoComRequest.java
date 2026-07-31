package org.knowm.xchange.cryptocom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Envelope for all private (signed) Crypto.com Exchange v1 requests. The {@code sig} field is an
 * HMAC-SHA256 signature computed over {@code method + id + api_key + sortedParams + nonce}, see
 * {@link org.knowm.xchange.cryptocom.CryptoComDigest}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoComRequest {

  private long id;
  private String method;

  @JsonProperty("api_key")
  private String apiKey;

  private String sig;
  private long nonce;
  private Map<String, Object> params;
}
