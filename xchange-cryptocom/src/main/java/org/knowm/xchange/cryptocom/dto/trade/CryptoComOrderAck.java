package org.knowm.xchange.cryptocom.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Acknowledgement result returned by {@code private/create-order} and {@code private/cancel-order}.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoComOrderAck {

  @JsonProperty("order_id")
  private String orderId;

  @JsonProperty("client_oid")
  private String clientOid;
}
