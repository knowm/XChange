package org.xchange.bitz.dto.marketdata.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.xchange.bitz.dto.BitZResult;
import org.xchange.bitz.dto.marketdata.BitZTicker;

public class BitZTickerResult extends BitZResult<BitZTicker> {

  public BitZTickerResult(
      @JsonProperty("code") int code,
      @JsonProperty("msg") String message,
      @JsonProperty("data") BitZTicker data) {
    super(code, message, data);
  }
}
