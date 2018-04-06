package org.knowm.xchange.bx.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bx.dto.BxResult;
import org.knowm.xchange.bx.dto.marketdata.BxHistoryTrade;

public class BxHistoryTradeResult extends BxResult<BxHistoryTrade> {

  public BxHistoryTradeResult(
      @JsonProperty("data") BxHistoryTrade result,
      @JsonProperty("success") boolean success,
      @JsonProperty("error") String error) {
    super(result, success, error);
  }
}
