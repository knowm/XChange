package org.knowm.xchange.bx.dto.trade.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bx.dto.BxResult;

public class BxCancelOrderResult extends BxResult<Object> {

  public BxCancelOrderResult(
      @JsonProperty("success") boolean success, @JsonProperty("error") String error) {
    super(null, success, error);
  }
}
