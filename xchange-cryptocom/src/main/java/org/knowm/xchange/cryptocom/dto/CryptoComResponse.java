package org.knowm.xchange.cryptocom.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Envelope shared by every Crypto.com Exchange v1 REST response. {@code result} is kept as a raw
 * {@link JsonNode} because its shape differs per method (sometimes a {@code data} list, sometimes a
 * differently named list, sometimes the entity itself); the raw services convert it to the
 * appropriate type.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoComResponse {

  private long id;
  private String method;
  private int code;
  private String message;
  private JsonNode result;
}
