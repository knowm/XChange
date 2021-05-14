package org.knowm.xchange.btcmarkets.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.btcmarkets.dto.BTCMarketsBaseResponse;

public class BTCMarketsTradeHistory extends BTCMarketsBaseResponse {

  private List<BTCMarketsUserTrade> trades;

  protected BTCMarketsTradeHistory(
      @JsonProperty("success") Boolean success,
      @JsonProperty("errorMessage") String errorMessage,
      @JsonProperty("errorCode") Integer errorCode,
      @JsonProperty("trades") List<BTCMarketsUserTrade> trades) {
    super(success, errorMessage, errorCode);
    this.trades = trades;
  }

  public List<BTCMarketsUserTrade> getTrades() {
    return trades;
  }
}
