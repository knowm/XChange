package org.xchange.bitz.dto.marketdata.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.xchange.bitz.dto.BitZResult;
import org.xchange.bitz.dto.marketdata.BitZOrders;

public class BitZOrdersResult extends BitZResult<BitZOrders> {

  public BitZOrdersResult(
      @JsonProperty("code") int code,
      @JsonProperty("msg") String message,
      @JsonProperty("data") BitZOrders data) {
    super(code, message, data);
  }
}
