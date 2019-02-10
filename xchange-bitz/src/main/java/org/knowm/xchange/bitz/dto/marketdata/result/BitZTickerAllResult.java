package org.knowm.xchange.bitz.dto.marketdata.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bitz.dto.BitZResult;
import org.knowm.xchange.bitz.dto.marketdata.BitZTickerAll;

public class BitZTickerAllResult extends BitZResult<BitZTickerAll> {

  public BitZTickerAllResult(
      @JsonProperty("code") int code,
      @JsonProperty("msg") String message,
      @JsonProperty("data") BitZTickerAll data) {
    super(code, message, data);
  }
}
