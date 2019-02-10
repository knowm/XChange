package org.knowm.xchange.bitz.dto.trade.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bitz.dto.BitZResult;
import org.knowm.xchange.bitz.dto.trade.BitZTradeAdd;

public class BitZTradeAddResult extends BitZResult<BitZTradeAdd> {

  public BitZTradeAddResult(
      @JsonProperty("code") int code,
      @JsonProperty("msg") String message,
      @JsonProperty("data") BitZTradeAdd data) {
    super(code, message, data);
  }
}
