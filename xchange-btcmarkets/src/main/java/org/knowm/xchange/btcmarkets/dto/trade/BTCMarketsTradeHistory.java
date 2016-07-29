package org.knowm.xchange.btcmarkets.dto.trade;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.btcmarkets.dto.BTCMarketsBaseResponse;

public class BTCMarketsTradeHistory extends BTCMarketsBaseResponse {

  protected BTCMarketsTradeHistory(@JsonProperty("success") Boolean success, @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("errorCode") Integer errorCode, @JsonProperty("trades") List<BTCMarketsUserTrade> trades) {
    super(success, errorMessage, errorCode);
    this.trades = trades;
  }

  private List<BTCMarketsUserTrade> trades;

  public List<BTCMarketsUserTrade> getTrades() {
    return trades;
  }
}
