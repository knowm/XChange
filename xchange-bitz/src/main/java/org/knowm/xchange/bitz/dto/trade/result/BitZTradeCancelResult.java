package org.knowm.xchange.bitz.dto.trade.result;

import org.knowm.xchange.bitz.dto.BitZResult;
import org.knowm.xchange.bitz.dto.trade.BitZTradeCancel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BitZTradeCancelResult extends BitZResult<BitZTradeCancel> {

  public BitZTradeCancelResult(
      @JsonProperty("code") int code,
      @JsonProperty("msg") String message,
      @JsonProperty("data") BitZTradeCancel data) {
    super(code, message, data);
  }
}
