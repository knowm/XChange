package org.xchange.bitz.dto.trade.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.xchange.bitz.dto.BitZResult;
import org.xchange.bitz.dto.trade.BitZTradeCancel;

public class BitZTradeCancelResult extends BitZResult<BitZTradeCancel> {

  public BitZTradeCancelResult(
      @JsonProperty("code") int code,
      @JsonProperty("msg") String message,
      @JsonProperty("data") BitZTradeCancel data) {
    super(code, message, data);
  }
}
