package org.knowm.xchange.bitz.dto.marketdata.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bitz.dto.BitZResult;
import org.knowm.xchange.bitz.dto.marketdata.BitZKline;

public class BitZKlineResult extends BitZResult<BitZKline> {

  public BitZKlineResult(
      @JsonProperty("code") int code,
      @JsonProperty("msg") String message,
      @JsonProperty("data") BitZKline data) {
    super(code, message, data);
  }
}
