package org.knowm.xchange.bx.dto.trade.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bx.dto.BxResult;

public class BxCreateOrderResult extends BxResult<String> {

  public BxCreateOrderResult(
      @JsonProperty("order_id") String result,
      @JsonProperty("success") boolean success,
      @JsonProperty("error") String error) {
    super(result, success, error);
  }
}
