package org.knowm.xchange.btctrade.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.btctrade.dto.BTCTradeResult;

public class BTCTradePlaceOrderResult extends BTCTradeResult {

  private final String id;

  public BTCTradePlaceOrderResult(
      @JsonProperty("result") Boolean result,
      @JsonProperty("message") String message,
      @JsonProperty("id") String id) {

    super(result, message);
    this.id = id;
  }

  public String getId() {

    return id;
  }
}
