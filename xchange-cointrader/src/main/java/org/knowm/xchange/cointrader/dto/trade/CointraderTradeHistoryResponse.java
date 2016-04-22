package org.knowm.xchange.cointrader.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cointrader.dto.CointraderBaseResponse;

public class CointraderTradeHistoryResponse extends CointraderBaseResponse<CointraderUserTrade[]> {

  private final Integer totalCount;

  protected CointraderTradeHistoryResponse(@JsonProperty("success") Boolean success, @JsonProperty("message") String message,
      @JsonProperty("data") CointraderUserTrade[] data, @JsonProperty("totalCount") Integer totalCount) {
    super(success || "No Past Trades Found".equals(message), message, data);
    this.totalCount = totalCount;
  }

  public Integer getTotalCount() {
    return totalCount;
  }
}
