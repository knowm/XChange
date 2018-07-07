package org.knowm.xchange.bx.dto.trade.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bx.dto.BxResult;
import org.knowm.xchange.bx.dto.trade.BxTradeHistory;

public class BxTradeHistoryResult extends BxResult<BxTradeHistory[]> {

  public BxTradeHistoryResult(
      @JsonProperty("transactions") BxTradeHistory[] result,
      @JsonProperty("success") boolean success,
      @JsonProperty("error") String error) {
    super(result, success, error);
  }
}
