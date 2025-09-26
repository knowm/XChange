package org.knowm.xchange.coinsph.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Important for optional fields
public class CoinsphNewOrderRequest {

  @JsonProperty("symbol")
  private String symbol; // e.g., BTCPHP

  @JsonProperty("side")
  private String side; // BUY, SELL

  @JsonProperty("type")
  private String type; // LIMIT, MARKET, STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT

  @JsonProperty("timeInForce")
  private String timeInForce; // GTC, IOC, FOK (optional for some types)

  @JsonProperty("quantity")
  private BigDecimal quantity; // Required for most order types

  @JsonProperty("quoteOrderQty")
  private BigDecimal
      quoteOrderQty; // Optional: For MARKET orders, specifies the amount of quote asset

  @JsonProperty("price")
  private BigDecimal price; // Required for LIMIT, STOP_LOSS_LIMIT, TAKE_PROFIT_LIMIT

  @JsonProperty("newClientOrderId")
  private String newClientOrderId; // Optional

  @JsonProperty("stopPrice")
  private BigDecimal
      stopPrice; // Optional: Used with STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT

  @JsonProperty("recvWindow")
  private Long recvWindow; // Optional

  @JsonProperty("timestamp")
  private Long timestamp; // Mandatory for signed requests
}
