package org.knowm.xchange.cobinhood.dto.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cobinhood.dto.CobinhoodOrderType;

public class CobinhoodPlaceOrderRequest {

  @JsonProperty("trading_pair_id")
  private final String symbol;

  @JsonProperty("side")
  private final String side;

  @JsonProperty("type")
  private final CobinhoodOrderType type;

  @JsonProperty("price")
  private final String price;

  @JsonProperty("size")
  private final String amount;

  public CobinhoodPlaceOrderRequest(
      String symbol, String side, CobinhoodOrderType type, String price, String amount) {
    this.symbol = symbol;
    this.side = side;
    this.type = type;
    this.price = price;
    this.amount = amount;
  }
}
