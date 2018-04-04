package org.xchange.bitz.dto.marketdata.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.xchange.bitz.dto.BitZResult;
import org.xchange.bitz.dto.marketdata.BitZTrades;

public class BitZTradesResult extends BitZResult<BitZTrades> {

  public BitZTradesResult(
      @JsonProperty("code") int code,
      @JsonProperty("msg") String message,
      @JsonProperty("data") BitZTrades data) {
    super(code, message, data);
  }
}
