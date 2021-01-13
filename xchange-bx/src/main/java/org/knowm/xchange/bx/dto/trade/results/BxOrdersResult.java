package org.knowm.xchange.bx.dto.trade.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bx.dto.BxResult;
import org.knowm.xchange.bx.dto.trade.BxOrder;

public class BxOrdersResult extends BxResult<BxOrder[]> {

  public BxOrdersResult(
      @JsonProperty("orders") BxOrder[] result,
      @JsonProperty("success") boolean success,
      @JsonProperty("error") String error) {
    super(result, success, error);
  }
}
