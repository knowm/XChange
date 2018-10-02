package org.knowm.xchange.bitz.dto.trade.result;

import org.knowm.xchange.bitz.dto.BitZResult;
import org.knowm.xchange.bitz.dto.trade.BitZOpenOrder;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BitZOpenOrderResult extends BitZResult<BitZOpenOrder> {

  public BitZOpenOrderResult(
      @JsonProperty("code") int code,
      @JsonProperty("msg") String message,
      @JsonProperty("data") BitZOpenOrder data) {
    super(code, message, data);
  }
}
